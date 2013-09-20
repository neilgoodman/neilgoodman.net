Title: How to create and release a node module on npm
Author: Neil Goodman
Categories: npm
Date: Sun Aug 18 2013 08:15:51 GMT-0500 (CDT)

Node.js's module system is one of the biggest advantages of platform. Modules are extremely simple to create, and with npm, they can be shared and easily integrated with another Node project.

It's not very difficult to publish a package to npm, but there are enough steps involved that it can be difficult at first.

## Initial Setup

The first step is to create a module that you want to register with npm. npm is an open source platform and packages available on it are generally open source. While there are no limits to the licenses you can include with your project, any user of your package is going to get a full copy of your source (because JavaScript is an interpreted language). So, you will want to host the source for your module on [GitHub](http://github.com). You could host it anywhere, but GitHub is the most popular for Node.js development.

__Note:__ It is entirely possible to create your own private npm registry and configure the command-line tool to fetch packages from it. In this way you can have a closed package registry and you wouldn't need to open source anything. To learn more about this you can read the official [documentationn](https://npmjs.org/doc/registry.html#Can-I-run-my-own-private-registry) (not much there but a very basic outline), or you can read this blog post that dives in the details of setting up a replicated CouchDB instance of npm: [How to create a private npm.js repository](http://clock.co.uk/tech-blogs/how-to-create-a-private-npmjs-repository).