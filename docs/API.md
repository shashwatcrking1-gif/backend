# CivicShield Backend API

All routes are under `/api/v1`. JSON errors use `{ "timestamp": "...", "status": 400, "error": "...", "message": "...", "path": "..." }`. Paginated lists accept `page` (zero based) and `size` (default 20, maximum 100), and return Spring's `content`, `totalElements`, `totalPages`, `number`, and related page metadata.

## Authentication

`POST /auth/login` accepts `{ "email": "admin@example.org", "password": "..." }` and returns `{ "accessToken": "...", "tokenType": "Bearer", "expiresInSeconds": 28800, "user": { "id": 1, "email": "...", "name": "...", "role": "ADMIN", "enabled": true, "createdAt": null } }`. Send `Authorization: Bearer <accessToken>` on protected routes. `GET /auth/me` returns the signed-in user's view. Tokens expire after `JWT_EXPIRATION` (default 8h). This is a stateless contract suitable for a Vaadin server-side client; store tokens server-side and do not expose them to browser script storage. There is no refresh endpoint: sign in again after expiry.

## Incidents and risk

`GET /incidents?page=0&size=20&q=bridge&status=OPEN` returns a page of `{ id, title, type, location, description, seedRisk, status, createdBy, createdAt, updatedAt }`. `POST /incidents` and `PUT /incidents/{id}` accept `{ "title": "Flooding", "type": "FLOOD", "location": "Ward 2", "description": "...", "seedRisk": 35.0 }`. Creation returns 201. `GET /incidents/{id}` reads one incident. `PATCH /incidents/{id}/status` accepts `{ "status": "IN_PROGRESS" }` (`OPEN`, `IN_PROGRESS`, `RESOLVED`, `CLOSED`).

`POST /risks/analyze/{incidentId}` accepts an optional JSON object of local factors, for example `{ "rain-flood": 50, "flood-road": 10 }`; each factor is 0–100. `GET /risks` is paginated; `GET /risks/{incidentId}` returns the latest analysis. A risk response is `{ id, incidentId, score, severity, nodes, localFactors, analyzedAt }`. The engine applies the weights in order to the incident seed and adds `localFactor * 0.1` at each edge; omitted factors default to zero. The source specification and spreadsheet vectors were not present in the supplied workspace, so mapping incident attributes to factors and the supplied expected reference scores remain unverified. The rule engine does not claim real-world predictive accuracy.

## Alerts and recommendations

`GET /alerts` returns a page of `{ id, incidentId, severity, message, status, createdAt }`. `PATCH /alerts/{id}/status` accepts `{ "status": "ACKNOWLEDGED" }` (`ACTIVE`, `ACKNOWLEDGED`, `RESOLVED`). Risk analyses with severity other than LOW create an ACTIVE alert. `GET /recommendations` returns `{ id, incidentId, text, status, createdAt }`. The current backend stores/serves recommendations but does not synthesize them because no approved response playbook was supplied.

## Simulations and reports

`POST /simulations` accepts `{ "incidentId": 1, "seed": 40, "localFactors": { "rain-flood": 20 } }`. `seed` is optional and defaults to the incident seed. Simulation results persist for history but never update the incident. Response: `{ id, incidentId, score, severity, nodes, localFactors, createdAt }`. `GET /simulations` lists paginated history; `GET /simulations/{id}` reads one. `GET /dashboard/summary` returns `{ totalIncidents, openIncidents, activeAlerts, simulations }`. `GET /reports/summary` returns a timestamp, that summary and aggregate risk/recommendation counts.

## Users, roles, and health

`GET /users` is ADMIN-only and paginated; each result has `{ id, email, name, role, enabled, createdAt }`. Accounts are provisioned out-of-band; no public registration endpoint exists. Roles: ADMIN, ANALYST, RESPONDER, VIEWER. Viewer role is read-only; incident/risk changes and simulations require ADMIN, ANALYST, or RESPONDER. `GET /actuator/health` is public and reports application health.

## Local setup

Requires Java 21, Maven, and PostgreSQL. Create a database/user named `civicshield`, then from `backend/` run `mvn spring-boot:run`. The default datasource is local-only convenience configuration. Provide `BOOTSTRAP_ADMIN_EMAIL` and `BOOTSTRAP_ADMIN_PASSWORD` (at least 12 characters) to provision the initial admin; values are hashed with BCrypt and should be passed through environment configuration. For production set `SPRING_PROFILES_ACTIVE=prod`, datasource variables, and a secret `JWT_SECRET` of at least 32 characters. Run tests with `mvn clean test`; build/run with `mvn spring-boot:run`. Docker: `docker build -t civicshield-backend .`, then run with PostgreSQL and the same environment variables.
