Title: Working with Fragments on Android - Part 1
Author: Neil Goodman
Category: Android
Date: Sun Jan 29 2012 17:20:00 GMT-0600 (CST)

[Fragments](http://developer.android.com/guide/topics/fundamentals/fragments.html) were added to the Android SDK when Honeycomb was launched and have since become an important component of modern Android development. This has been helped by the fact that they were also included in the [Android Compatibility Library](http://developer.android.com/sdk/compatibility-library.html) so that OS versions all the way back to Donut could make use of them. Prior to Fragments, an Android app's UI consisted of [Activities](http://developer.android.com/guide/topics/fundamentals/activities.html) and [Views](http://developer.android.com/guide/topics/ui/index.html) (sometimes called widgets in the Android documentation). Fragments allow you to break your Activities into smaller re-usable pieces which are easier to manage and allow for a more fluid visual design that can adapt to tablet and phone resolutions. I'm going to go over the basics of using Fragments in this first part of the tutorial.

##Getting Setup

If you are from an MVC background such as Rails or iOS, then you can think of an Activity as a controller and a View as a view (although to get really technical, Android's framework follows more along the lines of the [MVP model](http://en.wikipedia.org/wiki/Model_View_Presenter)). Fragments can then be thought of as "mini-controllers" or components that can be dropped into Activities either at runtime or through an Activity's layout XML.

Before I jump into some code, I first want to spend some time talking about how to get up and running with Fragments. If you are developing an app that only targets Android 3.0 (Honeycomb) or higher, then you are all set. You have access to the Fragment class and you can access the FragmentManager using the `getFragmentManager()` method in your Activities (I'll cover what these are below). However, most apps being developed right now are targeting Android 2.1 (Eclair), because it covers a significant percentage of the Android Market. In order to use Fragments on these older OS versions, you need to install the Android Compatibility Library. Google has excellent documentation on how to do this [here](http://developer.android.com/sdk/compatibility-library.html#Downloading). When using the compatibility library, you will need to subclass FragmentActivity for any Activity that wants to make use of Fragments and call the `getSupportFragmentManager()` method to gain access to the FragmentManager. Otherwise, all other details are exactly the same as developing for Fragments on Honeycomb and up.

You can make use of the compatibility library on Honeycomb and above. Why would you want to? Well, the compatibility library also contains a very useful new view called [ViewPager](http://developer.android.com/reference/android/support/v4/view/ViewPager.html). This view works very similarly to an `UIScrollView` with paging enabled on iOS, which allows a user to swipe left and right to access pages (download the [Google+ Android app](https://market.android.com/details?id=com.google.android.apps.plus) for an example). ViewPager has yet to be included in the core SDK, so if you want to make use of it then you have to install the compatibility library. ViewPager can also leverage Fragments, but these Fragments must be subclassed from `android.support.v4.app.Fragment` and not `android.app.Fragment`. You also have to use FragmentActivity in order to use ViewPager and these Fragments as a result.

##Building the Fragment

You can get all the source code for this tutorial over at GitHub here: [https://github.com/posco2k8/fragment_basic_tutorial](https://github.com/posco2k8/fragment_basic_tutorial). If you would like to see the final app in action, you can download it from the Android Market by going here or by scanning the QR code below: [https://market.android.com/details?id=net.neilgoodman.android.fragmentbasictutorial](https://market.android.com/details?id=net.neilgoodman.android.fragmentbasictutorial).

![QR Code](android-fragments-1/android-fragments-qr.png)

The first thing to cover is how to actually build a Fragment. Fragments are very similar to Activities, which means they have the same lifecycle callbacks and can have their own layouts.

Let's start by defining our Fragment's layout:

<android-fragments-1/fragment-basic.xml>

We have a very simple layout. Just a single button that is contained inside of a LinearLayout. Now on to the Fragment:

<android-fragments-1/basic-fragment.java>

One of the key differences between a Fragment and an Activity is that Fragments instantiate their Views inside the `onCreateView()` callback and Activities instantiate their Views using the `setContentView()` method inside of the `onCreate()` callback. Fragments also have to manually instantiate their Views using an instance of LayoutInflater, which is provided to the `onCreateView()` method for convenience.

Another difference is that a Fragment is not a subclass of Context. This means that a Fragment can not be launched as a component inside your app and therefore always has to live inside of an Activity. This also means that whenever you need a Context inside of a Fragment, you need to get access to the parent Activity. You can do this by using the `getActivity()` method as we have done in the Fragment button's OnClickListener callback. You need to watch out because `getActivity()` can return null depending on where the Fragment is in the Activity's lifecycle. So, you should also include a check to see if the Activity is null before you use it.

##Adding the Fragment to the Activity

Now we have two options to include the Fragment into an Activity. The first option is to include it via the Activity's layout XML just like you would with a View. Here is how to do that:

<android-fragments-1/activity-fragment-xml.xml>

You can use the `<fragment>` tag as many times as you like in a layout to include multiple Fragments. You can also use attributes on these tags to override the Fragment's parent view attributes. You need to always use a fully qualified name when using the `android:name` attribute or your Activity will throw an exception during runtime. You may notice the `<!-- Preview: -->` comment. You can use this comment to refer to the Fragment's layout so that it will be rendered in the layout preview tools inside of Eclipse.

Simply setting an Activity to use the above layout would cause the Fragment to be added and rendered to the screen. It is really that simple. However, you may want to have more control over when and how your Fragments are added over the course of your app. There is an alternative way to add a Fragment at runtime.

In order to add a Fragment at runtime we need to make a change to our Activity layout:

<android-fragments-1/activity-fragment-runtime.xml>

You will notice that we replaced the `<fragment>` tag from before with a simple FrameLayout view. This will be the View we target to dynamically add a Fragment. Now, let's look at the code for the Activity:

<android-fragments-1/basic-fragment-activity.java>

That's it for this tutorial. I will be writing more about Fragments in the next couple of weeks as there are a lot of cool things you can do with them. The next tutorial is going to take a look at how to build a tab interface using Fragments and we will look more at depth into the Fragment back stack. Please feel free to ask me any questions in the comments.

You can check out part two [here](http://neilgoodman.net/2012/03/12/working-with-fragments-on-android-part-2/).