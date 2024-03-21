var playing = false;
var score;
var action;
var timeremaining;
var correctAnswer;

document.getElementById("startreset").onclick = function () {
  if (playing == true) {
    location.reload();
  } else {
    playing = true;
    score = 0;

    document.getElementById("scorevalue").innerHTML = score;
    //show count
    show("timeremaining");
    timeremaining = 60;

    document.getElementById("timeremainingvalue").innerHTML = timeremaining;

    //hide game over
    hide("gameOver");

    //change start to reset
    document.getElementById("startreset").innerHTML = "RESTART";

    //start count
    startCountdown();

    //generate question
    generateQA();
  }
};

for (i = 1; i < 5; i++) {
  document.getElementById("box" + i).onclick = function () {
    if (playing == true) {
      if (this.innerHTML == correctAnswer) {
        //increase score
        score++;
        document.getElementById("scorevalue").innerHTML = score;
        hide("wrong");
        show("correct");
        setTimeout(function () {
          hide("correct");
        }, 1000);
        generateQA();
      } else {
        //wrong answer
        hide("correct");
        show("wrong");
        setTimeout(function () {
          hide("wrong");
        }, 1000);
      }
    }
  };
}

//functions
//start count
function startCountdown() {
  action = setInterval(function () {
    timeremaining -= 1;

    document.getElementById("timeremainingvalue").innerHTML = timeremaining;
    if (timeremaining == 0) {
      stopCountdown();
      show("gameOver");

      //game over
      document.getElementById("gameOver").innerHTML =
        "<p>GATA!</p><p>SCOR: " + score + ".</p>";
      hide("timeremaining");
      hide("correct");
      hide("wrong");
      playing = false;

      document.getElementById("startreset").innerHTML = "RESTART";
    }
  }, 1000);
}

//stop count
function stopCountdown() {
  clearInterval(action);
}

//hide
function hide(Id) {
  document.getElementById(Id).style.display = "none";
}

//show
function show(Id) {
  document.getElementById(Id).style.display = "block";
}

//question
function generateQA() {
  var x = 1 + Math.round(9 * Math.random());
  var y = 1 + Math.round(9 * Math.random());
  correctAnswer = x * y;

  document.getElementById("question").innerHTML = x + "x" + y;
  var correctPosition = 1 + Math.round(3 * Math.random());

  document.getElementById("box" + correctPosition).innerHTML = correctAnswer; //correct answer

  //wrong answers
  var answers = [correctAnswer];

  for (i = 1; i < 5; i++) {
    if (i != correctPosition) {
      var wrongAnswer;
      do {
        wrongAnswer =
          (1 + Math.round(9 * Math.random())) *
          (1 + Math.round(9 * Math.random())); //wrong answer
      } while (answers.indexOf(wrongAnswer) > -1);

      document.getElementById("box" + i).innerHTML = wrongAnswer;
      answers.push(wrongAnswer);
    }
  }
}
