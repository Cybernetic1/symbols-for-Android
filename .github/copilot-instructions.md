# Copilot instructions for `symbols-for-Android`

## Build, test, and lint

- Build debug APK: `./gradlew assembleDebug`
- Clean build outputs: `./gradlew clean`
- Install debug build on a connected device/emulator: `./gradlew installDebug`
- Run unit tests: `./gradlew app:testDebugUnitTest`
- Run a single unit test class/method: `./gradlew app:testDebugUnitTest --tests "com.symbols.MainActivityTest"`
- Run lint: `./gradlew app:lint` or `./gradlew app:lintDebug`
- Run connected Android tests: `./gradlew app:connectedDebugAndroidTest`

## High-level architecture

- This is a very small Android app with one launcher activity: `com.symbols.MainActivity`.
- The UI is built entirely in Java, not XML. `MainActivity` creates a `ListView` in `onCreate()`, and each list row renders a category title plus a nested `GridView` of symbols.
- Symbol data is defined inline in `getSymbolData()` as a `LinkedHashMap<String, List<String>>`, which preserves category order in the UI.
- Tapping a symbol copies it to the clipboard and shows a toast. The app is intentionally lightweight: there is no database, network layer, fragments, or navigation stack.
- The manifest only declares the launcher activity and a simple app theme/icon.

## Key conventions

- Preserve the category order in `getSymbolData()` by keeping the `LinkedHashMap`; the UI depends on insertion order.
- Keep symbol groups explicit and readable in `MainActivity` rather than moving them into unrelated abstractions.
- Reuse the existing adapter pattern (`CategoryAdapter` + `SymbolGridAdapter`) when changing how categories or symbol cells render.
- Keep the Java 8 source/target compatibility set in `app/build.gradle`.
- Changes that affect UI behavior should account for the programmatic layout sizing in `CategoryAdapter.getView()`, especially the nested `GridView` height calculation.
