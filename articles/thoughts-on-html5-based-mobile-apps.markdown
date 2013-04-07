Title: Thoughts on HTML5 based mobile apps
Author: Neil Goodman
Categories: Cordova
Date: Sun Jul 01 2012 07:34:00 GMT-0600 (CST)

Since the rise of mobile app development, people have been discussing HTML5 vs. native approaches. Apple initially wanted all apps to be web based, refusing to offer a native SDK. Eventually, they realized that wasn't going to be enough and offered their current Objective-C based solution. Google has offered its Java based native SDK since the beginning of Android, but they obviously have a lot to offer in the way of web SDKs.

My only exposure to mobile development has been using these native SDKs. I've read a lot about the HTML5 frameworks out there, but I've never had much of a chance to experiment with them because the clients I dealt with in the past often wanted native apps. I am currently working on some tutorials on how to get up and running with [Cordova](http://incubator.apache.org/cordova/) (formally known as PhoneGap) for both iOS and Android. There is a lot of material to cover on that topic, so I wanted to make an initial post to share some of my thoughts about the pros and cons of HTML5 mobile apps.

##Basics

The basics of a HTML5 app are fairly simple. HTML5 apps are essentially native apps that expose a single screen which contains a chrome-less web browser. A JavaScript based application is then loaded inside the web browser. The idea is that a developer can take it from there using HTML, CSS, and JavaScript. Because the browsers on Android and iOS are based on newer standards-compliant browsers (i.e. WebKit), developers can take full advantage of many of the new HTML5 and CSS3 APIs.

Cordova offers an interesting solution in that it has already built the native code you need to get up and running on all the popular mobile platforms out there. Cordova also exposes a JavaScript SDK to the browser that hooks into native code so that developers can still take advantage of lower-level APIs. Some examples of these low-level operations include interacting with a phone's camera, file system, or SQLite database. Cordova's JavaScript SDK is the same on all of the different platforms, so ideally a developer will need to only construct one JavaScript app and still be able to target multiple platforms.

Being able to target different platforms with the same code base is the biggest advantage of HTML5 apps.

##Looking past the Native Code

Once you have your projects created and can successfully load your JavaScript app in each platform you want to target, you still have a lot of work to do. When developing native apps you usually don't have to make any decisions about frameworks or layout engines. You simply use the ones that have been provided to you by the SDK. JavaScript apps are much different. If you have any experience developing single-page web apps, then you probably have some ideas of how to begin. There are many, many frameworks you can use, but I'm going to list some of the ones I'll be using in my upcoming tutorials.

- __[Twitter Bootstrap](http://twitter.github.com/bootstrap/)__: A poplar UI toolkit built in HTML5, [LESS](http://lesscss.org/) and jQuery that has been developed by Twitter. It's very lightweight and easy to modify. The core Bootstrap code is written in LESS, which is a language built to be a superset of CSS. You can use the CSS3 source files offered on Bootstrap's site, but I highly recommend learning LESS and using that instead. I'll be walking through how to get setup with LESS in my tutorials.

- __[jQuery](http://jquery.com/)__: This is definitely a no-brainer inclusion to any DOM based JavaScript project. I am also going to be playing around with replacing jQuery with <a href="http://zeptojs.com/">Zepto</a>, which is a lighter weight DOM manipulation library that tries to be compatible with jQuery.

- __[Backbone.js](http://backbonejs.org/)__: This is a very popular MVC-like framework written in JavaScript. It depends on [Underscore.js](http://underscorejs.org/) and jQuery/Zepto and provides a very simple model-view framework that interfaces with REST based APIs out of the box.

- __[Node.js](http://nodejs.org/)__: A server-side JavaScript implementation using Chrome's V8 engine that has a very strong open source community behind it. I do eventually want to dig into making a REST API backend for the HTML5 mobile app I develop in the tutorials, but I will be primarily using node to run some tools that will make development easier.

##The HTML5 vs. native Argument

At the end of the day I think that people should figure out which approach is going to work best for them. There are some excellent examples of HTML5 based apps in the market today, the big ones being LinkedIn and Facebook ([You’ll never believe how LinkedIn built its new iPad app](http://venturebeat.com/2012/05/02/linkedin-ipad-app-engineering/)). However, news has recently broke that Facebook's HTML5 strategy has not been working out for them and they soon plan to ditch it in favor of a pure Objective-C solution on iOS ([Facebook Plans to Speed Up its iPhone App](http://bits.blogs.nytimes.com/2012/06/27/facebook-plans-to-speedup-its-iphone-app/)). Some people may see this as a sign that HTML5 is a flawed strategy for mobile, but I think it has a place in the ecosystem, you just have to be aware of its limitations.

What are those limitations? Here are some interesting things to consider:

- __UIWebView on iOS is inherently slower than Mobile Safari__: Mobile Safari is the only game in town for iOS. There has been some buzz lately around Google Chrome showing up in the App Store, but everyone quickly points out that this app is just using UIWebView, as there is no other way to offer a browser in an iOS app (Apple won't allow it). So, HTML5 apps obviously have to use UIWebView. This isn't so bad, but it turns out that there are two versions of Safari's JavaScript engine on iOS. One is a purely interpreted one with no JIT and the other provides JIT. In the land of interpreted languages, JIT can make all the difference in speed by smartly compiling some parts of the interpreted language into native code at run-time. As it stands right now, Apple does not offer the JIT version of JavaScript to UIWebView for security reasons ([Why the Nitro JavaScript Engine Isn’t Available to Apps Outside Mobile Safari in iOS 4.3](http://daringfireball.net/2011/03/nitro_ios_43)). This means that your JavaScript performance won't be at its best on current versions of iOS.

- __Cordova doesn't use [addJavaScriptInterface()](http://developer.android.com/reference/android/webkit/WebView.html#addJavascriptInterface(java.lang.Object, java.lang.String)) on Android__: This is a really unfortunate reality for Cordova based apps. In order to overcome a bug in the Gingerbread emulator, the Cordova team ended up not using the built-in facilities of WebView to expose a Java interface to JavaScript. This is most certainly a short-sighted move that is detrimental to performance on a platform that already has UI performance issues (Jelly Bean notwithstanding, I'll believe it when I see it). I was not able to confirm if this is still the case with the current versions of Cordova, but I will be diving into their source code more deeply soon enough. See this article for more information: [Why Trigger.io doesn’t use PhoneGap – 5x faster native bridge](http://trigger.io/cross-platform-application-development-blog/2012/02/24/why-trigger-io-doesnt-use-phonegap-5x-faster-native-bridge/).

- __Breaking the native look of the platform__: This doesn't have to be an issue, you can certainly serve up different themes on different platforms and the flexibility of CSS can help with this. However, emulating the look and feel of each platform can be a lot of work, so I would imagine that a lot of HTML5 app developers will choose to make their own unique look. This can be jarring for a user on a UX heavy platform like iOS. Some ways to help alleviate this is by using actual native code for platform specific features like settings screens.

One of the best presentations I have seen on this topic took place at last year's Google I/O. I highly recommend watching it, it's still very relevant:

<iframe width="560" height="315" src="http://www.youtube.com/embed/4f2Zky_YyyQ" frameborder="0" allowfullscreen></iframe>

Watch the blog for future posts. I'll have the first part of the tutorials up soon.