# Introduction to Spring Data JPA

Spring Boot 4 / Spring Data JPA demo project on Java 25, demonstrating JPA repositories
(natural/UUID/composite/embedded IDs) against H2 (MySQL-compat mode) and MySQL, with schema
management via Flyway and Liquibase.

## Architecture Overview

```mermaid
graph LR
    Client(["💻 Client"])

    subgraph App ["Spring Boot App :8080"]
        Init["DataInitializer\n(seed data)"]
        Repos["Spring Data JPA\nRepositories"]
    end

    subgraph Domain ["Domain Model (ID strategies)"]
        LongId["Book / Author\n@GeneratedValue"]
        NaturalId["BookNatural\nnatural id"]
        UuidId["BookUuid / AuthorUuid\nUUID id"]
        CompositeId["AuthorComposite / AuthorEmbedded\ncomposite / embedded id"]
    end

    subgraph Migration ["Schema Management"]
        Flyway["Flyway\ndb/migration"]
        Liquibase["Liquibase\ndb/changelog"]
    end

    subgraph Databases ["Databases"]
        H2[("H2\nIn-Memory")]
        MySQL[("MySQL\nDocker")]
    end

    Client -->|"actuator :8080"| App
    Init --> Repos
    Repos --> Domain
    Repos <--> H2
    Repos <--> MySQL
    Flyway --> MySQL
    Liquibase --> MySQL
```

## Database Schema

```mermaid
erDiagram
    book {
        BIGINT       id PK "auto_increment"
        VARCHAR(255) title
        VARCHAR(255) isbn
        VARCHAR(255) publisher
        BIGINT       author_id
    }

    author {
        BIGINT       id PK "auto_increment"
        VARCHAR(255) first_name
        VARCHAR(255) last_name
    }

    author_uuid {
        VARCHAR(36)  id PK
        VARCHAR(255) first_name
        VARCHAR(255) last_name
    }

    book_uuid {
        BINARY(16)   id PK
        VARCHAR(255) title
        VARCHAR(255) isbn
        VARCHAR(255) publisher
    }

    book_natural {
        VARCHAR(255) title PK
        VARCHAR(255) isbn
        VARCHAR(255) publisher
    }

    author_composite {
        VARCHAR(255) first_name PK
        VARCHAR(255) last_name PK
        VARCHAR(255) country
    }

    author_embedded {
        VARCHAR(255) first_name PK
        VARCHAR(255) last_name PK
        VARCHAR(255) country
    }

    author ||--o{ book : "author_id"
```

## Build & Test

```bash
./mvnw clean verify          # full build: format check, unit (*Test) + IT (*IT) tests, Helm lint/template
./mvnw clean install         # verify + build local Docker image + package Helm chart
./mvnw test                  # unit tests only (surefire)
./mvnw verify                # integration tests only (failsafe)
./mvnw test -Dtest=BookRepositoryWithH2Test              # single test class
./mvnw test -Dtest=BookRepositoryWithH2Test#methodName   # single test method
./mvnw spotless:apply        # auto-fix pom/markdown/json/yaml/shell formatting
./mvnw spring-javaformat:apply                           # auto-fix Java code style
```

> Formatting is enforced at the `validate` phase. Run both `spotless:apply` and
> `spring-javaformat:apply` before committing if the build fails there.
> Skip the in-build app boot with `-Dskip.start.stop.springboot=true` and the Docker build with
> `-Dskip.docker.build=true`.

## Liquibase

Liquibase is enabled by default in the MySQL profile with the following properties:
- `spring.liquibase.enabled = true`
- `spring.flyway.enabled = false`
- `spring.docker.compose.file = compose-mysql-with-liquibase.yaml`

This profile starts MySQL on port 3306 using the Docker Compose file `compose-mysql-with-liquibase.yaml`

To generate the initial Liquibase changelog:

1. Start the application and database.
2. Run the Liquibase Maven plugin with the `generateChangeLog` goal.
3. Move the generated `changelog.xml` file into the `src/main/resources/db/changelog` folder.

The Liquibase changelog is applied whenever you start the application. In our case, all changelogs are always applied because we always start with an initial MySQL database.

## Flyway

To enable Flyway in the MySQL profile, override the following properties when starting the application:
- `spring.liquibase.enabled = false`
- `spring.flyway.enabled = true`
- `spring.docker.compose.file = compose-mysql-with-flyway.yaml`

This profile starts MySQL on port 3307 using the Docker Compose file `compose-mysql-with-flyway.yaml`.

## Docker

Both Docker Compose files (for mysql/liquibase and mysql/flyway) initially use the startup script located in `src/scripts`. These scripts create the database and users.

## Kubernetes

### Generate Config Map for mysql init script

When updating `src/scripts/init-mysql-liquibase.sql`, regenerate the Kubernetes ConfigMap:

```powershell
kubectl create configmap mysql-init-script --from-file=init.sql=src/scripts/init-mysql-liquibase.sql --dry-run=client -o yaml | Out-File -Encoding utf8 k8s/mysql-init-script-configmap.yaml
```

### Deployment with Kubernetes

Deployment goes into the **default** namespace when using raw manifests, or the **`sdjpa-intro`** namespace when
using Helm.

To deploy all resources:

```bash
kubectl apply -f target/k8s/
```

To remove all resources:

```bash
kubectl delete -f target/k8s/
```

Check

```bash
kubectl get deployments -o wide
kubectl get pods -o wide
```

You can use the actuator rest call to verify via port 30080

## Deployment with Helm

Be aware that we are using a different namespace here (not default).

Go to the directory where the tgz file has been created after 'mvn install'

```powershell
cd target/helm/repo
```

unpack

```powershell
$file = Get-ChildItem -Filter sdjpa-intro-chart-*.tgz | Select-Object -First 1
tar -xvf $file.Name
```

install

```powershell
$APPLICATION_NAME = Get-ChildItem -Directory | Where-Object { $_.LastWriteTime -ge $file.LastWriteTime } | Select-Object -ExpandProperty Name
helm upgrade --install $APPLICATION_NAME ./$APPLICATION_NAME --namespace sdjpa-intro --create-namespace --wait --timeout 5m --debug --render-subchart-notes
```

show logs and show event

```powershell
kubectl get pods -n sdjpa-intro
```

replace $POD with pods from the command above

```powershell
kubectl logs $POD -n sdjpa-intro --all-containers
```

Show Details and Event

$POD_NAME can be: sdjpa-intro-mysql, sdjpa-intro

```powershell
kubectl describe pod $POD_NAME -n sdjpa-intro
```

Show Endpoints

```powershell
kubectl get endpoints -n sdjpa-intro
```

test

```powershell
helm test $APPLICATION_NAME --namespace sdjpa-intro --logs
```

uninstall

```powershell
helm uninstall $APPLICATION_NAME  --namespace sdjpa-intro
```

delete all

```powershell
kubectl delete all --all -n sdjpa-intro
```

create busybox sidecar

```powershell
kubectl run busybox-test --rm -it --image=busybox:1.36 --namespace=sdjpa-intro --command -- sh
```

You can use the actuator rest call to verify via port 30080

## Running the Application

1. Choose between Liquibase (default) or Flyway for database schema management. (you can use one of the preconfigured intellij runners)
2. Start the application with the appropriate profile and properties.
3. The application will use Docker Compose to start MySQL and apply the database schema changes.

## Sandbox (local dev environment)

The sandbox is provisioned by the opencode-sandbox-kit and runs as a Docker container. It mounts this
repo, starts the agent, and connects the IntelliJ MCP server. The app runs on port `8080`; the
`compose-mysql-with-*.yaml` files provide MySQL.

Allow the kit source (GitHub without cloning):

```powershell
sbx settings set kit.allowedSources --% "[\"docker.io/\",\"github.com/dboeckli/\"]"
```

Start a new sandbox:

```powershell
sbx run opencode `
    --kit "git+https://github.com/dboeckli/opencode-sandbox-kit.git#dir=opencode-agent" `
    --template docker/sandbox-templates:opencode-docker-0.5.0 `
    --no-share-skills `
    --static-mcp idea `
    . `
    "C:\development\maven-repo:ro"
```

Start the sandbox with Kubernetes support:

```powershell
sbx run opencode `
    --kit "git+https://github.com/dboeckli/opencode-sandbox-kit.git#dir=opencode-agent" `
    --template docker/sandbox-templates:opencode-docker-0.5.0 `
    --no-share-skills `
    --static-mcp idea `
    . `
    "C:\development\maven-repo:ro" `
    "$env:USERPROFILE\.kube:ro"
```

Claude Code (Home) and Mammouth Code variants:

```powershell
sbx run claude `
    --kit "git+https://github.com/dboeckli/opencode-sandbox-kit.git#dir=opencode-agent" `
    --template docker/sandbox-templates:claude-code-docker-0.5.0 `
    --no-share-skills `
    --static-mcp idea `
    . `
    "C:\development\maven-repo:ro"
```

```powershell
sbx run "git+https://github.com/dboeckli/opencode-sandbox-kit.git#dir=mammouth-agent" `
    --no-share-skills `
    --static-mcp idea `
    . `
    "C:\development\maven-repo:ro"
```

Apply the kit to an existing sandbox (restarts the sandbox, VM state is kept):

```powershell
sbx kit add <sandbox-name> "git+https://github.com/dboeckli/opencode-sandbox-kit.git#dir=opencode-agent"
```

### Start the app

Pick a profile (H2 needs no Docker, MySQL uses the compose file):

```shell
docker compose -f compose-mysql-with-flyway.yaml up
```

Then run one of the IntelliJ run configurations (`.run/Spring6Application h2.run.xml`,
`.run/Spring6Application mysql flyway.run.xml`, `.run/Spring6Application mysql liquibase.run.xml`) or
start via `./mvnw spring-boot:run -Dspring-boot.run.profiles=h2`.

### Sandbox build quirk

The sandbox mounts the repo via filesystem passthrough, which blocks symlinks — Spotless's `npm install`
(prettier) would fail with `EPERM` unless npm skips bin links. The kit sets `npm_config_bin_links=false`
globally, so no manual export is needed.

