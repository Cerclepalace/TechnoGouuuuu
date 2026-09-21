# ISTP-002 — Adversarial Benchmark

## Purpose

This benchmark tests whether an inter-system transition protocol contains a residual capability not already supplied by established interoperability, credential, evidence-exchange, provenance, policy, and ledger technologies.

It is a falsification benchmark, not a novelty claim.

## Current evidence boundary

Public specifications show that major parts of the proposed stack already exist:

- NIEM provides reusable semantic vocabulary and information-exchange interoperability.
- OOTS provides structured evidence requests/responses between competent authorities and a semantic/data-service architecture.
- W3C Verifiable Credentials 2.0 provides machine-verifiable claims, issuer/holder/verifier roles, validity/status, evidence, authorization, time, and proof mechanisms.
- IBC provides cross-ledger verification mechanisms, but its applicability to administrative/legal state transitions is a separate engineering question.

Therefore the benchmark targets the remaining composition problem rather than treating signatures, schemas, semantic mapping, or generic provenance as novel by themselves.

## Core question

Can two independently operated government systems exchange a transition that is:

1. semantically interpretable;
2. cryptographically authentic;
3. bound to an authority and jurisdiction scope;
4. temporally coherent across valid-time, decision-time, and knowledge-time;
5. explicit about later corrections;
6. able to preserve signed contradictions without requiring global consensus;
7. independently verifiable without querying or trusting the sender's internal database;
8. able to express semantic loss rather than silently pretending equivalence.

## Test matrix

### T01 — Semantic equivalence

Input: two incompatible local schemas describing the same administrative object.

Expected: mapping result is explicit as PRESERVED, DEGRADED, REJECTED, or UNKNOWN.

Kill condition: existing semantic-interoperability tooling already provides the required behavior with no additional transition semantics.

### T02 — Cryptographic transition verification

Input: signed transition plus sender state commitment and proof material.

Expected: receiver verifies the transition and its claimed predecessor state without accessing sender storage.

Kill condition: an existing standard/protocol already provides the same administrative transition semantics with equivalent trust assumptions.

### T03 — Authority and jurisdiction

Input: transition signed by an actor whose authority is limited by organization, role, territory, procedure, subject matter, validity interval, delegation, and revocation state.

Expected: verifier can determine whether the signer was authorized for this exact transition scope at the relevant time.

Kill condition: existing VC authorization/trust frameworks or government authorization infrastructure fully express and independently verify this requirement without a new protocol layer.

### T04 — Knowledge boundary

Input: decision made at T_decision using information available at T_knowledge; a later event becomes known at T_later.

Expected: replay at T_decision cannot use later knowledge. Later evidence creates a correction/reconciliation event instead of rewriting historical decision context.

Kill condition: existing bitemporal/event-sourcing/provenance standards already provide the complete interoperable contract required here.

### T05 — Contested truth

Input: Authority A asserts X; Authority B asserts not-X; both assertions are validly signed and independently sourced.

Expected: the protocol preserves both assertions, their provenance, temporal scope, and contestation status without manufacturing consensus.

Kill condition: existing provenance/VC/knowledge-representation mechanisms already provide equivalent interoperable semantics and lifecycle handling.

### T06 — Composite transition

Input: one real-world administrative transition crossing two schemas, two authorities, two jurisdictions, retroactive correction, and a contradiction.

Expected: a third-party verifier can produce a deterministic verdict set containing semantic status, cryptographic status, authority status, temporal status, contradiction status, and evidence references.

Kill condition: the same result can be produced by composing existing standards with no new protocol-level contract.

## Required verdicts

The verifier must never collapse all dimensions into one boolean.

Required output dimensions:

- SEMANTIC: PRESERVED | DEGRADED | REJECTED | UNKNOWN
- CRYPTO: VERIFIED | FAILED | UNKNOWN
- AUTHORITY: AUTHORIZED | OUT_OF_SCOPE | EXPIRED | REVOKED | UNKNOWN
- TEMPORAL: COHERENT | FUTURE_KNOWLEDGE | RETROACTIVE_CORRECTION | UNKNOWN
- CONTESTATION: NONE | CONTESTED | RECONCILIATION_REQUIRED
- OVERALL: ACCEPT | ACCEPT_WITH_LOSS | REVIEW_REQUIRED | CONTESTED | REJECT | UNKNOWN

## Non-goals

This benchmark does not prove:

- legal validity in any real jurisdiction;
- production-grade non-repudiation;
- universal government ontology;
- replacement of NIEM, OOTS, VC, IBC, PKI, policy engines, or event sourcing;
- commercial viability;
- scientific novelty.

## Research gate

Before calling the primitive novel, compare every test against at least one concrete existing implementation/specification. The burden of proof is on the residual delta.

## Sources checked for this benchmark

- NIEM official material: https://www.niem.gov/
- European Commission OOTS technical documentation: https://ec.europa.eu/digital-building-blocks/sites/spaces/OOTS/overview
- W3C Verifiable Credentials Data Model 2.0: https://www.w3.org/TR/vc-data-model-2.0/
- IBC Protocol documentation: https://ibcprotocol.org/

## Next gate

Implement T03–T05 as deterministic executable tests in the Java PoC, then run a second-party implementation against the same vectors. The protocol survives only if the residual behavior remains necessary after composition attempts with existing standards.