// A basic example of a function that uses a variable defined in global scope.
var iAmAVariable = 'Storing a string';
function basicFunction() {
    console.log(iAmAVariable);
}

// A nested function example.
function nestedFunction() {
    var aLocalVariable = 'So local';

    var iAmTheNest = function () {
        console.log(aLocalVariable);
    }

    return iAmTheNest;
}

var testTheNest = nestedFunction();
testTheNest(); // This will output 'So local' on the log.