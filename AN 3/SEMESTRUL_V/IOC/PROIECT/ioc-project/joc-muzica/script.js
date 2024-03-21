const notes = [
    { note: 'DO', image: 'notes\\do.png' },
    { note: 'RE', image: 'notes\\re.png' },
    { note: 'MI', image: 'notes\\mi.png' },
    { note: 'FA', image: 'notes\\fa.png' },
    { note: 'SOL', image: 'notes\\sol.png' },
    { note: 'LA', image: 'notes\\la.png' },
    { note: 'SI', image: 'notes\\si.png' }
];

let round = 1;
let score = 0;
let usedImages = [];

function startGame() {
    round = 1;
    score = 0;
    usedImages = [];

    const instructionsContainer = document.getElementById('instructions-container');
    const startBtn = document.getElementById('start-btn');
    const noteContainer = document.getElementById('note-container');
    const noteImage = document.getElementById('note-image');
    const optionsContainer = document.getElementById('options-container');
    const nextRoundBtn = document.getElementById('next-round-btn');

    // Ascunde instrucțiunile și butonul "Start"
    instructionsContainer.style.display = 'none';
    startBtn.style.display = 'none';
    noteContainer.style.display = 'block';
    noteImage.style.display = 'block';
    optionsContainer.style.display = 'block';
    nextRoundBtn.style.display = 'block';

    // Începe prima rundă
    startRound();
}



function startRound() {
    const finalScoreContainer = document.getElementById('final-score-container');
    const noteImage = document.getElementById('note-image');
    const optionsContainer = document.getElementById('options-container');
    const nextRoundBtn = document.getElementById('next-round-btn');
    const noteContainer = document.getElementById('note-image-container');

    // Ascunde containerul scorului final și afișează elementele jocului
    finalScoreContainer.style.display = 'none';
    noteContainer.style.display = 'block';
    noteImage.style.display = 'block';
    optionsContainer.style.display = 'block';
    nextRoundBtn.style.display = 'none';


    const availableNotes = notes.filter(note => !usedImages.includes(note.image));
    if (availableNotes.length === 0) {
        // Toate imaginile au fost folosite, resetează lista
        usedImages = [];
    }

    // Alege o notă aleatorie pentru fiecare rundă
    const randomNote = availableNotes[Math.floor(Math.random() * availableNotes.length)];
    noteImage.src = randomNote.image;
    noteImage.alt = randomNote.note;

    optionsContainer.innerHTML = '';

    // Generează o listă de variante de răspuns
    const shuffledNotes = shuffleArray(notes);
    for (const note of shuffledNotes) {
        const optionElement = document.createElement('div');
        optionElement.textContent = note.note;
        optionElement.classList.add('note-option');
        optionElement.addEventListener('click', () => checkAnswer(optionElement, note.note, nextRoundBtn));
        optionsContainer.appendChild(optionElement);
    }

    // Ascunde butonul "Următoarea Rundă" până când este selectată o notă
    nextRoundBtn.style.display = 'none';

    // Adaugă imaginea folosită în lista de imagini deja utilizate
    usedImages.push(randomNote.image);
}

function checkAnswer(optionElement, selectedNote, nextRoundBtn) {
    // Deselectează toate opțiunile și evidențiază opțiunea selectată
    const options = document.querySelectorAll('.note-option');
    options.forEach(option => {
        option.classList.remove('selected');
    });
    optionElement.classList.add('selected');
    // Afișează butonul "Următoarea Rundă"
    nextRoundBtn.style.display = 'inline';

    // Incrementarea punctajului dacă nota a fost recunoscută corect
    if (selectedNote === document.getElementById('note-image').alt) {
        score++;
    }
}

function nextRound() {
    // Trecerea la următoarea rundă sau afișarea scorului final
    if (round < 5) {
        round++;
        console.log(round);
        startRound();
    } else {
        showFinalScore();
    }
}

function showFinalScore() {
    const finalScoreContainer = document.getElementById('final-score-container');
    const noteImage = document.getElementById('note-image');
    const optionsContainer = document.getElementById('options-container');
    const nextRoundBtn = document.getElementById('next-round-btn');
    const finalScore = document.getElementById('final-score');
    const restartBtn = document.getElementById('restart-btn');
    const noteContainer = document.getElementById('note-image-container');


    // Ascunde elementele jocului și afișează containerul scorului final
    noteImage.style.display = 'none';
    noteContainer.style.display = 'none';
    optionsContainer.style.display = 'none';
    nextRoundBtn.style.display = 'none';
    finalScoreContainer.style.display = 'block';

    if (score === 5) {
        finalScore.innerHTML = `Felicitări! Ai recunoscut toate notele muzicale!`;
        finalScore.style.color = '#008000';
        finalScore.style.fontWeight = 'bold';
    }
    else if (score > 3) {
        finalScore.textContent = `Felicitări! Ai recunoscut ${score} note muzicale!`;
        finalScore.style.color = '#008000';
        finalScore.style.fontWeight = 'bold';
    } else if (score == 0) {
        finalScore.innerHTML = `Nu ai recunoscut nicio notă muzicală!<br> Încearcă din nou!`;
        finalScore.style.color = '#8b0000';
        finalScore.style.fontWeight = 'bold';
    }
    else if (score == 1) {
        finalScore.innerHTML = `Ai recunoscut doar ${score} notă muzicală!<br> Încearcă din nou!`;
        finalScore.style.color = '#8b0000';
        finalScore.style.fontWeight = 'bold';
    }
    else {
        finalScore.innerHTML = `Ai recunoscut doar ${score} note muzicale!<br> Încearcă din nou!`;
        finalScore.style.color = '#8b0000';
        finalScore.style.fontWeight = 'bold';
    }
    restartBtn.style.display = 'inline';
}

function restartGame() {
    round = 1;
    score = 0;
    const finalScoreContainer = document.getElementById('final-score-container');
    finalScoreContainer.style.display = 'none';
    const instructionsContainer = document.getElementById('instructions-container');
    instructionsContainer.style.display = 'block';
    const startBtn = document.getElementById("start-btn")
    startBtn.style.display = 'inline';
    // startRound();
}

function shuffleArray(array) {
    const shuffled = array.slice();
    for (let i = shuffled.length - 1; i > 0; i--) {
        const j = Math.floor(Math.random() * (i + 1));
        [shuffled[i], shuffled[j]] = [shuffled[j], shuffled[i]];
    }
    return shuffled;
}

// Inițierea primului joc
startRound();