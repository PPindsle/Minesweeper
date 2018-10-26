var symEntered = false;
var equation = "";
var startOver = false;
var sum = 0;

function insertNum(num) {
  var display = document.getElementById('display');
  var topDisplay = document.getElementById('topDisplay');
  var comma = false;

  if (num != ".") {
    if (display.innerHTML == "0" || symEntered || startOver) {
      if (startOver) {
        topDisplay.innerHTML = "";
        startOver = false;
      }
      display.innerHTML = num;
      symEntered = false;
    } else {
      display.innerHTML += num;
    }
  } else {
    for (var i = 0; i < display.innerHTML.length; i++) {
      if (display.innerHTML.charAt(i) == ".") {
        comma = true;
        break;
      }
    }
    if (!comma) {
      display.innerHTML += num;
    }
  }
}

function insertSym(sym) {
  var display = document.getElementById('display').innerHTML;
  var topDisplay = document.getElementById('topDisplay');
  if (display != "") {
    var string = display + " " + sym;
    topDisplay.innerHTML += " " + string;
    symEntered = true;
    clearDisplay();
  }
}

function backSpace() {
  var display = document.getElementById('display');
  display.innerHTML = display.innerHTML.substring(0, display.innerHTML.length - 1);
}

function equals() {
  var display = document.getElementById('display');
  var topDisplay = document.getElementById('topDisplay');
  if (display.innerHTML != "" && topDisplay.innerHTML != "") {
    topDisplay.innerHTML += " " + display.innerHTML;
    clearDisplay();
    equation = topDisplay.innerHTML;
    startOver = true;
    calculate();
  }
}

function calculate() {
  var display = document.getElementById('display');
  cleanEquation();
  console.log(equation);
  sum = eval(equation);
  sum = Math.round(sum * 100) / 100;
  display.innerHTML = sum;
}

function clearAllDisplays() {
  var display = document.getElementById('display');
  var topDisplay = document.getElementById('topDisplay');
  equation = "";
  clearDisplay();
  topDisplay.innerHTML = "";
}

function clearDisplay() {
  var display = document.getElementById('display');
  display.innerHTML = "";
}

function cleanEquation() {
  for (var i = 0; i < equation.length; i++) {
    var char = equation.charAt(i);
    if (char == '÷') {
      equation = replace(equation, i, '/');
    } else if (char == '×') {
      equation = replace(equation, i, '*');
    }
  }
}

function replace(string, index, replacement) {
  return string.substring(0, index) + replacement + string.substring(index + 1);
}
