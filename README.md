# Spring Data JPA - Introduction to Spring Data JPA

This repository contains source code examples to support my course Spring Data JPA and Hibernate Beginner to Guru

## Liquibase

Liquibase is enabled by default in the MySQL profile with the following properties:
- `spring.liquibase.enabled = true`
- `spring.flyway.enabled = false`
- `spring.docker.compose.file = compose-mysql-with-liquibase.yaml`

This profile starts MySQL on port 3306 using the Docker Compose file `compose-mysql-with-liquibase.yaml`.

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

When updating 'src/scripts/init-mysql-liquibase.sql', apply the changes to the Kubernetes ConfigMap:
```bash
kubectl create configmap mysql-init-script --from-file=init.sql=src/scripts/init-mysql-liquibase.sql --dry-run=client -o yaml | Out-File -Encoding utf8 k8s/mysql-init-script-configmap.yaml
```

### Deployment

To deploy all resources:
```bash
kubectl apply -f k8s/
```

To remove all resources:
```bash
kubectl delete -f k8s/
```

Check
```bash
kubectl get deployments -o wide
kubectl get pods -o wide
```

## Running the Application
1. Choose between Liquibase (default) or Flyway for database schema management. (you can use one of the preconfigured intellij runners)
2. Start the application with the appropriate profile and properties.
3. The application will use Docker Compose to start MySQL and apply the database schema changes.