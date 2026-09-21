# ISTP-001 — Proof-of-Concept v0.1

## Objective

Test the narrow hypothesis:

> Two systems with different SQLite schemas can exchange a transition while the receiver verifies origin/integrity, semantic mapping metadata, authority scope, temporal consistency, and declared contradictions without reading the issuer's internal database.

This is a research PoC, not a production security protocol.

## What this PoC actually proves

- Two independent SQLite databases are created.
- System A emits signed transition envelopes.
- System B verifies the envelope without querying A.
- The envelope carries explicit preserved/degraded/rejected semantic fields.
- Authority is checked against B's local authority registry.
- `decision_time` and `knowledge_time` are distinct.
- A future knowledge boundary is rejected deterministically.
- An undeclared rule version is rejected.
- Accepted transitions are persisted with provenance.

## What it does NOT prove

- Legal validity in a real jurisdiction.
- Zero-knowledge execution proofs.
- NIEM compatibility.
- IBC compatibility.
- Production-grade PKI, key management, revocation, or non-repudiation.
- That ISTP is novel. Novelty requires a dedicated prior-art/patent/literature search.

## Threat tests

T1 valid transition -> ACCEPT  
T2 knowledge time after decision time -> REJECT  
T3 declared future data -> REJECT  
T4 unknown rule version -> REJECT  
T5 authority/jurisdiction mismatch -> REJECT  
T6 tampered signature -> REJECT (extendable test)  
T7 contradictory signed assertions -> transport/preservation test (next increment)

## Run

Requirements: JDK 21+ and Maven 3.9+.

```bash
mvn test
mvn package
java -jar target/istp-001-poc-0.1.0.jar
```

## Next gate

Do not expand the platform before implementing the delta tests against NIEM-style mapping, IBC-style state proofs, delegated authority, retroactive legal correction, signed contradiction sets, and third-party independent verification.
