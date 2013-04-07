// This is common jQuery boilerplate, but it is an event that is fired once the DOM
// has finished loading. So, this fires a callback function.
$(document).ready(function () {
    var iLiveInThisFunction = 'Value';

    $('body').on('click', function (event) {
        // iLiveInThisFunction also lives in this handler's closure, so
        // it can be safely referenced when this handler is executed at a later
        // time.
        console.log(iLiveInThisFunction);
    });
});