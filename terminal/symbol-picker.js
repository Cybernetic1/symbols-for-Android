#!/usr/bin/env node

'use strict';

var childProcess = require('child_process');

var CATEGORIES = [
    { name: 'Arrows', symbols: ['←', '→', '↑', '↓', '↔', '↕', '⇐', '⇒', '⇑', '⇓', '⇔', '⇕', '↖', '↗', '↘', '↙', '⟵', '⟶', '⟷', '↦', '↣', '↤', '⇀', '↼'] },
    { name: 'Operators', symbols: ['±', '×', '÷', '∓', '∗', '∘', '∙', '√', '∛', '∜', '∑', '∏', '∫', '∬', '∭', '∮', '∂', '∇', '∆', '∞', '⊕', '⊗', '⊙', '⊖'] },
    { name: 'Relations', symbols: ['=', '≠', '≈', '≡', '≢', '≤', '≥', '<', '>', '≪', '≫', '∝', '∼', '≃', '≅', '≆', '≇', '≉', '≐', '≔', '≕', '⊂', '⊃', '⊆', '⊇', '∈', '∉', '∋'] },
    { name: 'Greek', symbols: ['α', 'β', 'γ', 'δ', 'ε', 'ζ', 'η', 'θ', 'ι', 'κ', 'λ', 'μ', 'ν', 'ξ', 'ο', 'π', 'ρ', 'σ', 'τ', 'υ', 'φ', 'χ', 'ψ', 'ω', 'ϵ', 'ϑ', 'ϕ', 'ϖ'] },
    { name: 'GREEK', symbols: ['Α', 'Β', 'Γ', 'Δ', 'Ε', 'Ζ', 'Η', 'Θ', 'Ι', 'Κ', 'Λ', 'Μ', 'Ν', 'Ξ', 'Ο', 'Π', 'Ρ', 'Σ', 'Τ', 'Υ', 'Φ', 'Χ', 'Ψ', 'Ω'] },
    { name: 'Letters', symbols: ['ℵ', 'ℶ', 'ℷ', 'ℸ', 'ℏ', 'ℑ', 'ℜ', '℘', 'ℓ', 'ℕ', 'ℙ', 'ℚ', 'ℝ', 'ℤ', 'ℂ', '∀', '∃', '∄', '∅', '∂'] },
    { name: 'Logic', symbols: ['∧', '∨', '¬', '⊤', '⊥', '⊢', '⊣', '⊨', '∀', '∃', '∄', '⟹', '⟺', '∴', '∵', '⋀', '⋁', '⋂', '⋃'] },
    { name: 'Misc', symbols: ['°', '′', '″', '‴', '∠', '∡', '⊥', '∥', '⌈', '⌉', '⌊', '⌋', '〈', '〉', '‖', '†', '‡', '•', '◦', '⋯', '⋮', '⋰', '⋱', '…'] },
    { name: 'Chinese', symbols: ['，', '。', '、', '；', '：', '？', '！', '（', '）', '［', '］', '【', '】', '《', '》', '〈', '〉', '「', '」', '『', '』', '“', '”', '‘', '’', '——', '……', '·'] }
];

var ANSI = {
    clear: '\u001b[2J\u001b[H',
    hideCursor: '\u001b[?25l',
    showCursor: '\u001b[?25h',
    reset: '\u001b[0m',
    bold: '\u001b[1m',
    dim: '\u001b[2m',
    inverse: '\u001b[7m',
    cyan: '\u001b[36m',
    yellow: '\u001b[33m',
    red: '\u001b[31m'
};

var state = {
    categoryIndex: 0,
    symbolIndex: 0,
    status: 'Ready. Press Enter to copy the highlighted symbol.',
    statusColor: ANSI.cyan
};

function getColumns() {
    var availableWidth = Math.max(40, process.stdout.columns || 80) - 4;
    return Math.max(4, Math.min(10, Math.floor(availableWidth / 6)));
}

function getCurrentCategory() {
    return CATEGORIES[state.categoryIndex];
}

function clampSymbolIndex() {
    var category = getCurrentCategory();
    if (state.symbolIndex < 0) {
        state.symbolIndex = 0;
    }
    if (state.symbolIndex >= category.symbols.length) {
        state.symbolIndex = category.symbols.length - 1;
    }
}

function setCategory(nextCategoryIndex, nextSymbolIndex) {
    state.categoryIndex = nextCategoryIndex;
    state.symbolIndex = nextSymbolIndex;
    clampSymbolIndex();
}

function moveHorizontal(delta) {
    var nextIndex = state.symbolIndex + delta;

    if (nextIndex >= 0 && nextIndex < getCurrentCategory().symbols.length) {
        state.symbolIndex = nextIndex;
        return;
    }

    if (nextIndex < 0 && state.categoryIndex > 0) {
        setCategory(state.categoryIndex - 1, CATEGORIES[state.categoryIndex - 1].symbols.length - 1);
        return;
    }

    if (nextIndex >= getCurrentCategory().symbols.length && state.categoryIndex < CATEGORIES.length - 1) {
        setCategory(state.categoryIndex + 1, 0);
    }
}

function moveVertical(deltaRows) {
    var columns = getColumns();
    var targetIndex = state.symbolIndex + (deltaRows * columns);

    if (targetIndex >= 0 && targetIndex < getCurrentCategory().symbols.length) {
        state.symbolIndex = targetIndex;
        return;
    }

    if (targetIndex < 0 && state.categoryIndex > 0) {
        setCategory(state.categoryIndex - 1, CATEGORIES[state.categoryIndex - 1].symbols.length + targetIndex);
        return;
    }

    if (targetIndex >= getCurrentCategory().symbols.length && state.categoryIndex < CATEGORIES.length - 1) {
        setCategory(state.categoryIndex + 1, targetIndex - getCurrentCategory().symbols.length);
    }
}

function centerCell(symbol) {
    if (symbol.length === 1) {
        return '  ' + symbol + '  ';
    }
    if (symbol.length === 2) {
        return ' ' + symbol + '  ';
    }
    return (' ' + symbol + '    ').slice(0, 5);
}

function getVisibleCategoryWindow() {
	// Note: window size changed to 9 to show all categories
    var radius = 2;
    var start = Math.max(0, state.categoryIndex - radius);
    var end = Math.min(CATEGORIES.length, start + 9);

    if (end - start < 9) {
        start = Math.max(0, end - 9);
    }

    return CATEGORIES.slice(start, end).map(function (category, index) {
        var absoluteIndex = start + index;
        if (absoluteIndex === state.categoryIndex) {
            return ANSI.inverse + ' ' + category.name + ' ' + ANSI.reset;
        }
        return category.name;
    }).join('  ');
}

function render() {
    var category = getCurrentCategory();
    var columns = getColumns();
    var rows = [];
    var i;

    for (i = 0; i < category.symbols.length; i += columns) {
        rows.push(category.symbols.slice(i, i + columns));
    }

    var output = [];
    output.push(ANSI.clear + ANSI.hideCursor);
    output.push(ANSI.bold + 'Math Symbols Terminal Picker' + ANSI.reset);
    output.push('');
    output.push('Arrow keys move  ' + ANSI.bold + 'Enter' + ANSI.reset + ' copies  ' + ANSI.bold + 'Esc/q' + ANSI.reset + ' quits');
    output.push('');
    output.push(getVisibleCategoryWindow());
    output.push('');
    output.push(ANSI.bold + category.name + ANSI.reset + '  ' + ANSI.dim + '(' + (state.categoryIndex + 1) + '/' + CATEGORIES.length + ')' + ANSI.reset);
    output.push('');

    rows.forEach(function (row, rowIndex) {
        var renderedRow = row.map(function (symbol, columnIndex) {
            var absoluteIndex = rowIndex * columns + columnIndex;
            var cell = centerCell(symbol);

            if (absoluteIndex === state.symbolIndex) {
                return ANSI.inverse + cell + ANSI.reset;
            }

            return ' ' + cell;
        }).join(' ');

        output.push(renderedRow);
    });

    output.push('');
    output.push(state.statusColor + state.status + ANSI.reset);

    process.stdout.write(output.join('\n'));
}

function tryClipboardCommand(command, args, text) {
    var result = childProcess.spawnSync(command, args, {
        input: text,
        stdio: ['pipe', 'ignore', 'ignore']
    });

    return !result.error && result.status === 0;
}

function copyToClipboard(text) {
    var commands = [
        { command: 'pbcopy', args: [] },
        { command: 'wl-copy', args: [] },
        { command: 'xclip', args: ['-selection', 'clipboard'] },
        { command: 'xsel', args: ['--clipboard', '--input'] },
        { command: 'clip.exe', args: [] },
        { command: 'clip', args: [] }
    ];

    for (var i = 0; i < commands.length; i += 1) {
        if (tryClipboardCommand(commands[i].command, commands[i].args, text)) {
            return true;
        }
    }

    return false;
}

function updateStatus(message, color) {
    state.status = message;
    state.statusColor = color;
}

function copyCurrentSymbol() {
    var symbol = getCurrentCategory().symbols[state.symbolIndex];

    if (copyToClipboard(symbol)) {
        updateStatus('Copied: ' + symbol, ANSI.yellow);
        return;
    }

    updateStatus('Could not access the clipboard. Install wl-copy, xclip, or xsel on Linux.', ANSI.red);
}

function cleanupAndExit(exitCode) {
    process.stdout.write(ANSI.reset + ANSI.showCursor + '\n');
    if (process.stdin.isTTY) {
        process.stdin.setRawMode(false);
    }
    process.stdin.pause();
    process.exit(exitCode);
}

function handleInput(buffer) {
    var key = buffer.toString('utf8');

    if (key === '\u001b' || key === 'q') {
        cleanupAndExit(0);
        return;
    }

    if (key === '\r') {
        copyCurrentSymbol();
        render();
        return;
    }

    if (key === '\u001b[D') {
        moveHorizontal(-1);
    } else if (key === '\u001b[C') {
        moveHorizontal(1);
    } else if (key === '\u001b[A') {
        moveVertical(-1);
    } else if (key === '\u001b[B') {
        moveVertical(1);
	// Page Up or Ctrl-Left 
    } else if (key === '\u001b[5~' || key === '\u001b[1;5D') {
		if (state.categoryIndex > 0) 
			setCategory(state.categoryIndex - 1, 0);
		else
			setCategory(CATEGORIES.length - 1);
	// Page Down or Ctrl-Right
    } else if (key === '\u001b[6~' || key === '\u001b[1;5C') {
		if (state.categoryIndex < CATEGORIES.length - 1)
			setCategory(state.categoryIndex + 1, 0);
		else
			setCategory(0, 0);
    }

    render();
}

function main() {
    if (!process.stdin.isTTY || !process.stdout.isTTY) {
        console.error('This picker must be run in an interactive terminal.');
        process.exit(1);
    }

    process.stdin.setEncoding('utf8');
    process.stdin.setRawMode(true);
    process.stdin.resume();
    process.stdin.on('data', handleInput);
    process.stdout.on('resize', render);
    process.on('SIGINT', function () {
        cleanupAndExit(0);
    });
    process.on('exit', function () {
        process.stdout.write(ANSI.reset + ANSI.showCursor);
    });

    render();
}

main();
