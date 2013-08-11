Title: neilgoodman.net Redesign
Author: Neil Goodman
Categories: News
Date: Sun Aug 11 2013 09:24:39 GMT-0500 (CDT)

This blog has been completely redesigned from the ground up.

##Motivation

I have neglected this site for a while. One reason for this was because it was so difficult for me to write new posts using WordPress. I'm not going to lay all the blame there, but it was very difficult to write code examples and posts in a tiny WYSIWYG editor in my browser. I could use external editors, but I didn't like any of them. I ended up writing most posts in a word processor and then copy and pasting them into WordPress. Not very efficient or fun.

##Engines

I looked at a lot of blogging engines. I really liked [Jekyll](http://jekyllrb.com/). Jekyll would allow me to write all my posts and code examples in my text editor (currently [Sublime Text 2](http://www.sublimetext.com/2)). This was really appealing, because I spend most of my day using a text editor and I would be able to write posts very quickly. Also Jekyll uses Markdown, which is much easier and faster to write than HTML.

One drawback I saw to using Jekyll is that I would lose my back-end server. I wanted to still have one so that I could do things like cache tweets and then display them client-side. I remember reading about [Wheat](https://github.com/creationix/wheat) when I first starting learning [Node.js](http://nodejs.org/). Wheat is similar to Jekyll in that it turns Markdown files into HTML and displays them as a blog. However, unlike Jekyll, it does this at runtime through a server written in Node and using a Git repo as a database replacement.

##Wheat Customizations

A few months ago I forked Wheat on GitHub and started customizing it for my needs. The first hurdle I ran into was that its rendering engine was using [Haml](http://haml.info/). I know some people love Haml, but I don't. I've been writing HTML since 1998 and at this point in my life I feel pretty comfortable with it. While Haml's syntax mirrors CSS and I can see how it can be faster to write, I still can't get behind it. I'm not saying I don't see the benefit in pre-processed languages for web development, but I prefer them to be a super-set of what exists instead of an entire rewrite (so guess how I feel about CoffeeScript).

You now might want to say: "Hey Neil, you just said earlier that you think Markdown is faster to write than HTML, how is that different?" That's true, but when I use Markdown the context is different. I use Markdown to write documentation, or a blog post like this one. When I am doing that, I care more about what I am writing and less about the syntax that I am using. When I am writing the HTML for a page or web application, I care very about about the syntax. When what I am writing lines up 1:1 with the documentation and code examples that I am reading, I have to think less and I can work faster. End rant.

I really like Mustache templating engines, my favorite being [Handlebars](http://handlebarsjs.com/). I also really like [LESS](http://lesscss.org/) and wanted to be able to use [Twitter Bootstrap](http://getbootstrap.com/) with LESS to implement my layout. So, I quickly switched out the existing Haml engine for Handlebars. I also wrapped Wheat inside of a [Connect](https://github.com/senchalabs/connect) server so that I could use the very nice [Connect Assets](https://github.com/adunkman/connect-assets) plugin to compile and serve my CSS and JavaScript.

##Layout

My girlfriend [Hannah](http://hannahfass.com) once again developed a new design for me. I think it turned out really well and it was very easy to make responsive versions for phones and tablets. Like I mentioned before, I am using Twitter Bootstrap as a framework for the layout and making heavy use of their responsive grid.

##Comments and Migration

My biggest concern was migration. Jekyll has nice tools for WordPress migration, Wheat does not. I did most of the migration by hand, which wasn't so bad because I didn't have a lot of articles. I haven't had time to proof read all of them, but they are all at least displaying content.

My only option for a commenting system was [Disqus](http://disqus.com/) (as I no longer have a database). Disqus is fantastic. Its UI fits in very naturally with my design and it gives me really nice moderation tools. I am going to try and be more responsive to comments on this site now that my tools have gotten better. Migration was super easy here, I simply exported my WordPress blog and fed it into Disqus' import system.

The biggest hurdle during the switch was preserving the old URLs for my WordPress articles, but at the same time being able to transition to Wheat's URL system. I did this by hacking in a very simple alias system into Wheat. This allows me to assign any URL to any article with a simple mapping object. This seems to be working well and all of my social counters and comments have carried over.

##Remaining Work

The current design is 99% implemented in CSS and so it scales very well with retina displays. Unfortunately, I am still using Bootstrap 2.3.2, which uses PNG images for all icons. At some point in the near future I am going to replace the icons with Bootstrap's new [Glyphicons project](http://glyphicons.getbootstrap.com/), which will provide better retina support.

Oh, and I am also going to try and write more posts =).

##Source Code

If you are interested in seeing all the changes I made to Wheat, you can find the source code for this blog on GitHub: [https://github.com/posco2k8/neilgoodman.net](https://github.com/posco2k8/neilgoodman.net).