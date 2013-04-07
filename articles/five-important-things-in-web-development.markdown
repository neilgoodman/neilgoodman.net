Title: Five Important Things to Know in Web Development
Author: Neil Goodman
Category: Web
Date: Sat May 05 2012 17:07:00 GMT-0600 (CST)

Getting back into web development a few months ago inspired me to write a list of topics on the subject. I came up with five things that I think are important for any web developer to at least be aware of.

##The List

There are certainly more than five things to know when studying web development, but I came up with this list by choosing the skills that changed my development pattern from wild random hacking, to actual engineering.

I decided to focus on topics that would be useful to people just starting out, but at the same time were not completely trivial.

##1. Clearing containers with `overflow: hidden;`

I used to insert `<div class="clear"></div>` tags all over my HTML when dealing with floated elements. I stopped doing this after I learned a pretty useful trick: if a layout container in an HTML document is given the `overflow: hidden;` property, it will wrap around its floated children, producing the same effect as the `<div class="clear"></div>` solution. See my example [here](http://jsfiddle.net/hr8GT/) on jsFiddle.

__Note:__ This is a useful trick, but I currently use LESS and Twitter Bootstrap in a lot of my projects. In that environment I just rely on the `.clearfix()` mixin. Check out LESS here: [http://lesscss.org/](http://lesscss.org/) and Twitter Bootstrap here: [http://twitter.github.com/bootstrap/](http://twitter.github.com/bootstrap/).

##2. CSS `position` and `z-index`

CSS positioning allows you to move elements around the page using offsets. For example:

<five-important-things-in-web-development/zindex.css>

The above CSS offsets the #container element 10 pixels from its top and left edge in normal flow. Normal flow is the default rendering scheme for elements. There are CSS properties that can remove an element from normal flow, which allows for manual placement. This happens most often with elements that are floated or elements that have a position value set to absolute.

Absolute positioning is probably one of the most misunderstood concepts in CSS. I struggled with it for a while, but once you understand all the nuances, then it really isn't that difficult of a concept. Here are the basics:

- All elements have a default position of static.

- Any element with the position of relative will be offset from its normal position. You specify offsets using the top, left, right, and bottom properties.

- Any element with the position of absolute will be offset from its first ancestor that has a position that is not static. An ancestor is any container element. For example, any `<p>` tag on this blog post has the `<body>` tag as an ancestor, because all `<p>` tags are inside of the `<body>` tag. All `<p>` tags are also inside of the `<html>` tag, so `<html>` is also an ancestor of `<p>`.

- Any element with the position of absolute is taken out of the normal flow of the document, which means that the margin and padding of any neighboring element do not impact the absolutely positioned element.

There is another concept that is tied to using the position property in CSS, and that is the z-index property. This property allows you to lay elements on top of others. If you come from a math or engineering background, then you can think of the z-index as being an axis that is orthogonal to the screen. If you come from a design background, you can think of the z-index as being a layer number. In either case, elements with a higher z-index number will appear on top of elements with a lower z-index number. The reason this property is related to the position property is because you can only give z-index values to elements that have a position value other than static.

There is one last gotcha with position absolute. If you absolutely position an element, but you do not give it a top, left, right, or bottom value, then it will remain in the position that the browser would normally place it in. However, the element will still be taken out of the normal flow of the document, which means that its containers will not wrap around it, neighboring elements will not impact it with margin or padding values, and elements can appear above or beneath it. This style of absolute positioning is often used with drop-down menus, so that the drop-downs appear above all other content on the page, but otherwise appear where you would expect them to in the document.

[Here](http://jsfiddle.net/qRDBV/) is a jsFiddle with a position example.

If everything I just said made no sense to you, then read over some of these resources. One of them is bound to resonate with you. This is a really important concept to get down:

- [http://css-tricks.com/absolute-positioning-inside-relative-positioning](http://css-tricks.com/absolute-positioning-inside-relative-positioning)

- [http://coding.smashingmagazine.com/2009/10/05/mastering-css-coding-getting-started/#CSS-Basics7](http://coding.smashingmagazine.com/2009/10/05/mastering-css-coding-getting-started/#CSS-Basics7)

- For the brave (or engineer): [http://www.w3.org/TR/CSS2/visuren.html](http://www.w3.org/TR/CSS2/visuren.html)

##3. CSS Specificity

I went a long time without knowing about CSS specificity. Those days were a nightmare.

The bottom line: not all CSS selectors are equal. Each selector is given a weight and selectors with higher weight will override the styles contained in selectors with lower weight. Example:

__CSS:__

<five-important-things-in-web-development/specificity-1.css>

__HTML:__

<five-important-things-in-web-development/specificity.html>

So, what is happening here? Well, we are trying to make the above `<div>` tag have a background-color of red and even though we wrote a valid selector that points to our `<div>` tag, it will still have a background of blue. Why? Because the `#too-bad-youre-blue` selector is selecting the same element using an ID selector, and ID selectors have a greater weight (or in CSS terms, a greater specificity). So, at the end of the day the ID selector is going to win.

What can you do to override the blue background? Each component of a selector impacts its specificity. The rule I learned was to give each ID in the selector a value of 100, each class a value of 10, and each tag a value of 1. For example:

<five-important-things-in-web-development/specificity-2.css>

In the above example, the first selector will make all text inside of the .items class bold, because the second selector has a smaller specificity. So, to make the `<div>` blue in the first example, we could use the following selector:

<five-important-things-in-web-development/specificity-3.css>

Here is a great article to learn more about specificity: [http://coding.smashingmagazine.com/2007/07/27/css-specificity-things-you-should-know](http://coding.smashingmagazine.com/2007/07/27/css-specificity-things-you-should-know)

##4. JavaScript Prototypes

JavaScript is a fully object-oriented language, but many developers coming from other object-oriented languages (such as Java) can have trouble adapting to JavaScript because it doesn't use classes. What JavaScript offers instead is prototype inheritance.

In JavaScript every object has a property known as a prototype. There is no cross-browser way to access this prototype, but you can access it in WebKit based browsers and Firefox by examining the `__proto__` property. `__proto__` will either be another object or null. Objects in `__proto__` also have a `__proto__` attribute. This defines a list of objects which is know as the prototype chain.

So what does the prototype actually do? If a method is called on an object that is undefined, then its prototype will be checked. If the prototype has the method, then it is called, otherwise the next prototype is checked and so on. This is also true for properties. This is easier to visualize in an example:

<five-important-things-in-web-development/prototypes-1.js>

The only cross-browser way to set the prototype of an object is to use JavaScript's "constructor" mechanism:

<five-important-things-in-web-development/prototypes-2.js>

JavaScript's implementation of prototype based inheritance is often regarded as a major failure of the language's design, because it mixes in class inheritance syntax. Many JavaScript programmers use inheritance frameworks. Here is one from Google's Closure Library: [http://closure-library.googlecode.com/svn/docs/closure_goog_base.js.html#goog.inherits](http://closure-library.googlecode.com/svn/docs/closure_goog_base.js.html#goog.inherits)

##5. JavaScript Closures

In JavaScript you can define a function inside of another function, which causes some interesting scope issues:

<five-important-things-in-web-development/closures-1.js>

Notice how the `iAmTheNest()` function uses a local variable from the `nestedFunction()`. When `iAmTheNest()`was returned and stored in `testTheNest` and then executed, it was still able to access the `aLocalVariable` value even though `nestedFunction()` had already returned. This is because when a function is defined inside of another function in JavaScript, it is given a reference to all the local variables that existed in the outer function it was defined in. This reference is what is called a closure. So even though those local variables no longer live on the call stack, they do live inside of the closure that is being referenced by the nested function.

How is this useful? One use is with nested event handlers. Here is a jQuery example:

<five-important-things-in-web-development/closures-2.js>

That's it. Let me know if anything I said was horribly wrong or if you have any questions.