Title: New Responsive Design for The Blog
Author: Neil Goodman
Categories: News
Date: Sun Mar 18 2012 11:39:00 GMT-0600 (CST)

For a while now I've wanted to refactor the theme of this blog to use [CSS3 media queries](http://coding.smashingmagazine.com/2010/07/19/how-to-use-css3-media-queries-to-create-a-mobile-version-of-your-website/) in order to provide better support for mobile devices. This weekend I finally sat down and did it.

##Responsive Theme

I had been using the great [960gs](http://960.gs) framework. I initially thought I could still use this framework and just modify the grid column lengths depending on screen size. This turned out to be a lot of work and the results didn't turn out very well. So, instead I decided to rip out the grid system and replace it with [Twitter Bootstrap's Grid](http://twitter.github.com/bootstrap/scaffolding.html#gridSystem). This gave me exactly what I wanted, since the Bootstrap grid has built-in responsive resizing.

The next step I took was to convert all the font-size properties in my CSS from pixels to em units. This site was very useful for that: [http://pxtoem.com/](http://pxtoem.com/). Then I changed any layout elements that defined their width in pixels to use percents, based on the 940px width baseline from the Bootstrap grid. I also had to refactor the search bar in the upper-right corner to render itself using just CSS3, as it had been implemented using background images.

##Caching

The host I have for this site is very underpowered and I'm often running out of memory. So, I decided to install the [W3 Total Cache](http://wordpress.org/extend/plugins/w3-total-cache/) plugin for Wordpress. So far the results have been great. It caches pages to disk and makes sure that all page requests are sent gzipped when possible.

##Results

Now I have a blog that will automatically resize itself for both small screens and large screens just using CSS. Check out the blog on your Android or iOS device to see the new theme!

![Responsive Screenshot](responsive-design-for-the-blog/responsive-preview.png)