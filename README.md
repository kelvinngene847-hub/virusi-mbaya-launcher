# Virusi Mbaya Launcher

A full Java Swing launcher application with a custom neon theme, app grid, search bar, theme picker, and interactive app tiles.

## Architecture Overview

```mermaid
flowchart LR
    A[User] --> B[Launcher Shell]
    B --> C[Theme Manager]
    B --> D[Search Bar]
    B --> E[App Grid]
    C --> F[Neon Theme]
    C --> G[Aurora Theme]
    C --> H[Voltage Gold Theme]
    E --> I[Messages]
    E --> J[Camera]
    E --> K[Music]
    E --> L[Settings]
    E --> M[Gallery]
    E --> N[Contacts]
```

## Features

- Custom color themes
- Rounded app tiles
- Search field and launcher look
- Real-time clock
- App launch popups
- Java Swing interface

## Run

```bash
mvn clean package
java -jar target/virusi-mbaya-launcher-1.0.0.jar
```

Or:

```bash
mvn exec:java
```
