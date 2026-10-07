# Virusi Mbaya Launcher

A premium-style Android launcher project designed as a real APK-ready app.

## Build

1. Open the `android/` folder in Android Studio.
2. Let Gradle sync.
3. Select Build > Build APK(s).
4. APK output: `android/app/build/outputs/apk/debug/app-debug.apk`

## Features

- Premium dark phone UI
- Theme switching
- App grid with search
- Live clock
- App launch mockup
- Android-style launcher design

## Architecture

```mermaid
flowchart LR
    A[User] --> B[MainActivity]
    B --> C[Theme Manager]
    B --> D[AppAdapter]
    C --> E[Neon Pulse]
    C --> F[Aurora Glow]
    C --> G[Voltage Gold]
    D --> H[App Grid]
    H --> I[Launch Action]
```
