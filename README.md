# Gestionale-Prenotazioni

## Avvio con Docker

Prerequisiti:
- Docker Desktop avviato

Servizi inclusi:
- `app`: backend Spring Boot su `http://localhost:8080`
- `db`: PostgreSQL locale su `localhost:5432`
- `sonarqube` + `sonar_db`: stack analisi codice (come da compose esistente)

Comandi principali (PowerShell):

```powershell
docker compose build app
docker compose up -d db app
docker compose logs -f app
```

Stop e cleanup volumi:

```powershell
docker compose down -v
```

Note:
- Le credenziali DB nel compose sono di sviluppo (`postgres/postgres`).
- Lo schema `formerjob` viene creato automaticamente da `docker/postgres/init/01-create-schema.sql`.
- Il backend usa il profilo `dev` quando gira nel container (`SPRING_PROFILES_ACTIVE=dev`).
