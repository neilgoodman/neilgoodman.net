Title: Building an iOS and Android HTML5 App with Cordova - Part 1
Author: Neil Goodman
Category: Cordova
Date: Sun Jul 15 2012 12:04:00 GMT-0600 (CST)

This is the first part in my tutorial series on Cordova (aka PhoneGap). I will be focusing on targeting Android and iOS, but I may extend this to Windows Phone in the near future. I'm not sure how long this tutorial will be, but it will be a long one, so please have patience with me while I write this in my spare time.

This part of the tutorial is going to cover how to organize the folder layout of the project, how to create the Android project, how to create the iOS project, and the various JavaScript libraries that will be used.

All of the code for this tutorial can be found on GitHub here: [https://github.com/posco2k8/photo-mapper](https://github.com/posco2k8/photo-mapper). I will be maintaining this single repository for the entire series. The code for this part will be in the TutorialPart1 tag.

##Assumptions

I am assuming a lot about the reader's background in this tutorial (that's you). If you are not familiar with single-page JavaScript web application development, then this may not be the right tutorial for you. Feel free to jump in anyway and read my source code though. That's the only way I've been able to learn. It also helps to know your way around the iOS and Android tools. I will try my best to answer questions in the comments.

If you want to read some other articles on JavaScript development first, then I highly recommend looking over the annotated source code of Backbone.js and the various example projects referenced on the that site: [http://backbonejs.org/](http://backbonejs.org/). Also, anything written by [John Resig](http://ejohn.org/) (creator of jQuery) is very good.

##The App

The app is going to be fairly simple, but at the same time include enough complexity to not be trivial. I am going to be making an app that will let a user take and view photos. The app will have three screens: the photo grid, the photo map, and individual photo details. The app will all be made in HTML5/CSS3 and JavaScript. Cordova will provide access to the device's camera, file system, databases, and GPS. The [Google Maps JavaScript API](https://developers.google.com/maps/documentation/javascript/) will be used for mapping.

##Getting Started

The first step is to create a project structure for the app. Here is a screenshot of the layout I settled on:

![Folder Layout](cordova-html5-tutorial-1/cordova-finder.png)

Here is a quick rundown of what all the directories are for:

- `android`: This will store the Android project.

- `images`: This will store all the image assets for the app.

- `ios`: This will store the iOS project.

- `lib`: This will store all the JavaScript libraries the app will depend on.

- `src`: This will store the heart of the app. All models, views, and controllers will be stored here. Backbone.js will be used as the MVC framework, more on that later.

- `stylesheets`: This will store application wide stylesheets and any styles that libraries need. The app will be using Twitter Bootstrap as a UI toolkit, so most stylesheets will be written in [LESS](http://lesscss.org/).

##Creating the iOS Project

I feel like it should go without saying that you will need to be developing on OS X in order to perform any of these steps, but I'll say it anyway (and I just did). If you don't care about iOS, then you can just grab the PhoneGap installers from [http://phonegap.com](http://phonegap.com) and jump to the Android section below.

You will need a few things before you can begin.

The first is xCode. You can get xCode and the released iOS libraries for free, but you will need to pay if you want access to the beta iOS libraries and the ability to install to a device. You can get xCode here: [https://developer.apple.com/xcode/](https://developer.apple.com/xcode/). I will be developing in the iPhone Simulator for the tutorial, I may do device testing later.

The second thing is to get Cordova/PhoneGap. You can get pre-built libraries from [http://phonegap.com/](http://phonegap.com/). Right now I am using PhoneGap 1.8.1, but 1.9 is already out. I will most likely upgrade for future posts, but be aware of that when looking through my source on GitHub. If you want access to Cordova/PhoneGap's source code you can go here to find links to their repos: [http://incubator.apache.org/cordova/](http://incubator.apache.org/cordova/).

This is a little bit of a cop out, but I am going to link to PhoneGap's wiki on how to create the iOS project. I am doing this because it is what I used to figure out how to create the various mobile projects and I feel it does the job well enough: [http://docs.phonegap.com/en/1.9.0/guide_getting-started_ios_index.md.html#Getting%20Started%20with%20iOS](http://docs.phonegap.com/en/1.9.0/guide_getting-started_ios_index.md.html#Getting%20Started%20with%20iOS). The only thing I will say is that when it comes time to put the iOS project somewhere, put it in the `ios` directory under your project.

You will need to do one last thing for the iOS project. By default scrolling is enabled in the `UIWebView`. This means the user will be able to scroll the view and the entire screen will bounce, which is a terrible experience. To prevent this, we need to add a few lines of Objective-C code to the project. You will need to add these lines in MainViewController.m in the `webViewDidFinishLoad` method:

<cordova-html5-tutorial-1/ios.m>

__Note:__ This code will only work on iOS 5.0 and up.

##Creating the Android Project

In order to create the Android project, you will need the Android SDK and Eclipse (you don't technically need Eclipse, but I will be using it for the Android parts of the tutorial). You can get the Android SDK here: [http://developer.android.com/sdk](http://developer.android.com/sdk) and you can learn how to get setup with Eclipse and Android here: [http://developer.android.com/sdk/installing/installing-adt.html](http://developer.android.com/sdk/installing/installing-adt.html).

You can follow the PhoneGap Android tutorial here: [http://docs.phonegap.com/en/1.9.0/guide_getting-started_android_index.md.html#Getting%20Started%20with%20Android](http://docs.phonegap.com/en/1.9.0/guide_getting-started_android_index.md.html#Getting%20Started%20with%20Android). Be sure to put the Android project into the __android__ directory in the overall project.

##The JavaScript Libraries

I have included the various JavaScript libraries the app is going to use in the GitHub repository. Here is a list of which libraries I'm using and and their roles:

- [Twitter Bootstrap](http://twitter.github.com/bootstrap/): UI Toolkit.

- [Backbone.js](http://backbonejs.org/): MVC Application Framework.

- [Undersocre.js](http://underscorejs.org/): Provides JavaScript 5 features to lower versions of JavaScript and a bunch of useful utilities in general. Backbone.js depends on this, but I am also going to use Underscore.js as a template engine.

- [Zepto.js](http://zeptojs.com/): Zepto is a light-weight DOM manipulation library and mimics jQuery as much as possible. I am using Zepto instead of jQuery mainly because it provides a CSS3 based animation framework, which will be crucial in providing high performance animations.

- [Simply-Deferred](http://sudhirj.github.com/simply-deferred/): Zepto isn't a drop-in replacement for jQuery and this is very apparent when it comes to its lack of support for jQuery.Deferred. Simply-Deferred can patch this in, but I will also use this library to manually patch in Deferred behavior on Zepto's animate() method.

- [iScroll 4](http://cubiq.org/iscroll-4): Some versions of iOS and Android do not support `overflow: auto;` on elements in a web view. iScroll provides support for this in a JavaScript interface, but it also provides a lot of other nice features like pull-to-refresh lists.

##Wrapping Up

I've covered how to organize the project and how to create the mobile projects. I've also covered the various libraries that will be used. In the next part I am going to cover setting up a build system using [Grunt.js](https://github.com/cowboy/grunt) and [node.js](http://nodejs.org/). This system will concatenate all of the JavaScript files in the correct order and produce a single app.js we can include in our HTML. It will also compile all of the LESS stylesheets and produce a single styles.css file. Unfortunately, this isn't a system that exists as a template anywhere, so this requires that it be developed manually. However, it's all written and executed in JavaScript, so it isn't very difficult to build.

Please ask questions in the comments.