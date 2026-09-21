# ISTP-003 — ISTP CHARTER

## Protocol hypothesis

ISTP is treated as a candidate verification primitive for inter-system state transitions. It is not assumed to be novel or superior.

## Candidate dimensions

ISTP may derive, from primitive artifacts only:

- semantic compatibility;
- cryptographic authenticity;
- authority at transition time;
- temporal/knowledge boundary;
- authenticated contestation;
- dependency impact of corrections.

These are output concepts, not vector input fields.

## Temporal model

The benchmark distinguishes transition time, verification time, valid time, knowledge time, and correction/effect time where the supplied artifacts support those distinctions.

Retroactive correction is represented as a new authenticated event. Historical observations and decisions are never silently overwritten.

## Authority model

A verifier must evaluate authority against the relevant historical time when sufficient authenticated evidence exists. Current status alone is not assumed to establish historical authority.

## Contestation model

Two independently authenticated and contradictory assertions are not automatically collapsed into a winner. Resolution, when present, must be represented by a distinct authenticated resolution event.

## Knowledge boundary

A cryptographically proven boundary, an attested-only boundary, a violated boundary, and an undetermined boundary are distinct outcomes. An attestation must not be silently promoted to cryptographic proof.

## Hardware neutrality

NVIDIA Confidential Computing, remote attestation, GPUs, or any other hardware/vendor implementation may be evaluated as optional backends. None is part of the definition of ISTP unless a later benchmark demonstrates that the capability itself is indispensable and non-composable.

## FABLE neutrality

FABLE/F#-compatible formal modeling may generate or validate adversarial cases and properties. It must not inject ISTP-specific fields or expected verdicts into canonical vectors.
