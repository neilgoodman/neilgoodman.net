var baseObject  = {},
    superObject = {
        aMethod: function () {
            alert('Hello!');
        }
    };

baseObject.__proto__ = superObject; // Only use this in WebKit or Firefox.

baseObject.aMethod(); // This will popup an alert that says hello.