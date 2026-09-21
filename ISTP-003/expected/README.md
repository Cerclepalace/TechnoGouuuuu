# ISTP-003 — Pre-registered Oracle

The oracle is intentionally not populated with verdicts yet.

Before verifier implementation, each vector must receive an oracle status:

- `ORACLE_CONFIRMED`
- `ORACLE_DISPUTED`
- `ORACLE_UNDETERMINABLE`

Only confirmed, falsifiable vectors participate in the kill/survival calculation. Disputed or undeterminable vectors remain visible in the scientific report but cannot be used to claim survival.

The expected result set must be frozen and hashed before the first verifier implementation is accepted.
