# GitHub Actions CI Workflows for HuyCommerce

This repository uses a modular and smart Continuous Integration (CI) setup powered by GitHub Actions. It supports:

- Java microservices (Maven)
- Angular frontend (web-app)
- Efficient matrix builds
- Auto-detection of changed modules

---

## 🔁 Workflow Overview

| Workflow File                      | Purpose                                             |
|-----------------------------------|-----------------------------------------------------|
| `build-feature-branch.yml`        | Triggered on `feature/*` pushes (quick builds)     |
| `pull-request-build.yml`          | Triggered on PRs to `develop` or `main`            |
| `reusable-detect-changes.yml`     | Reusable logic for detecting changed modules       |
| `reusable-service-build.yml`      | Builds a Java microservice using Maven             |
| `reusable-webapp-build.yml`       | Builds the Angular frontend                        |

---

## ⚙️ How It Works

### 🔍 1. `reusable-detect-changes.yml`
- Detects what changed in a commit/PR
- Sets outputs:
    - `matrix`: list of services to build (in JSON)
    - `webapp_changed`: boolean flag
- Handles edge cases (e.g., first commit, missing history)
- Prints changed files for debugging

### ⚙️ 2. Service Build (`reusable-service-build.yml`)
- Builds any service folder (e.g., `identity-service`) via Maven
- Supports input `goal`: use `clean package -DskipTests` or `clean verify`
- Uses `actions/cache` to speed up Maven dependency loading

### ⚙️ 3. Angular WebApp Build (`reusable-webapp-build.yml`)
- Runs `npm install`, `ng build`, and optionally `ng lint`
- Caches:
    - `node_modules`
    - `.angular/cache`
- Triggered only if `web-app/` was modified

---

## 🚀 Example Workflow Behavior

### ✅ Feature Branch Push:
- Only changed services are built with `mvn clean package -DskipTests`
- Angular is built only if `web-app/` is touched

### ✅ Pull Request to `develop` or `main`:
- Services are built with `mvn clean verify` (includes tests)
- Angular build includes `ng lint`

---

## 🧪 Future Enhancements
- Add test coverage reporting
- Add deployment workflow (optional)
- Add Slack/email notifications per build
- Use job matrix for parallel frontend builds

---

For questions or improvements, feel free to open an issue or PR! 😄

