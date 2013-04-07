// Define a simple function.
function MyFunction () {
}

// All functions are objects in JavaScript. Each function has a special property called prototype. When
// a function is used with the new operator, the object in its prototype property is placed into
// the __proto__ property of the new object. __proto__ will contain a reference to this object,
// so a change to the MyFunction.prototype property will effect all instances of MyFunction.
MyFunction.prototype = {
    aValue: 'Hello!'
};

// You will see this line in a lot of JavaScript code. This doesn't really do anything
// useful other than support some JavaScript programmers who like to test object types by using
// the constructor property: (obj.constructor === MyFunction). This is considered bad practice
// by many JavaScript programmers because the instanceof operator is a better way make this
// type of test. Still, why not just support everyone.
MyFunction.prototype.constructor = MyFunction;

// Use the new operator to create a new object using MyFunction as a constructor.
var instance = new MyFunction();

alert(instance.aValue); // This will output 'Hello!'