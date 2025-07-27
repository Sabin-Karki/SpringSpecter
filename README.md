# Spring Specter 👻

A lightweight, command-line (CLI) tool designed to scan Spring Boot projects for common security misconfigurations and dependency vulnerabilities. Spring Specter acts as an automated safety net, helping developers identify and fix issues early in the development lifecycle.

##  Overview

In the fast-paced world of software development, it's easy for small misconfigurations to slip through the cracks, leading to potential security risks. Spring Specter solves this by providing a fast, repeatable, and extensible way to analyze a project's source code, focusing on both application configuration and build dependencies.

This tool was built from scratch, emphasizing a clean, decoupled architecture based on modern Java and Spring Boot principles.

## Key Features

### 🔍 Multi-Faceted Scanning
Analyzes different aspects of a project for a holistic security overview.

### 🛠️ Configuration Analysis
- Scans both traditional `.properties` and modern `.yml` / `.yaml` configuration files.
- Detects common issues like disabled CSRF protection, disabled SSL, and default passwords.

### 📦 Build Analysis
- Parses `pom.xml` files to inspect build dependencies.
- Flags outdated and unsupported parent POM versions (e.g., Spring Boot 2.x).
- Detects specific known-vulnerable dependencies (e.g., older versions of `log4j-core`).

### 🧩 Extensible Rule Engine
- Built on a robust interface-based design (`SecurityRule`, `BuildRule`).
- Adding a new scanner rule is as simple as creating a new class, requiring no changes to the core engine.

### 📊 Clear Console Reporting
- Provides clean, color-coded output for easy identification of results.
- `[PASS]` messages are shown in green
- `[FAIL]` messages are in red, with a clear list of all vulnerabilities found.

## Prerequisites

- Java Development Kit (JDK) 17 or higher.

