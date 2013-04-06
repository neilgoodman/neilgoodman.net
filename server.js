/**
 * Server for neilgoodman.net.
 */

var less = require('less'),
    connect = require('connect'),
    connectAssets = require('connect-assets'),
    wheat = require('wheat');

var app = connect();
app
    .use(connect.compress())
    .use(connectAssets({
        src: __dirname + '/skin/assets'
    }))
    .use(function (req, res, next) {
        if (global.js) {
            global.js.root = '/javascripts';
        }

        if (global.css) {
            global.css.root = '/stylesheets';
        }
        
        var wheatProcess = wheat(__dirname);
        wheatProcess.call(this, req, res, next);
    })
    .use(connect.static(__dirname + '/skin/public'))
    .use(connect.errorHandler({
        showStack: true,
        dumpExceptions: true
    }))
    .listen(3000);