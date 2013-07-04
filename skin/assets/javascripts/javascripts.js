//= require jquery-1.9.1
//= require underscore
//= require prettify
//= require moment
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

    // Templates
    var tweetListTemplate = _.template($('[data-template-name="tweet-list"]').html()),
        success = function (tweets) {
            $('.tweet-list').append($.parseHTML(tweetListTemplate({ tweets: tweets })));
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