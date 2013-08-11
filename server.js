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
        'cordova-html5-tutorial-1': '2012/07/15/building-an-ios-and-android-html5-app-with-cordova-part-1',
        'thoughts-on-html5-based-mobile-apps': '2012/07/01/thoughts-on-html5-based-mobile-apps',
        'five-important-things-in-web-development': '2012/05/05/five-important-things-to-know-in-web-development',
        'responsive-design-for-the-blog': '2012/03/18/new-responsive-design-for-the-blog',
        'android-fragments-2': '2012/03/12/working-with-fragments-on-android-part-2',
        'life-updates': '2012/03/11/life-updates-first-of-many',
        'android-fragments-1': '2012/01/29/working-with-fragments-on-android-part-1',
        'social-button-alignment': '2012/01/14/making-social-buttons-line-up-in-a-row-in-css',
        'stack-overflow-comments-in-wordpress': '2012/01/07/building-stack-overflow-style-code-blocks-on-wordpress',
        'android-rest-client-2': '2012/01/01/modern-techniques-for-implementing-rest-clients-on-android-4-0-and-below-part-2/',
        'android-2-1-setimageuri': '2011/12/28/using-imageviews-setimageuri-method-on-android-2-1',
        'android-rest-client-1': '2011/12/26/modern-techniques-for-implementing-rest-clients-on-android-4-0-and-below-part-1',
        'welcome': '2011/12/24/welcome-to-the-blog'
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