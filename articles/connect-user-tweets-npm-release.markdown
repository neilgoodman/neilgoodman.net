Title: connect-user-tweets Released on NPM
Author: Neil Goodman
Categories: Releases
Date: Mon Aug 12 2013 22:07:34 GMT-0500 (CDT)

I just released my first NPM package. It provides a way to fetch tweets for a given user and cache them on a [Connect](http://www.senchalabs.org/connect/) based server. This module is being used on this site to provide the `Recent Tweets` sidebar on desktop and tablet resolutions.

##Install

To install simply install [Node.js](http://nodejs.org/) and NPM and run the following command in your project's directory:

`npm install connect-user-tweets`

##Usage

Here is a simple example to configure the middleware:

<connect-user-tweets-npm-release/usage-example.js>

##Source

The source code is available on GitHub here: [https://github.com/posco2k8/connect-user-tweets](https://github.com/posco2k8/connect-user-tweets)

If you have any questions or comments please let me know. If you find any issues, please make a bug report here: [https://github.com/posco2k8/connect-user-tweets/issues](https://github.com/posco2k8/connect-user-tweets/issues)

I plan to make a simple jQuery plugin that will fetch and render the tweets. I will probably release that sometime soon on [Bower](http://bower.io/) so that I can get some experience releasing on that platform.