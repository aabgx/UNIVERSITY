document.addEventListener('DOMContentLoaded', function () {
    const backgroundColorPicker = document.getElementById('background-color');
    backgroundColorPicker.addEventListener('change', function () {
        document.body.style.backgroundColor = this.value;
    });
    document.getElementById('background-color').value = "#4ca8d0";
    document.body.style.backgroundColor = "#4ca8d0";
    document.getElementById('difficulty').value = 2;
    document.getElementById('lives').value = 3;
});

function onPlayButtonClicked() {
    let difficulty = document.getElementById('difficulty').value;
    let lives = document.getElementById('lives').value;
    let backgroundColor = document.getElementById('background-color').value;
    let gameMode = document.getElementById('game-mode').value;
    window.location.href = 'falling-expressions.html?difficulty=' + encodeURIComponent(difficulty) +
        '&lives=' + encodeURIComponent(lives) +
        '&backgroundColor=' + encodeURIComponent(backgroundColor) + '&game-mode=' + encodeURIComponent(gameMode);
}