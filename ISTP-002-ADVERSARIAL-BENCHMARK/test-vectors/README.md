# Test Vectors

The vectors are intentionally minimal and jurisdiction-neutral. They test protocol mechanics, not real legal conclusions.

## Vector A — Authority scope

Signer: `agent-17`

Authority scope:
- organization: `ministry-A`
- role: `procurement-officer`
- territory: `FR-75`
- procedure: `PROCUREMENT-AWARD`
- valid_from: `2026-01-01`
- valid_until: `2026-12-31`

Transition:
- organization: `ministry-A`
- territory: `FR-75`
- procedure: `PROCUREMENT-AWARD`
- decision_time: `2026-09-21T10:00:00Z`

Expected authority result: `AUTHORIZED`

Mutation: change territory to `FR-13`.
Expected result: `OUT_OF_SCOPE`.

## Vector B — Knowledge boundary

Decision time: `2026-06-01T10:00:00Z`
Knowledge boundary: `2026-06-01T09:00:00Z`

Evidence E1 observed at `2026-05-31T15:00:00Z`.
Evidence E2 first observed at `2026-06-02T11:00:00Z`.

A replay at the original decision time must be unable to use E2 as known information.
Expected result for E2: `FUTURE_KNOWLEDGE`.

## Vector C — Retroactive correction

Original assertion:
- valid_time: `2026-01-01..2026-12-31`
- recorded_at: `2026-03-01`

Correction received:
- recorded_at: `2026-09-21`
- states that the underlying fact was wrong from `2026-04-01`.

Expected behavior: preserve the original assertion and emit a correction/reconciliation event. Historical decision context remains replayable.

## Vector D — Signed contradiction

Assertion A:
- issuer: `authority-A`
- claim: `eligibility = TRUE`
- evidence_hash: `E-A`

Assertion B:
- issuer: `authority-B`
- claim: `eligibility = FALSE`
- evidence_hash: `E-B`

Both signatures verify.

Expected contestation result: `CONTESTED`.
No automatic winner is selected.

## Vector E — Semantic degradation

Source field: `annual_income_net`
Target field: `annual_income`

Mapping loses the distinction between net and gross.

Expected semantic result: `DEGRADED` / `ACCEPT_WITH_LOSS`, never `PRESERVED`.

## Determinism requirement

For identical vector + policy + verifier version + evidence set, the verifier must produce byte-equivalent canonical output. Any nondeterministic ordering is a benchmark failure.