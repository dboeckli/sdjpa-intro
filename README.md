# Spring Data JPA - Introduction to Spring Data JPA

This repository contains source code examples to support my course Spring Data JPA and Hibernate Beginner to Guru

## Kubernetes

### Generate Config Map for mysql init script

When updating 'src/scripts/init-mysql.sql' we need to apply this to the kubernetes config map as well.

```bash
kubectl create configmap mysql-init-script --from-file=init.sql=src/scripts/init-mysql.sql --dry-run=client -o yaml | Out-File -Encoding utf8 k8s/mysql-init-script-configmap.yaml
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