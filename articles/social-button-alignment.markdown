Title: Making social buttons line up in a row in CSS
Author: Neil Goodman
Categories: CSS
Date: Sat Jan 14 2012 11:23:00 GMT-0600 (CST)

I don't have a lot of time for a big blog post today, so I thought I would talk about theming social buttons in CSS. Social buttons are those icons from Facebook, Twitter, etc that allow users to share your site's content by clicking on them. If you have ever had to line them up horizontally in a site design, then you may have had a lot of trouble. Here's how to get those buttons positioned exactly how you want them.

##Styling Social Buttons

Most buttons offer a 20 pixel high image and they render inline, so getting them to appear as a row isn't a problem.

So, here is our first try:

<social-button-alignment/social-buttons-1.html>

Here is what that looks like in a browser:

![Misaligned Buttons](social-button-alignment/social-buttons-misaligned.png)

Now you may want to try to use margins or padding in CSS to adjust the position of these elements, but that's not going to help much. Each button has slightly different margins and padding, which means your pixel-perfect adjustments are probably not going to look 100% correct and your CSS certainly won't have cross-browser support.

What you really want are the elements surrounding the social buttons to ignore their spacing, but you also want the social buttons to stay where you placed them in your HTML. If you have worked with CSS for any length of time then you know the solution to this problem is by using floats. Here is a nice rundown of what a floated element is and how it behaves: [http://css.maxdesign.com.au/floatutorial/introduction.htm](http://css.maxdesign.com.au/floatutorial/introduction.htm).

Here is the modified code that uses floats:

<social-button-alignment/social-buttons-2.html>

Here is what the modified code looks like in a browser:

![Aligned Buttons](social-button-alignment/social-buttons-aligned.png)

You will notice that there is a lot of space between the Facebook and the Twitter button. That is ok, because once the Facebook button renders its count bubble to the right, the spacing is fine. If you don't like the initial spacing, make the button last or just click it yourself when your webpage is up.

That's it. Let me know in the comments if there are any questions or issues.