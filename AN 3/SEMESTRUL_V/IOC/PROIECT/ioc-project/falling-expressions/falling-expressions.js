let moveLeft = false;
let moveRight = false;
let isMoving = false;
let arithmeticalExpressions;
let expressions = [];
let score = 0;
let currentExpression;
let currentNumberImage;
let scoreImage;
let nrLives = 3;
let lives = 3;
let nrLivesImage;
let backgroundColor = "#4ca8d0";
let difficulty = 0.002;
let gameControlOption = 1;

function getQueryParam(param) {
    let urlParams = new URLSearchParams(window.location.search);
    return urlParams.get(param);
}

document.addEventListener('DOMContentLoaded', function () {
    if (getQueryParam('backgroundColor')) {
        document.body.style.backgroundColor = getQueryParam('backgroundColor');
    }
});

function updateImagePosition(boxImg, initialStageWidth, initialStageHeight) {

    let x = boxImg.x() + boxImg.width();
    let y = boxImg.y() + boxImg.height();
    const newSize = calculateAspectRatioFit(
        boxImg.image().naturalWidth,
        boxImg.image().naturalHeight,
        window.innerWidth * 0.2,
        window.innerHeight * 0.2
    );
    let newX = (x / initialStageWidth) * window.innerWidth - newSize.width;
    let newY = (y / initialStageHeight) * window.innerHeight - newSize.height;
    boxImg.x(newX);
    boxImg.width(newSize.width);
    boxImg.y(newY);
    boxImg.height(newSize.height);
}

function calculateAspectRatioFit(srcWidth, srcHeight, maxWidth, maxHeight) {
    const ratio = Math.min(maxWidth / srcWidth, maxHeight / srcHeight);
    return {width: srcWidth * ratio, height: srcHeight * ratio};
}

function createStage() {
    return new Konva.Stage({
        container: 'container',
        width: window.innerWidth,
        height: window.innerHeight,
    });
}

function createDigit(imagePath, percentage) {
    let imgObj = new Image();
    imgObj.src = imagePath

    const initialSizeImg = calculateAspectRatioFit(
        80,
        80,
        window.innerWidth * percentage,
        window.innerHeight * percentage
    );

    return new Konva.Image({
        image: imgObj,
        x: 20,
        y: 20,
        width: initialSizeImg.width,
        height: initialSizeImg.height
    });
}

function createBox(imagePath, percentage, stage) {
    let imgObj = new Image();
    imgObj.src = imagePath

    const initialSizeImg = calculateAspectRatioFit(
        80,
        80,
        window.innerWidth * percentage,
        window.innerHeight * percentage
    );

    return new Konva.Image({
        image: imgObj,
        x: stage.width() / 2 - initialSizeImg.width,
        y: stage.height() - initialSizeImg.height,
        width: initialSizeImg.width,
        height: initialSizeImg.height
    });
}

function createHeartImage(imagePath, percentage) {
    let imgObj = new Image();
    imgObj.src = imagePath

    const initialSizeImg = calculateAspectRatioFit(
        80,
        80,
        window.innerWidth * percentage,
        window.innerHeight * percentage
    );

    return new Konva.Image({
        image: imgObj,
        width: initialSizeImg.width,
        height: initialSizeImg.height
    });
}

function createLeftArrow(imagePath, percentage, stage) {
    let imgObj = new Image();
    imgObj.src = imagePath;
    const initialSizeImg = calculateAspectRatioFit(
        80,
        80,
        window.innerWidth * percentage,
        window.innerHeight * percentage
    );

    return new Konva.Image({
        image: imgObj,
        x: stage.width() / 2 - stage.width() / 3 - initialSizeImg.width,
        y: stage.height() - initialSizeImg.height,
        width: initialSizeImg.width,
        height: initialSizeImg.height
    });
}

function createRightArrow(imagePath, percentage, stage) {
    let imgObj = new Image();
    imgObj.src = imagePath

    const initialSizeImg = calculateAspectRatioFit(
        80,
        80,
        window.innerWidth * percentage,
        window.innerHeight * percentage
    );

    return new Konva.Image({
        image: imgObj,
        x: stage.width() / 2 + stage.width() / 3,
        y: stage.height() - initialSizeImg.height,
        width: initialSizeImg.width,
        height: initialSizeImg.height
    });
}

function resizeExpressions(layer, expressions) {
    let x = window.innerWidth * 0.01;
    for (let i = 0; i < arithmeticalExpressions.length; i++) {
        let images = getImagesFromArithmeticalExpression(arithmeticalExpressions[i][0]);
        let expressionImage = getGroupImage(images, x, 20);
        x += expressionImage.width() + window.innerWidth * 0.2;
        expressions.push(expressionImage);
    }
    expressions.forEach(function (expression) {
        layer.add(expression);
    });
}

function addWindowEvents(stage, layer, box, objects, expressions) {
    window.addEventListener('keydown', function (e) {
        if (e.key === 'ArrowLeft') {
            if (box.x() - window.innerWidth * 0.02 >= 0) {
                box.x(box.x() - innerWidth * 0.02);
                layer.draw();
            }
        } else if (e.key === 'ArrowRight') {
            if (box.x() + box.width() + window.innerWidth * 0.02 <= window.innerWidth) {
                box.x(box.x() + innerWidth * 0.02);
                layer.draw();
            }
        }
    });
    window.addEventListener('resize', function () {
        objects.forEach(function (o) {
            updateImagePosition(o, stage.width(), stage.height());
        });

        for (let i = 0; i < arithmeticalExpressions.length; i++) {
            expressions[i].remove();
        }
        expressions = [];
        resizeExpressions(layer, expressions);
        layer.draw();
        stage.width(window.innerWidth);
        stage.height(window.innerHeight);
    });
}

function addStageEvents(stage, box, leftArrow, rightArrow) {
    let hammerStage = new Hammer(stage.content);

    hammerStage.on('tap', function (e) {
        let touchX = e.center.x;
        let touchY = e.center.y;
        if (leftArrow.x() <= touchX && touchX <= leftArrow.x() + leftArrow.width() &&
            leftArrow.y() <= touchY && touchY <= leftArrow.y() + leftArrow.height()) {
            if (box.x() - window.innerWidth * 0.02 >= 0) {
                box.x(box.x() - window.innerWidth * 0.02);
            }
        } else if (rightArrow.x() <= touchX && touchX <= rightArrow.x() + rightArrow.width() &&
            rightArrow.y() <= touchY && touchY <= rightArrow.y() + rightArrow.height()) {
            if (box.x() + box.width() + window.innerWidth * 0.02 <= window.innerWidth) {
                box.x(box.x() + window.innerWidth * 0.02);
            }
        }
    });

    hammerStage.on('press', function (e) {
        let touchX = e.center.x;
        let touchY = e.center.y;
        if (leftArrow.x() <= touchX && touchX <= leftArrow.x() + leftArrow.width() &&
            leftArrow.y() <= touchY && touchY <= leftArrow.y() + leftArrow.height()) {
            isMoving = true;
            moveLeft = true;
        } else if (rightArrow.x() <= touchX && touchX <= rightArrow.x() + rightArrow.width() &&
            rightArrow.y() <= touchY && touchY <= rightArrow.y() + rightArrow.height()) {
            isMoving = true;
            moveRight = true;
        }
    });

    hammerStage.on('pressup', function (e) {
        let touchX = e.center.x;
        let touchY = e.center.y;
        if (leftArrow.x() <= touchX && touchX <= leftArrow.x() + leftArrow.width() &&
            leftArrow.y() <= touchY && touchY <= leftArrow.y() + leftArrow.height()) {
            isMoving = false;
            moveRight = false;
            moveLeft = false;
        } else if (rightArrow.x() <= touchX && touchX <= rightArrow.x() + rightArrow.width() &&
            rightArrow.y() <= touchY && touchY <= rightArrow.y() + rightArrow.height()) {
            isMoving = false;
            moveRight = false;
            moveLeft = false;
        }
    });
}

function buildMultiplicativeDecompositionsMap() {
    let multiplicativeDecompositionsMap = new Map();
    let decompositions;
    let i, j;
    for (i = 1; i <= 9; i++) {
        for (j = 1; j <= 9; j++) {
            if (!multiplicativeDecompositionsMap.has(i * j)) {
                multiplicativeDecompositionsMap.set(i * j, [`${i}*${j}`]);
            } else {
                decompositions = multiplicativeDecompositionsMap.get(i * j);
                decompositions.push(`${i}*${j}`);
                multiplicativeDecompositionsMap.set(i * j, decompositions);
            }
        }
    }
    return multiplicativeDecompositionsMap;
}

function generateArithmeticalExpressions(multiplicativeDecompositionsMap) {
    let arithmeticalExpressions = [];
    let operators = ['+', '-', '*'];
    let operatorIndex;
    let firstOperand;
    let secondOperand;
    let i;
    for (i = 1; i <= 5; i++) {
        operatorIndex = Math.floor(Math.random() * operators.length);
        if (operators[operatorIndex] === '+' || operators[operatorIndex] === '-') {
            firstOperand = Math.floor(Math.random() * 81) + 1;
            secondOperand = Math.floor(Math.random() * 19) + 1;
            if (operators[operatorIndex] === '+') {
                arithmeticalExpressions.push([`${Math.abs(firstOperand - secondOperand)}+${secondOperand}`, Math.abs(firstOperand - secondOperand) + secondOperand])
            } else {
                arithmeticalExpressions.push([`${firstOperand + secondOperand}-${secondOperand}`, firstOperand]);
            }
        } else if (operators[operatorIndex] === '*') {
            let randomExpressionFromMultiplicativeDecompositionsMap = getRandomExpressionFromMultiplicativeDecompositionsMap(multiplicativeDecompositionsMap);
            arithmeticalExpressions.push([randomExpressionFromMultiplicativeDecompositionsMap, eval(randomExpressionFromMultiplicativeDecompositionsMap)]);
        }
    }

    return arithmeticalExpressions;
}

function shuffle(arithmeticalExpressions) {
    for (let i = arithmeticalExpressions.length - 1; i > 0; i--) {
        const j = Math.floor(Math.random() * i);
        [arithmeticalExpressions[i], arithmeticalExpressions[j]] = [arithmeticalExpressions[j], arithmeticalExpressions[i]];
    }
    return arithmeticalExpressions;
}

function getImagesFromArithmeticalExpression(expression, x = window.innerWidth * 0.03, p = 0.025) {
    let images = [], digit;
    for (let i = 0; i < expression.length; i++) {
        if (expression[i] === '*') {
            digit = createDigit("assets/mul.png", p);
            digit.x(x);
            x += digit.width() + window.innerWidth * 0.001;
            images.push(digit);
        } else if (expression[i] === '+') {
            digit = createDigit("assets/add.png", p);
            digit.x(x);
            x += digit.width() + window.innerWidth * 0.001;
            images.push(digit);
        } else if (expression[i] === '-') {
            digit = createDigit("assets/minus.png", p)
            digit.x(x);
            x += digit.width() + window.innerWidth * 0.001;
            images.push(digit);
        } else {
            digit = createDigit(`assets/${expression[i]}.png`, p)
            digit.x(x);
            x += digit.width() + window.innerWidth * 0.001;
            images.push(digit);
        }
    }
    return images;
}

function getRandomExpressionFromMultiplicativeDecompositionsMap(multiplicativeDecompositionsMap) {
    let keys = Array.from(multiplicativeDecompositionsMap.keys());
    let randomKeyIndex = Math.floor(Math.random() * keys.length);
    let valueArray = multiplicativeDecompositionsMap.get(keys[randomKeyIndex]);
    let randomValueIndex = Math.floor(Math.random() * valueArray.length);
    return valueArray[randomValueIndex];
}

function getGroupImage(images, x, y) {
    let groupImage = new Konva.Group({
        x: x,
        y: y
    });
    images.forEach(function (image) {
        groupImage.add(image);
    });
    return groupImage;
}

function createNrLivesImage() {
    let heartImage = createHeartImage('assets/heart.png', 0.07);
    let nrLivesImages = [];
    nrLivesImages.push(heartImage);
    heartImage.x(window.innerWidth * 0.025);
    let numberImages = getImagesFromArithmeticalExpression(nrLives.toString(), window.innerWidth * 0.025 + heartImage.width(), 0.05);
    numberImages.forEach(function (image) {
        nrLivesImages.push(image);
    })
    nrLivesImage = getGroupImage(nrLivesImages, 100, 100);
    nrLivesImage.x(window.innerWidth - window.innerWidth * 0.2)
    nrLivesImage.y(20);
}

function main() {
    if (getQueryParam('lives')) {
        nrLives = getQueryParam('lives');
        lives = getQueryParam('lives');
    }
    if (getQueryParam('difficulty')) {
        difficulty = getQueryParam('difficulty') / 1000;
    }
    if (getQueryParam('game-mode')) {
        gameControlOption = parseInt(getQueryParam('game-mode'));
    }
    if (getQueryParam('backgroundColor')) {
        backgroundColor = getQueryParam('backgroundColor');
    }
    let multiplicativeDecompositionsMap = buildMultiplicativeDecompositionsMap();
    arithmeticalExpressions = generateArithmeticalExpressions(multiplicativeDecompositionsMap);
    currentExpression = arithmeticalExpressions[0];
    arithmeticalExpressions = shuffle(arithmeticalExpressions);

    let x = window.innerWidth * 0.01;
    for (let i = 0; i < arithmeticalExpressions.length; i++) {
        let images = getImagesFromArithmeticalExpression(arithmeticalExpressions[i][0]);
        let expressionImage = getGroupImage(images, x, 20);
        x += expressionImage.width() + window.innerWidth * 0.2;
        expressions.push(expressionImage);
    }

    let objects = []
    let stage = createStage();
    let layer = new Konva.Layer();
    let box = createBox('assets/box.png', 0.2, stage);

    if (gameControlOption === 1) {
        box.draggable(true);
        box.dragBoundFunc(function (pos) {
            return {
                x: pos.x,
                y: this.absolutePosition().y
            };
        });
    } else if (gameControlOption === 2) {
        let leftArrow = createLeftArrow('assets/left-arrow.png', 0.2, stage);
        let rightArrow = createRightArrow('assets/right-arrow.png', 0.2, stage);
        leftArrow.opacity(0.5);
        rightArrow.opacity(0.5);
        addStageEvents(stage, box, leftArrow, rightArrow);
        layer.add(leftArrow);
        layer.add(rightArrow);
        objects.push(leftArrow);
        objects.push(rightArrow);
    }

    let currentNumberImages = getImagesFromArithmeticalExpression(currentExpression[1].toString(), window.innerWidth * 0.03, 0.04);
    currentNumberImage = getGroupImage(currentNumberImages, 20, 20);
    let width = currentNumberImages[0].attrs.x + currentNumberImages[currentNumberImages.length - 1].attrs.x + currentNumberImages[currentNumberImages.length - 1].attrs.width;
    currentNumberImage.x(window.innerWidth / 2 - width / 2)
    currentNumberImage.y(20);

    let scoreImages = getImagesFromArithmeticalExpression(score.toString(), window.innerWidth * 0.03, 0.05);
    scoreImage = getGroupImage(scoreImages, 20, 20);
    scoreImage.x(window.innerWidth * 0.05);

    createNrLivesImage();
    layer.add(nrLivesImage);

    objects.push(box);
    box.opacity(0.6);
    layer.add(box);
    layer.add(currentNumberImage);
    layer.add(scoreImage);
    expressions.forEach(function (expression) {
        layer.add(expression);
    });
    addWindowEvents(stage, layer, box, objects, expressions);
    stage.add(layer);

    let movingBox = new Konva.Animation(function () {
        if (isMoving) {
            if (moveLeft) {
                if (box.x() - window.innerWidth * 0.02 >= 0) {
                    box.x(box.x() - innerWidth * 0.02);
                }
            } else if (moveRight) {
                if (box.x() + box.width() + window.innerWidth * 0.02 <= window.innerWidth) {
                    box.x(box.x() + innerWidth * 0.02);
                }
            }
        }

        for (let i = 0; i < expressions.length; i++) {
            if (haveIntersection(box.getClientRect(), expressions[i].getClientRect())) {
                if (arithmeticalExpressions[i][1] === currentExpression[1]) {
                    score++;
                    let scoreImages = getImagesFromArithmeticalExpression(score.toString(), window.innerWidth * 0.03, 0.05);
                    scoreImage.remove();
                    scoreImage = getGroupImage(scoreImages, 20, 20);
                    scoreImage.x(window.innerWidth * 0.05);
                    layer.add(scoreImage);
                } else {
                    nrLives--;
                    if (nrLives === 0) {
                        redirectToGameOverPage(lives, difficulty, backgroundColor, gameControlOption);
                        break;
                    }
                    nrLivesImage.remove();
                    createNrLivesImage();
                    layer.add(nrLivesImage);
                }

                for (let i = 0; i < arithmeticalExpressions.length; i++) {
                    expressions[i].remove();
                }
                expressions = [];
                arithmeticalExpressions = generateArithmeticalExpressions(multiplicativeDecompositionsMap);
                currentExpression = arithmeticalExpressions[0];
                currentNumberImage.remove();
                let currentNumberImages = getImagesFromArithmeticalExpression(currentExpression[1].toString(), window.innerWidth * 0.03, 0.04);
                currentNumberImage = getGroupImage(currentNumberImages, 20, 20);
                let width = currentNumberImages[0].attrs.x + currentNumberImages[currentNumberImages.length - 1].attrs.x + currentNumberImages[currentNumberImages.length - 1].attrs.width;
                currentNumberImage.x(window.innerWidth / 2 - width / 2)
                currentNumberImage.y(20);
                layer.add(currentNumberImage);
                arithmeticalExpressions = shuffle(arithmeticalExpressions);
                resizeExpressions(layer, expressions);
            }
        }
    }, layer);

    let movingExpressions = new Konva.Animation(function () {
        expressions.forEach(function (expression) {
            expression.y(expression.y() + innerHeight * difficulty)
        })

        if (expressions[0].attrs.y >= window.innerHeight) {
            for (let i = 0; i < arithmeticalExpressions.length; i++) {
                expressions[i].remove();
            }
            expressions = [];
            arithmeticalExpressions = generateArithmeticalExpressions(multiplicativeDecompositionsMap);
            resizeExpressions(layer, expressions);
        }
    }, layer);
    movingExpressions.start();
    movingBox.start();
}

function haveIntersection(r1, r2) {
    return !(
        r2.x > r1.x + r1.width ||
        r2.x + r2.width < r1.x ||
        r2.y > r1.y + r1.height ||
        r2.y + r2.height < r1.y
    );
}

function redirectToGameOverPage(lives, difficulty, backgroundColor, gameMode) {
    window.location.href = 'game-over-page.html?score=' + encodeURIComponent(score) +
        '&difficulty=' + encodeURIComponent(difficulty) +
        '&lives=' + encodeURIComponent(lives) +
        '&backgroundColor=' + encodeURIComponent(backgroundColor) +
        '&game-mode=' + encodeURIComponent(gameMode);
}

main();