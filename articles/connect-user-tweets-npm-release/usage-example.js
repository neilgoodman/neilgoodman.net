var connect = require('connect'),
    connectUserTweets = require('connect-user-tweets');

var app = connect();

app
    .use(connectUserTweets({
        screen_name: 'posco2k8',
        count: 2, // Default. Number of tweets to return.
        cache_timeout: 5 * 60000, // Default five minutes
        consumer_key: '...', 
        consumer_secret: '...',
        access_token: '...',
        access_token_secret: '...'
    }))
    .listen(3000);