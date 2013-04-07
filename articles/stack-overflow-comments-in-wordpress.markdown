Title: Building Stack Overflow style code blocks on WordPress
Author: Neil Goodman
Categories: WordPress
Date: Sat Jan 07 2012 18:19:00 GMT-0600 (CST)

I knew I was going to be posting a lot of code when I was developing the theme for this blog. I really like how Stack Overflow styles their code blocks and I used their solution as a model for my theme. So, I thought I would go over my implementation for WordPress.

##Syntax Highlighting

Stack Overflow uses Google Prettify for their syntax highlighting. You can download a copy here: [http://code.google.com/p/google-code-prettify/](http://code.google.com/p/google-code-prettify/). You can find different themes for Prettify by doing a [Google search](https://www.google.com/search?q=prettify+themes). The Prettify project has also posted some themes here: [http://google-code-prettify.googlecode.com/svn/trunk/styles/index.html](http://google-code-prettify.googlecode.com/svn/trunk/styles/index.html). A Prettify theme is just a simple CSS file, so it is really easy to make your own if you want, just look at the examples.

##Install Prettify and a Theme

To install Prettify, simply download prettify.js and a theme (I'll assume the default theme for now which is prettify.css). Copy these two files somewhere in your WordPress theme directory. I have a directory named `javascripts` for all my JavaScript code and a directory named `stylesheets` for all my CSS, so I copied prettify.js and prettify.css into those two directories respectively. Open your theme's header.php file in a text editor and include the two files in the `<head>` section of the template. Your code should look something like this:

<stack-overflow-comments-in-wordpress/install-prettify.html>

##Bootstrapping Prettify

Prettify works by styling any code within a `<pre>` or `<code>` tag that has the class `prettyprint`. WordPress doesn't include this class on these tags by default. This would be ok if you were only concerned with styling code blocks in your posts, since you could add the class yourself, but this doesn't help people in the comments. The approach I took was to include this class using JavaScript, which is what Stack Overflow does as well.

I also wanted some more flexibility. I wanted to be able to have inline code snippets and full code blocks. To do that I decided on the convention that any `<code>` tag that was a child of a `<pre>` tag would be considered a code block and a single `<code>` tag would be considered an inline snippet. To style my code blocks I needed to wrap the block in a `<div>` with my own class called `codeblock`. I used the following jQuery code to accomplish all of that:

<stack-overflow-comments-in-wordpress/bootstrapping-prettify.html>

##Adding scroll bars to `<pre>` tags

One of the nice features that Stack Overflow code blocks have are scroll bars. With scroll bars your code can be a longer width than your post content. This allows you to copy paste code out of a source file and not worry about the character length of lines. This behavior doesn't come with Prettify out of the box, but it is simple to add with the following CSS rule. Just place this somewhere in your theme's style.css file:

<stack-overflow-comments-in-wordpress/prettify-scrollbars.css>

##Getting WordPress to leave code alone

That's it for the Prettify part. Everything should be highlighting correctly now. However, your code may be formatted incorrectly when you view posts. This is because WordPress has content filters that get applied to each post and comment before they are rendered in templates. To make WordPress behave a little better you can do two things. The first is to turn off the visual editor. You can do this in the WordPress admin pages by going to `Users > You User Account` and checking the box that says "Disable the visual editor when writing". This prevents your code from being manipulated in JavaScript by the visual editor.

The second step is to install a plugin called Preserve Code Formatting which you can get here: [http://wordpress.org/extend/plugins/preserve-code-formatting/](http://wordpress.org/extend/plugins/preserve-code-formatting/). This plugin will give you a few options after you have installed it. I recommend turning on the "Wrap multiline code in `<pre>` tag?" option so that you don't have to add `<pre>` tags while writing your posts.

That's it! Please let me know in the comments if there are any questions or issues.