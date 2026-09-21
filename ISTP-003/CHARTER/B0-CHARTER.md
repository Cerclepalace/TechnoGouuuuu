# ISTP-003 — B0 CHARTER

## Purpose

B0 is the strongest reasonable baseline composed only from pre-authorized standard mechanisms. It must not be reduced to a naive current-status checker.

## Authorized stack

1. W3C Verifiable Credentials Data Model v2.0.
2. W3C Data Integrity mechanisms applicable to the supplied credentials.
3. W3C Bitstring Status List.
4. Authenticated transparency-log evidence, including SCITT-style transparency services where applicable.
5. RFC 3161 trusted timestamp tokens.
6. Historical artifacts and authenticated inclusion proofs available through the mechanisms above.
7. Deterministic application logic needed to compose those mechanisms, provided that the logic does not import ISTP-specific semantics.

## Explicit limits

B0 may reconstruct historical status when the supplied artifacts actually prove it.

B0 may compose multiple standards. A B0 failure is not established merely because one standard, considered alone, lacks a capability that a composition can provide.

B0 may not use an ISTP-specific protocol, verdict enum, hidden oracle, or ISTP-specific input field.

## Repair round

Every candidate ISTP delta receives one mandatory B0 repair round. The repair may improve composition or implementation while remaining inside this charter. A post-hoc expansion of the charter invalidates the old run and requires a new benchmark identity and hash.

## Determinism

Given identical canonical input bytes and identical authorized reference material, B0 must emit deterministic JSON. Any order-dependent result is recorded as such and tested separately; it cannot be silently converted into a semantic advantage for ISTP.

## Scope of claims

B0 is a benchmark baseline, not a claim that the listed standards are legally sufficient for every government deployment.
