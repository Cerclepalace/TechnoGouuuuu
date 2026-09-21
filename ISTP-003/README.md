# ISTP-003 — Adversarial Differential Benchmark

## Status

**PRE-IMPLEMENTATION / NON-VALIDATED**

ISTP-003 is the consolidated falsification benchmark derived from the preceding ISTP analysis and adversarial review. No novelty, superiority, or legal sufficiency is assumed.

## Objective

Determine whether any claimed ISTP capability survives comparison against a competent B0 composition of existing standard mechanisms.

The experiment is deliberately capable of killing ISTP.

## Core rule

Both verifiers consume the **same canonical primitive artifacts**. Canonical vectors must not contain ISTP-specific dimensions or expected verdicts. Each verifier derives its own conclusions.

## B0

B0 is not a straw man. Its frozen charter permits composition of:

- W3C Verifiable Credentials Data Model v2.0
- W3C Data Integrity mechanisms
- W3C Bitstring Status List
- authenticated transparency-log evidence / SCITT-style mechanisms
- RFC 3161 trusted timestamping
- authenticated historical artifacts and inclusion proofs
- deterministic application logic that does not import ISTP-specific semantics

Every candidate delta receives one mandatory B0 repair round inside this charter.

## ISTP hypothesis

ISTP may provide a compositional verification model covering historical authority, temporal/knowledge boundaries, authenticated contestation, and correction dependency closure. These are hypotheses to test, not established facts.

## Main falsification axes

### T03 — Historical authority

Test whether authority can be reconstructed at the exact transition time, including later revocation and retroactive effect. A current status alone is not treated as sufficient evidence of historical status.

### T04 — Knowledge boundary and reconciliation

Separate cryptographically proven boundaries from attestations. Test post-cutoff evidence, immutable historical decisions, correction events, and reverse dependency closure from corrected evidence to affected decisions.

### T05 — Authenticated contestation

Test preservation of contradictory authenticated assertions, explicit signed resolution, unverifiable claims, and deterministic behavior under permutation of input order.

## Important controls

- V-T03-1 is a control candidate, not presumed ISTP survival.
- Retroactive revocation/effect receives a distinct vector.
- T04a (proven boundary) and T04b (attested-only boundary) are distinct.
- `SUPERSEDED` is not presumed novel because append-only credentials/logs may reproduce it.
- Dependency closure is tested separately.
- T05 permutation invariance is a mechanical property and is not replaced by a subjective contestation judgment.

## Oracle protocol

The oracle is frozen before verifier implementation. Vectors with disputed or undeterminable expected results are excluded from the kill/survival calculation but remain in reports.

## Kill / survival

ISTP can only reach `CANDIDATE_SURVIVAL` when a confirmed oracle is matched by ISTP, not matched by B0, and the B0 result remains non-matching after the mandatory repair round. The divergence must be structural and reproducible.

If all surviving candidates are reproduced by B0, the result is `KILLED_BY_B0`.

`CANDIDATE_SURVIVAL` is not proof of novelty. It is a surviving empirical research candidate.

## FABLE and NVIDIA

FABLE/F#-compatible formal modeling is treated as a benchmark-generation/property-testing aid and cannot enrich vectors with ISTP semantics.

NVIDIA Confidential Computing / remote attestation is treated as an optional T04 backend candidate, not as a defining requirement of ISTP. Hardware dependence cannot be used to manufacture a protocol delta.

## Implementation gate

Do not implement the verifiers until the charter, neutral vector contract, oracle protocol, and kill criterion are frozen and hashed.
