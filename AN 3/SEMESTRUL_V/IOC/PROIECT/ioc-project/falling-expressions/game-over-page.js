function getQueryParam(param) {
    let urlParams = new URLSearchParams(window.location.search);
    return urlParams.get(param);
}

document.addEventListener('DOMContentLoaded', function () {
    let score = getQueryParam('score');
    let scoreText = document.getElementById('scoreText');
    if (score !== null) {
        scoreText.textContent += score;
    }
    if (getQueryParam('backgroundColor')) {
        document.body.style.backgroundColor = getQueryParam('backgroundColor');
    }
});

function redirectToFallingExpressionsGamePage() {
    window.location.href = 'falling-expressions.html?difficulty=' + encodeURIComponent(getQueryParam('difficulty') * 1000) +
        '&lives=' + encodeURIComponent(getQueryParam('lives')) +
        '&backgroundColor=' + encodeURIComponent(getQueryParam('backgroundColor')) +
        '&game-mode=' + encodeURIComponent(getQueryParam('game-mode'));
}

function redirectToFallingExpressionsMenuPage() {
    window.location.href = 'falling-expressions-menu.html';
}