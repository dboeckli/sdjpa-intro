# Spring Data JPA - Introduction to Spring Data JPA

This repository contains source code examples to support my course Spring Data JPA and Hibernate Beginner to Guru

## Liquibase

To generate the initial Liquibase changelog, use and run the Liquibase Maven plugin with the generateChangeLog goal.
The application and database should be started before running this command.

The generated changelog.xml file can be moved into the src/main/resources/db/changelog folder. The Liquibase changelog is
applied whenever you start the application. In our case, all changelogs are always applied because we always start
with an initial MySQL database.

## Kubernetes

### Generate Config Map for mysql init script

When updating 'src/scripts/init-mysql-liquibase.sql', we need to apply this to the Kubernetes ConfigMap as well:

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