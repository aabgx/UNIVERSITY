let selectedCells = [];


let wordMatrix = [];

const wordsToFind = ['ana', 'mere', 'pa'];
let size = 5;

function generateRandomLetters() {
    const alphabet = 'abcdefghijklmnopqrstuvwxyz';

    const wordMatrix = [];
    for (let i = 0; i < size; i++) {
        const row = [];
        for (let j = 0; j < size; j++) {
            row.push('');
        }
        wordMatrix.push(row);
    }
    for (let i = 0; i < wordsToFind.length; i++) {
        let anaRow, anaCol;
        let direction = Math.floor(Math.random() * 3);
        if(wordsToFind[i] === "mere"){
            direction = Math.floor(Math.random() * 2);
        }

        switch (direction) {
            case 0:
                anaRow = Math.floor(Math.random() * size);
                anaCol = Math.floor(Math.random() * (size - wordsToFind[i].length + 1));
                while (wordMatrix[anaRow].slice(anaCol, anaCol + wordsToFind[i].length).some(cell => cell !== '')) {
                    anaRow = Math.floor(Math.random() * size);
                    anaCol = Math.floor(Math.random() * (size - wordsToFind[i].length + 1));
                }
                for (let j = 0; j < wordsToFind[i].length; j++) {
                    wordMatrix[anaRow][anaCol + j] = wordsToFind[i][j];
                }
                break;

            case 1:
                anaRow = Math.floor(Math.random() * (size - wordsToFind[i].length + 1));
                anaCol = Math.floor(Math.random() * size);
                while (wordMatrix.slice(anaRow, anaRow + wordsToFind[i].length).some(row => row[anaCol] !== '')) {
                    anaRow = Math.floor(Math.random() * (size - wordsToFind[i].length + 1));
                    anaCol = Math.floor(Math.random() * size);
                }
                for (let j = 0; j < wordsToFind[i].length; j++) {
                    wordMatrix[anaRow + j][anaCol] = wordsToFind[i][j];
                }
                break;

            case 2:
                anaRow = Math.floor(Math.random() * (size - wordsToFind[i].length + 1));
                anaCol = Math.floor(Math.random() * (size - wordsToFind[i].length + 1));
                let direction = Math.random() < 0.5 ? 1 : -1;
                while (wordMatrix.slice(anaRow, anaRow + wordsToFind[i].length).some((row, index) => row[anaCol + index * direction] !== '')) {
                    anaRow = Math.floor(Math.random() * (size - wordsToFind[i].length + 1));
                    anaCol = Math.floor(Math.random() * (size - wordsToFind[i].length + 1));
                }
                for (let j = 0; j < wordsToFind[i].length; j++) {
                    wordMatrix[anaRow + j][anaCol + j * direction] = wordsToFind[i][j];
                }
                break;
        }
    }

    for (let i = 0; i < size; i++) {
        for (let j = 0; j < size; j++) {
            if (wordMatrix[i][j] === '') {
                wordMatrix[i][j] = alphabet[Math.floor(Math.random() * alphabet.length)];
            }
        }
    }

    return wordMatrix;
}

function renderWordMatrix(matrix) {
    const wordSearchDiv = document.getElementById('wordSearch');
    wordSearchDiv.innerHTML = '';
    for (let i = 0; i < matrix.length; i++) {
        for (let j = 0; j < matrix[i].length; j++) {
            const cell = document.createElement('div');
            cell.textContent = matrix[i][j];
            cell.id = `${i}${j}`;
            cell.onclick = () => selectCell(i, j);
            wordSearchDiv.appendChild(cell);
        }
    }
}

function selectCell(row, col) {
    const cell = document.getElementById(`${row}${col}`);
    const index = selectedCells.findIndex(cell => cell.row === row && cell.col === col);
    const hasSelectedClass = cell.classList.contains('selected');
    if (wordsToFind.length !== 0) {
        if (index !== -1) {
            cell.classList.remove('selected');
            selectedCells.splice(index, 1);
        } else if (!hasSelectedClass) {
            cell.classList.add('selected');
            selectedCells.push({row, col});
        }

        checkSelectedWord();
    }
}

function checkSelectedWord() {
    const selectedWord = getSelectedWord();
    let found = false;
    if (selectedWord) {
        for (let i in wordsToFind) {
            if (selectedWord === wordsToFind[i]) {
                const isOnSameLine = checkIfOnSameLine(selectedCells);
                if(isOnSameLine) {
                    found = true;
                    selectedCells = [];
                    let indexToDelete = wordsToFind.indexOf(wordsToFind[i]);
                    wordsToFind.splice(indexToDelete, 1);
                    break;
                }else{
                    let dialogContainer = document.getElementById('dialog-container');
                    if (dialogContainer) {
                        dialogContainer.innerHTML = 'Cuvantul nu este unul bun! Cuvintele selectate trebuie sa fie pe aceeasi linie, coloana sau diagonala!' + '<br>' + 'Cuvintele cautate sunt:  ana, mere, pa';
                    }
                    clearSelection();
                }
            }
        }
        if (wordsToFind.length === 0) {
            let dialogContainer = document.getElementById('dialog-container');
            if (dialogContainer) {
                dialogContainer.innerHTML = '<h2>Yeeeee - Haaaa</h2>' +
                    '<p>Felicitari! Ai batut primul nivel!</p>' +
                    '<p>Crezi ca poti bate si uramotrul nivel?</p>' +
                '<button id = claimTreasureButton onclick="nextLevel()">Urmatorul nivel</button>';
            }

        } else {
            let nr = 0;
            if (!found) {
                for (let i in wordsToFind) {
                    if (!wordsToFind[i].startsWith(selectedWord)) {
                        nr++;
                    }
                }
                if (nr === wordsToFind.length) {
                    let dialogContainer = document.getElementById('dialog-container');
                    if (dialogContainer) {
                        dialogContainer.innerHTML = 'Cuvantul nu este unul bun! Incearca din nou!' +
                            '<br>'+ 'Cuvintele cautate sunt:  ana, mere și pa'
                    }

                    clearSelection();
                }
            }
        }
    }
}


function checkIfOnSameLine(cells) {
    const rows = cells.map(cell => cell.row);
    const cols = cells.map(cell => cell.col);

    const isOnSameRow = rows.every(row => row === rows[0]);

    const isOnSameCol = cols.every(col => col === cols[0]);

    const isOnSameDiagonal = rows.every((row, index) => {
        const rowDiff = Math.abs(row - rows[0]);
        const colDiff = Math.abs(cols[index] - cols[0]);
        return rowDiff === colDiff;
    });
    return isOnSameRow || isOnSameCol || isOnSameDiagonal;
}


function getSelectedWord() {
    if (selectedCells.length === 0) {
        return null;
    }
    let word = '';
    for (const cell of selectedCells) {
        word += wordMatrix[cell.row][cell.col];
    }
    return word;
}

function clearSelection() {
    selectedCells.forEach(cell => {
        const cellElement = document.getElementById(`${cell.row}${cell.col}`);
        cellElement.classList.remove('selected');
    });
    selectedCells = [];
}

wordMatrix = generateRandomLetters();
renderWordMatrix(wordMatrix);

function nextLevel(){
    window.location.href = '../level2/rebus2.html';
}