//= require jquery-1.9.1
//= require underscore
//= require prettify
//= require moment
//= require spin
//= require bootstrap/bootstrap-affix
//= require bootstrap/bootstrap-alert
//= require bootstrap/bootstrap-button
//= require bootstrap/bootstrap-carousel
//= require bootstrap/bootstrap-collapse
//= require bootstrap/bootstrap-dropdown
//= require bootstrap/bootstrap-modal
//= require bootstrap/bootstrap-tooltip
//= require bootstrap/bootstrap-popover
//= require bootstrap/bootstrap-scrollspy
//= require bootstrap/bootstrap-tab
//= require bootstrap/bootstrap-transition
//= require bootstrap/bootstrap-typeahead

// Prettify
$(function () {
    "use strict";

    prettyPrint();
});

// Twitter integration
$(function () {
    "use strict";

    setupSpinner();

    // Templates
    var tweetListTemplate = _.template($('[data-template-name="tweet-list"]').html()),
        success = function (tweets) {
            var $tweetList = $($.parseHTML(tweetListTemplate({ tweets: tweets })));

            $('.tweet-list')
                .empty()
                .append($tweetList);

            _.defer(function () {
                $tweetList.addClass('in');
            });
        };

    // Request tweets through local proxy.
    $.get('/tweets', success, 'json');
});

// Client-side template helpers
function parseTweet(tweet) {
    var userNamesPattern = /@([A-Za-z0-9_]{1,15})/gi,
        linkPattern = /(http:\/\/[^" ]+)/gi,
        hashtagPattern = /#([^" ]+)/gi;

    tweet = tweet.replace(linkPattern, '<a target="_blank" href="$1">$&</a>');
    tweet = tweet.replace(hashtagPattern, '<a target="_blank" href="//twitter.com/search?q=%23$1&amp;src=hash">$&</a>')
    tweet = tweet.replace(userNamesPattern, '<a target="_blank" href="//twitter.com/$1">$&</a>');

    return tweet;
}

function setupSpinner() {
    var opts = {
        lines: 13, // The number of lines to draw
        length: 6, // The length of each line
        width: 2, // The line thickness
        radius: 5, // The radius of the inner circle
        corners: 1, // Corner roundness (0..1)
        rotate: 0, // The rotation offset
        direction: 1, // 1: clockwise, -1: counterclockwise
        color: '#000', // #rgb or #rrggbb
        speed: 1, // Rounds per second
        trail: 60, // Afterglow percentage
        shadow: false, // Whether to render a shadow
        hwaccel: false, // Whether to use hardware acceleration
        className: 'spinner', // The CSS class to assign to the spinner
        zIndex: 2e9, // The z-index (defaults to 2000000000)
        top: 'auto', // Top position relative to parent in px
        left: 'auto' // Left position relative to parent in px
    };

    var target = $('.tweet-list')[0];
    var spinner = new Spinner(opts).spin(target);
}