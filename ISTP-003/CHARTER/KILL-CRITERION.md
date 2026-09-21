# ISTP-003 — KILL CRITERION

## Status

NON-VALIDATED. This document defines the falsification gate before implementation.

## H0 — Composition hypothesis

All claimed ISTP capabilities can be reproduced by a competent composition of the authorized B0 stack within its charter.

## H1 — Residual delta hypothesis

At least one canonical vector has an oracle result that ISTP reproduces correctly while B0 cannot reproduce it within the B0 charter, including one mandatory B0 repair round.

## Survival condition

ISTP may be marked `CANDIDATE_SURVIVAL` only if all are true:

1. The vector contains only primitive real-world artifacts; no ISTP-specific output field is supplied as input.
2. The oracle is not `ORACLE_DISPUTED` or `ORACLE_UNDETERMINABLE`.
3. ISTP matches the pre-registered oracle.
4. B0 does not match the oracle.
5. B0 has received one explicit repair attempt within its frozen charter.
6. The divergence is reproducible and attributable to a structural capability gap, not implementation error or missing B0 composition.

`CANDIDATE_SURVIVAL` is not proof of novelty. It is a surviving empirical candidate for further research.

## Kill condition

ISTP is `KILLED_BY_B0` if every non-disputed vector that initially differentiates ISTP from B0 becomes reproducible by B0 after the repair round, or if the supposed delta is shown to be an application policy/composition artifact.

## Required invariants

- Same canonical input bytes for both verifiers.
- Deterministic JSON output.
- No post-hoc oracle changes.
- No ISTP-only fields in canonical vectors.
- No NVIDIA hardware requirement in the protocol definition.
- FABLE is a benchmark/formal-modeling aid, not an input-side oracle.
- Retroactive correction creates a new event; historical state is not silently overwritten.
- Authenticated contradictions are preserved until an explicit resolution event exists.
