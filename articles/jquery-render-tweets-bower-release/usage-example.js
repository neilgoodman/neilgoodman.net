$(function () {
    // The rendered HTML will be appended to the <body> element.
    $('body').renderTweets({
        tweets: [], // Inject the tweet data directly if you have it.
        template: function (data) {}, // Specify a standard JST function to render the tweets. Data contains: { tweets: [...] }
        url: '/tweets' // The URL to lookup tweets. Can also be a function that takes in a callback as its first parameter which should be called when the request is complete.
    });
});