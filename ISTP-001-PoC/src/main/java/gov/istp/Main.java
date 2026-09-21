package gov.istp;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.security.MessageDigest;
import java.sql.*;
import java.time.Instant;
import java.util.*;

public class Main {
    static final ObjectMapper JSON = new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);
    static final String SECRET = "ISTP-DEMO-KEY-CHANGE-IN-PRODUCTION";
    record Result(boolean accepted, List<String> reasons) {}

    static String sha256(String s) throws Exception {
        var md = MessageDigest.getInstance("SHA-256");
        return HexFormat.of().formatHex(md.digest(s.getBytes(StandardCharsets.UTF_8)));
    }
    static String hmac(String s) throws Exception {
        var mac = Mac.getInstance("HmacSHA256");
        mac.init(new SecretKeySpec(SECRET.getBytes(StandardCharsets.UTF_8), "HmacSHA256"));
        return HexFormat.of().formatHex(mac.doFinal(s.getBytes(StandardCharsets.UTF_8)));
    }
    static Connection db(String file) throws Exception {
        var c = DriverManager.getConnection("jdbc:sqlite:" + file);
        try (var st = c.createStatement()) {
            st.executeUpdate("CREATE TABLE IF NOT EXISTS authority(issuer TEXT, role TEXT, jurisdiction TEXT, action TEXT, valid_from TEXT, valid_to TEXT)");
            st.executeUpdate("CREATE TABLE IF NOT EXISTS accepted_transition(id TEXT PRIMARY KEY, issuer TEXT, payload_hash TEXT, decision_time TEXT, knowledge_time TEXT, status TEXT)");
        }
        return c;
    }
    static void seed(Connection a, Connection b) throws Exception {
        try (var st = a.createStatement()) {
            st.executeUpdate("DELETE FROM authority");
            st.executeUpdate("INSERT INTO authority VALUES ('Agency-A','BENEFIT_OFFICER','REGION-7','APPROVE_BENEFIT','2026-01-01T00:00:00Z','2027-01-01T00:00:00Z')");
        }
        try (var st = b.createStatement()) { st.executeUpdate("DELETE FROM accepted_transition"); }
        try (var st = b.createStatement()) {
            st.executeUpdate("DELETE FROM authority");
            st.executeUpdate("INSERT INTO authority VALUES ('Agency-A','BENEFIT_OFFICER','REGION-7','APPROVE_BENEFIT','2026-01-01T00:00:00Z','2027-01-01T00:00:00Z')");
        }
    }
    static Map<String,Object> transition(String id, String ruleVersion, String decision, String knowledge, boolean futureInjection) throws Exception {
        Map<String,Object> m = new LinkedHashMap<>();
        m.put("transition_id", id); m.put("issuer", "Agency-A"); m.put("subject", "PERSON-42");
        m.put("source_type", "A:ResidentRecord"); m.put("target_type", "B:BenefitCase");
        m.put("payload", Map.of("income_eur",1200,"residency","REGION-7","eligible",true,"future_injection",futureInjection));
        m.put("mapping", Map.of("preserved",List.of("income_eur","residency","eligible"),"degraded",List.of("household_definition"),"rejected",List.of("internal_case_notes")));
        m.put("authority", Map.of("role","BENEFIT_OFFICER","jurisdiction","REGION-7","action","APPROVE_BENEFIT"));
        m.put("temporal", Map.of("decision_time",decision,"knowledge_time",knowledge,"valid_from","2026-09-21T00:00:00Z","valid_to","2027-09-21T00:00:00Z"));
        m.put("evidence", List.of(Map.of("id","EVID-100","hash","demo-evidence-hash")));
        m.put("contradictions", List.of()); m.put("rule_version", ruleVersion);
        String canonical = JSON.writeValueAsString(m);
        m.put("payload_hash", sha256(canonical)); m.put("signature", hmac((String)m.get("payload_hash") + "|" + id));
        return m;
    }
    static Result verify(Connection b, Map<String,Object> t) throws Exception {
        List<String> reasons = new ArrayList<>();
        String id=(String)t.get("transition_id"), issuer=(String)t.get("issuer"), rule=(String)t.get("rule_version"), hash=(String)t.get("payload_hash"), sig=(String)t.get("signature");
        if (!Objects.equals(sig,hmac(hash+"|"+id))) reasons.add("AUTHENTICITY_FAIL");
        @SuppressWarnings("unchecked") Map<String,Object> authority=(Map<String,Object>)t.get("authority");
        @SuppressWarnings("unchecked") Map<String,Object> temporal=(Map<String,Object>)t.get("temporal");
        boolean auth=false;
        try (var ps=b.prepareStatement("SELECT 1 FROM authority WHERE issuer=? AND role=? AND jurisdiction=? AND action=? AND valid_from <= ? AND valid_to > ?")) {
            String d=(String)temporal.get("decision_time"); ps.setString(1,issuer); ps.setString(2,(String)authority.get("role")); ps.setString(3,(String)authority.get("jurisdiction")); ps.setString(4,(String)authority.get("action")); ps.setString(5,d); ps.setString(6,d);
            try(var rs=ps.executeQuery()){auth=rs.next();}
        }
        if(!auth) reasons.add("AUTHORITY_OR_JURISDICTION_FAIL");
        Instant decision=Instant.parse((String)temporal.get("decision_time")); Instant knowledge=Instant.parse((String)temporal.get("knowledge_time"));
        if(knowledge.isAfter(decision)) reasons.add("KNOWLEDGE_TIME_AFTER_DECISION_TIME");
        @SuppressWarnings("unchecked") Map<String,Object> payload=(Map<String,Object>)t.get("payload");
        if(Boolean.TRUE.equals(payload.get("future_injection"))) reasons.add("FUTURE_DATA_DECLARED_IN_PAYLOAD");
        if("RULE-999-UNDECLARED".equals(rule)) reasons.add("UNKNOWN_RULE_VERSION");
        boolean accepted=reasons.isEmpty();
        if(accepted) try(var ps=b.prepareStatement("INSERT OR REPLACE INTO accepted_transition VALUES(?,?,?,?,?,?)")){
            ps.setString(1,id); ps.setString(2,issuer); ps.setString(3,hash); ps.setString(4,(String)temporal.get("decision_time")); ps.setString(5,(String)temporal.get("knowledge_time")); ps.setString(6,"ACCEPTED"); ps.executeUpdate();
        }
        return new Result(accepted,reasons);
    }
    static void write(Path p,Object o)throws Exception{Files.writeString(p,JSON.writeValueAsString(o));}
    public static void main(String[] args)throws Exception{
        Files.createDirectories(Path.of("runtime"));
        try(var a=db("runtime/system-a.sqlite");var b=db("runtime/system-b.sqlite")){
            seed(a,b);
            var valid=transition("T-001","RULE-2026-01","2026-09-21T10:00:00Z","2026-09-21T09:00:00Z",false);
            var future=transition("T-002","RULE-2026-01","2026-09-21T10:00:00Z","2026-09-21T11:00:00Z",true);
            var badRule=transition("T-003","RULE-999-UNDECLARED","2026-09-21T10:00:00Z","2026-09-21T09:00:00Z",false);
            write(Path.of("runtime/T-001.json"),valid); write(Path.of("runtime/T-002-future.json"),future); write(Path.of("runtime/T-003-badrule.json"),badRule);
            System.out.println("ISTP-001 / Java PoC");
            for(var x:List.of(valid,future,badRule)){Result r=verify(b,x);System.out.printf("%s -> %s %s%n",x.get("transition_id"),r.accepted?"ACCEPTED":"REJECTED",r.reasons);}
        }
    }
}
