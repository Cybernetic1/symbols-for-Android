# Math Symbols for Android

A simple Android app for quick access to math symbols and special characters.

## Features

- Organized categories: Arrows, Operators, Relations, Greek letters, Logic, and more
- One-tap copy to clipboard
- Expandable list view showing all symbols
- Toast notification confirms copy

## Building

```bash
./gradlew assembleDebug
```

The APK will be in `app/build/outputs/apk/debug/`

## Web version

A standalone HTML/CSS/JavaScript version is available under `web/`.

- Open `web/index.html` directly in a browser for the ready-made page
- Reuse `web/symbol-picker.js` and `web/symbol-picker.css` inside another HTML app
- The component CSS is scoped to `.symbol-picker`, so it avoids styling the host page globally

Example integration:

```html
<link rel="stylesheet" href="symbol-picker.css">
<div id="math-symbols"></div>
<script src="symbol-picker.js"></script>
<script>
  SymbolsApp.createSymbolPicker(document.getElementById('math-symbols'));
</script>
```

## Categories

- **Arrows**: ←, →, ⇒, ⟶, etc.
- **Operators**: ±, ×, ÷, ∑, ∫, √, etc.
- **Relations**: =, ≠, ≈, ≤, ≥, ∈, ⊂, etc.
- **Greek (lowercase/uppercase)**: α, β, γ, Δ, Σ, Ω, etc.
- **Letter-like**: ℵ, ℏ, ℕ, ℝ, ℂ, ∅, etc.
- **Logic**: ∧, ∨, ¬, ∀, ∃, ⊢, etc.
- **Miscellaneous**: °, ′, ∠, ⌈⌉, 〈〉, etc.

## Usage

1. Install the APK on your Android device
2. Open "Math Symbols" app
3. Browse categories (all expanded by default)
4. Tap any symbol to copy it to clipboard
5. Paste anywhere you need it
