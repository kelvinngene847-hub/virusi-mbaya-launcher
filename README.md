# Virusi Mbaya Launcher

A Java-based custom theme launcher mockup with a vivid neon interface, app tiles, and theme switching.

## Architecture Overview

```mermaid
flowchart LR
    A[User] --> B[Launcher Shell]
    B --> C[Theme Manager]
    B --> D[Search & Navigation]
    B --> E[App Grid]
    C --> F[Color Palette]
    C --> G[Wallpaper Style]
    D --> H[Quick Actions]
    E --> I[Messages]
    E --> J[Camera]
    E --> K[Music]
    E --> L[Settings]
    E --> M[Gallery]
    E --> N[Contacts]
```

## Features

- Custom theme switching
- Rounded app tiles
- Search panel
- Neon/glass modern styling
- Java Swing interface

## Run

```bash
mvn clean package
java -jar target/virusi-mbaya-launcher-1.0.0.jar
```

Or directly:

```bash
mvn exec:java
```
