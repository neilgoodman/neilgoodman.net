Title: Using ImageView’s setImageUri method on Android 2.1
Author: Neil Goodman
Category: Android
Date: Wen Dec 28 2012 20:15:00 GMT-0600 (CST)

Today I ran into an issue with the [setImageUri](http://developer.android.com/reference/android/widget/ImageView.html#setImageURI(android.net.Uri)) method on `ImageView`, but it only happened on our Android 2.1 devices. The method doesn't work on `Uri`'s that point to a `File`. This is actually a well documented issue on [Stack Overflow](http://stackoverflow.com/questions/3720530/why-would-imageview-setimageuri-work-in-android-2-2-but-not-2-1), but I thought I would share my solution since I couldn't find the exact answer I was looking for.

##The Issue

<android-2-1-setimageuri/issue.java>

##The Solution

<android-2-1-setimageuri/solution.java>

You should probably wrap that code into a utility method for your project. `android.os.Build.VERSION.SDK_INT` is a very useful system constant to know, because you can check which Android version you are on at runtime and use things like Java reflection to access newer APIs that you aren't specifically targeting. It also helps you solve stupid bugs like this.