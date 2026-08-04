(function (global) {
    'use strict';

    var DEFAULT_CATEGORIES = [
        { name: 'Arrows', symbols: ['←', '→', '↑', '↓', '↔', '↕', '⇐', '⇒', '⇑', '⇓', '⇔', '⇕', '↖', '↗', '↘', '↙', '⟵', '⟶', '⟷', '↦', '↣', '↤', '⇀', '↼'] },
        { name: 'Operators', symbols: ['±', '×', '÷', '∓', '∗', '∘', '∙', '√', '∛', '∜', '∑', '∏', '∫', '∬', '∭', '∮', '∂', '∇', '∆', '∞', '⊕', '⊗', '⊙', '⊖'] },
        { name: 'Relations', symbols: ['=', '≠', '≈', '≡', '≢', '≤', '≥', '<', '>', '≪', '≫', '∝', '∼', '≃', '≅', '≆', '≇', '≉', '≐', '≔', '≕', '⊂', '⊃', '⊆', '⊇', '∈', '∉', '∋'] },
        { name: 'Greek (lowercase)', symbols: ['α', 'β', 'γ', 'δ', 'ε', 'ζ', 'η', 'θ', 'ι', 'κ', 'λ', 'μ', 'ν', 'ξ', 'ο', 'π', 'ρ', 'σ', 'τ', 'υ', 'φ', 'χ', 'ψ', 'ω', 'ϵ', 'ϑ', 'ϕ', 'ϖ'] },
        { name: 'Greek (uppercase)', symbols: ['Α', 'Β', 'Γ', 'Δ', 'Ε', 'Ζ', 'Η', 'Θ', 'Ι', 'Κ', 'Λ', 'Μ', 'Ν', 'Ξ', 'Ο', 'Π', 'Ρ', 'Σ', 'Τ', 'Υ', 'Φ', 'Χ', 'Ψ', 'Ω'] },
        { name: 'Letter-like', symbols: ['ℵ', 'ℶ', 'ℷ', 'ℸ', 'ℏ', 'ℑ', 'ℜ', '℘', 'ℓ', 'ℕ', 'ℙ', 'ℚ', 'ℝ', 'ℤ', 'ℂ', '∀', '∃', '∄', '∅', '∂'] },
        { name: 'Logic', symbols: ['∧', '∨', '¬', '⊤', '⊥', '⊢', '⊣', '⊨', '∀', '∃', '∄', '⟹', '⟺', '∴', '∵', '⋀', '⋁', '⋂', '⋃'] },
        { name: 'Miscellaneous', symbols: ['°', '′', '″', '‴', '∠', '∡', '⊥', '∥', '⌈', '⌉', '⌊', '⌋', '〈', '〉', '‖', '†', '‡', '•', '◦', '⋯', '⋮', '⋰', '⋱', '…'] },
        { name: 'Chinese punctuations', symbols: ['，', '。', '、', '；', '：', '？', '！', '（', '）', '［', '］', '【', '】', '《', '》', '〈', '〉', '「', '」', '『', '』', '“', '”', '‘', '’', '——', '……', '·'] }
    ];

    function cloneCategories(categories) {
        return categories.map(function (category) {
            return {
                name: category.name,
                symbols: category.symbols.slice()
            };
        });
    }

    function normalizeCategories(categories) {
        if (Array.isArray(categories)) {
            return cloneCategories(categories);
        }

        return Object.keys(categories).map(function (name) {
            return {
                name: name,
                symbols: categories[name].slice()
            };
        });
    }

    function fallbackCopy(text) {
        return new Promise(function (resolve, reject) {
            var textArea = document.createElement('textarea');
            textArea.value = text;
            textArea.setAttribute('readonly', 'readonly');
            textArea.style.position = 'fixed';
            textArea.style.top = '-9999px';
            document.body.appendChild(textArea);
            textArea.focus();
            textArea.select();

            var copied = document.execCommand('copy');
            document.body.removeChild(textArea);

            if (!copied) {
                reject(new Error('Copy command was rejected.'));
                return;
            }

            resolve();
        });
    }

    function copyText(text) {
        if (global.navigator && global.navigator.clipboard && typeof global.navigator.clipboard.writeText === 'function') {
            return global.navigator.clipboard.writeText(text);
        }

        return fallbackCopy(text);
    }

    function createElement(tagName, className, textContent) {
        var element = document.createElement(tagName);

        if (className) {
            element.className = className;
        }

        if (typeof textContent === 'string') {
            element.textContent = textContent;
        }

        return element;
    }

    function createStatusController(statusElement) {
        var hideTimerId = null;

        return function showStatus(message, isError) {
            statusElement.textContent = message;
            statusElement.style.color = isError ? '#b42318' : '';
            statusElement.style.background = isError ? '#fee4e2' : '';
            statusElement.classList.add('is-visible');

            if (hideTimerId !== null) {
                global.clearTimeout(hideTimerId);
            }

            hideTimerId = global.setTimeout(function () {
                statusElement.classList.remove('is-visible');
            }, 1800);
        };
    }

    function createSymbolButton(symbol, showStatus, onCopy) {
        var button = createElement('button', 'symbol-picker__button', symbol);
        button.type = 'button';
        button.setAttribute('aria-label', 'Copy ' + symbol);
        button.title = 'Copy ' + symbol;

        button.addEventListener('click', function () {
            copyText(symbol)
                .then(function () {
                    showStatus('Copied: ' + symbol, false);

                    if (typeof onCopy === 'function') {
                        onCopy(symbol);
                    }
                })
                .catch(function (error) {
                    showStatus(error.message, true);
                });
        });

        return button;
    }

    function createCategoryCard(category, showStatus, onCopy) {
        var card = createElement('section', 'symbol-picker__category');
        var title = createElement('h2', 'symbol-picker__category-title', category.name);
        var grid = createElement('div', 'symbol-picker__grid');

        category.symbols.forEach(function (symbol) {
            grid.appendChild(createSymbolButton(symbol, showStatus, onCopy));
        });

        card.appendChild(title);
        card.appendChild(grid);
        return card;
    }

    function createSymbolPicker(container, options) {
        if (!container || container.nodeType !== 1) {
            throw new Error('createSymbolPicker expects a DOM element container.');
        }

        var settings = options || {};
        var categories = normalizeCategories(settings.categories || DEFAULT_CATEGORIES);

        container.innerHTML = '';

        var root = createElement('section', 'symbol-picker');
        var header = createElement('header', 'symbol-picker__header');
        var headerText = createElement('div', 'symbol-picker__header-text');
        var heading = createElement('h1', 'symbol-picker__heading', settings.title || 'Math Symbols');
        var subtitle = createElement('p', 'symbol-picker__subtitle', settings.subtitle || 'Tap any symbol to copy it to the clipboard.');
        var status = createElement('div', 'symbol-picker__status');
        var categoriesGrid = createElement('div', 'symbol-picker__categories');
        var showStatus = createStatusController(status);

        status.setAttribute('aria-live', 'polite');

        headerText.appendChild(heading);
        headerText.appendChild(subtitle);
        header.appendChild(headerText);
        header.appendChild(status);

        categories.forEach(function (category) {
            categoriesGrid.appendChild(createCategoryCard(category, showStatus, settings.onCopy));
        });

        root.appendChild(header);
        root.appendChild(categoriesGrid);
        container.appendChild(root);

        return {
            root: root,
            categories: cloneCategories(categories)
        };
    }

    function autoMount() {
        var containers = document.querySelectorAll('[data-symbol-picker]');

        containers.forEach(function (container) {
            createSymbolPicker(container);
        });
    }

    global.SymbolsApp = {
        defaultCategories: cloneCategories(DEFAULT_CATEGORIES),
        createSymbolPicker: createSymbolPicker
    };

    if (document.readyState === 'loading') {
        document.addEventListener('DOMContentLoaded', autoMount);
    } else {
        autoMount();
    }
}(window));
