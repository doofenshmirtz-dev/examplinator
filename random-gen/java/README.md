# 🔢 Random Number Generator API (Java – Quarkus & Spring Boot)

A REST API that returns a random unsigned long (`0` to `Long.MAX_VALUE`) optionally between `min` and `max`.

This repository provides:
- `random-java-quarkus-api`: built with Quarkus
- `random-java-springboot-api`: built with Spring Boot

---

## 📦 API Overview

### `GET /getRandom`
Returns a random number between optional query parameters `min` and `max`.

**Query Parameters:**
- `min` (optional): non-negative integer
- `max` (optional): non-negative integer

```bash
curl http://localhost:8080/getRandom
curl "http://localhost:8080/getRandom?min=10&max=99"
```

---

### `POST /getRandom`
Accepts a JSON payload with optional `min` and `max`. If omitted or empty, defaults are used.

```json
{
  "min": "10",
  "max": "99"
}
```

```bash
curl -X POST http://localhost:8080/getRandom \
     -H "Content-Type: application/json" \
     -d '{"min": "5", "max": "15"}'

curl -X POST http://localhost:8080/getRandom \
     -H "Content-Type: application/json" \
     -d '{}'
```

**Sample Success Response:**
```json
{
  "random": 90218320983201938
}
```

**Sample Error Response:**
```json
{
  "error": "min must be less than or equal to max"
}
```

---

## 🐳 Running with Docker

From the root of this `java/` directory:

### Quarkus
```bash
docker build -t quay.io/doofenshmirtz/random-gen:java-quarkus -f quarkus/random-gen/Dockerfile ./quarkus/random-gen
docker run -p 8080:8080 quay.io/doofenshmirtz/random-gen:java-quarkus
```

### Spring Boot
```bash
docker build -t quay.io/doofenshmirtz/random-gen:java-springboot -f spring-boot/random-gen/Dockerfile ./spring-boot/random-gen
docker run -p 8080:8080 quay.io/doofenshmirtz/random-gen:java-springboot
```

---

## ☸️ Deploying to Kubernetes (kind cluster)

The deployment manifests are available in:

- Quarkus: `quarkus/random-gen/manifests/deploy.yaml`
- Spring Boot: `spring-boot/random-gen/manifests/deploy.yaml`

### Step 1: Apply the appropriate manifest
```bash
# For Quarkus
kubectl apply -f quarkus/random-gen/manifests/deploy.yaml

# For Spring Boot
kubectl apply -f spring-boot/random-gen/manifests/deploy.yaml
```

### Step 2: Access via port forwarding
```bash
# Quarkus
kubectl port-forward -n random-gen svc/random-java-quarkus-service 8080:8080

# Spring Boot
kubectl port-forward -n random-gen svc/random-java-springboot-service 8080:8080
```

Then access:
```bash
curl http://localhost:8080/getRandom
```

---

## 🧪 Sample cURL Requests

```bash
curl http://localhost:8080/getRandom
curl http://localhost:8080/getRandom?min=100&max=500
curl -X POST http://localhost:8080/getRandom -H "Content-Type: application/json" -d '{"min":"1", "max":"100"}'
curl -X POST http://localhost:8080/getRandom -H "Content-Type: application/json" -d '{}'
```

---

## 🛠️ Built With

- Java 21
- Quarkus
- Spring Boot
- Docker
