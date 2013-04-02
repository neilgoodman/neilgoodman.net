/**
 * Server for neilgoodman.net.
 */

var wheat = require('wheat'),
    connect = require('connect'),
    http = require('http'),
    connectAssets = require('connect-assets');

var app = connect();
app
    .use(connect.compress())
    .use(connect.static(__dirname + '/skin/public'))
    .use(connectAssets({
        src: __dirname + '/skin/public'
    }))
    .use(connect.errorHandler())
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
    .listen(3000);