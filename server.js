/**
 * Server for neilgoodman.net.
 */

var wheat = require('wheat'),
    http = require('http');

http
    .createServer(wheat(process.env.PWD))
    .listen(8888);
