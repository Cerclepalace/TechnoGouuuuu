# ISTP-003 — Canonical Vector Contract

Canonical vectors are intentionally neutral. They contain primitive artifacts emitted by real systems and a verification query. They must not contain ISTP-specific dimensions or expected verdicts.

## Allowed conceptual inputs

- Verifiable credentials and data-integrity proofs.
- Bitstring Status List credentials.
- Timestamp tokens.
- Transparency-log inclusion/proof artifacts.
- Signed assertions.
- Signed correction/resolution events.
- References between artifacts when those references are themselves authenticated.
- Query parameters such as transition time and verification time.

## Forbidden in canonical vectors

No field may encode an ISTP-derived verdict or dimension, including names such as:

- `authorized_at_transition_time`
- `evidence_boundary`
- `temporal_boundary_type`
- `contested`
- `superseded`
- `accept_with_loss`
- `review_required`
- `status_unknown_at_t`

The verifier must derive these conclusions.

## Required differential property

Both B0 and ISTP consume the same canonical bytes. Neither verifier may receive an enriched copy containing the other verifier's interpretation.

## Planned vector families

- T03: historical authority, revocation timing, retroactive effect, jurisdiction.
- T04: knowledge boundary, post-cutoff evidence, correction, dependency closure.
- T05: authenticated contradiction, explicit resolution, unverifiable assertion, permutation invariance.
