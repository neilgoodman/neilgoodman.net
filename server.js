/**
 * Server for neilgoodman.net.
 */

var less = require('less'),
    connect = require('connect'),
    connectAssets = require('connect-assets'),
    connectUserTweets = require('connect-user-tweets'),
    wheat = require('wheat'),
    dateformat = require('dateformat');

var app = connect(),
    aliases = {
        'cordova-html5-tutorial-1': '2012/07/15/building-an-ios-and-android-html5-app-with-cordova-part-1'
    },
    wheatProcess = wheat(__dirname, aliases);

app
    .use(connectAssets({
        src: __dirname + '/skin/assets'
    }))
    .use(connect.static(__dirname + '/skin/assets'))
    .use(function (req, res, next) {
        if (global.js) {
            global.js.root = '/javascripts';
        }

        if (global.css) {
            global.css.root = '/stylesheets';
        }

        wheatProcess.call(this, req, res, next);
    })
    .use(connectUserTweets({
        screen_name: 'posco2k8',
        consumer_key: process.env.TWITTER_CONSUMER_KEY,
        consumer_secret: process.env.TWITTER_CONSUMER_SECRET,
        access_token: process.env.TWITTER_ACCESS_TOKEN,
        access_token_secret: process.env.TWITTER_ACCESS_TOKEN_SECRET
    }));


if (process.env.NODE_ENV != 'production') {
    app.use(connect.errorHandler({
        showStack: true,
        dumpExceptions: true
    }));
}

app.listen(3000);