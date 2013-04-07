Title: Working with Fragments on Android - Part 2
Author: Neil Goodman
Category: Android
Date: Mon Mar 12 2012 21:00:00 GMT-0600 (CST)

After a long delay, here is the second part in the Working with Fragments series. If you haven't read the first part then you can do so [here](http://neilgoodman.net/2012/01/29/working-with-fragments-on-android-part-1/).

In this part of the tutorial I am going to cover how to implement a simple tab interface using only Fragments. If you have ever messed around with Android's TabWidget and TabHost APIs, then you probably know what a nightmare it can be to deal with tab interfaces on Android. The good news is that it is very easy to reproduce that interface using Fragments and by doing so it gives you far more control over the look and feel of your tabs.

##Tabs

Just like TabHost and TabWidget, a Fragment tab interface can be broken into two parts: the tab content, and the tab controller. The tab controller will change the tab content when the user selects one of the tabs. The app I built contains a Fragment called TabFragment that acts as the controller, and LocationGridFragment and LocationListFragment which act as two examples of tab content. As you may have guessed from the Fragment names, this app is going to display a collection of location data in a list view and a grid view. The location data will be very simple: an address, and a picture of the location.

You can get a copy of the code for this tutorial at GitHub: [https://github.com/posco2k8/fragment_tabs_tutorial](https://github.com/posco2k8/fragment_tabs_tutorial). If you want to see the app in action, you can download it from the Android Market... ugh... I mean "Google Play" [here](https://play.google.com/store/apps/details?id=net.neilgoodman.android.fragmenttabstutorial). I won't be providing a QR code this time around, because I've always secretly believed they were stupid. Now you know.

##The Tab Controller

Here is the code for the tab controller:

<android-fragments-2/tab-fragment.java>

The key thing to note about the above code is that Fragment's have direct access to their Activity's FragmentManager. This means a Fragment can make all the same FragmentTransactions as the Activity. This allows Fragments to interact with other Fragments from within the same Activity.

##The List Tab Fragment

One of the two tab content Fragments displays a vertical list of address names. Here is the code:

<android-fragments-2/location-list-fragment.java>

The Fragment is very straight forward, so I won't dig too deeply into it. The list is powered by a custom adapter I've written which renders the data model into a custom list item view. I won't be going into how these adapters are structured, but I like to follow the ViewHolder pattern which is explained here: http://developer.android.com/resources/samples/ApiDemos/src/com/example/android/apis/view/List14.html](http://developer.android.com/resources/samples/ApiDemos/src/com/example/android/apis/view/List14.html).

##The Grid Tab Fragment

Unlike ListFragment, there is no convenient GridFragment in the Android APIs. Luckily, it is not very hard to create a similar interface. Here is the code:

<android-fragments-2/location-grid-fragment.java>

##The Activity

With the Fragments out of the way, we only have the Activity left. Of all the components in this tutorial, this is by far one of the simplest:

<android-fragments-2/fragment-tab-activity.java>

It would also be useful to see how the Activity's layout has been defined:

<android-fragments-2/fragment-tab-activity.xml>

__Update 3/13/2012:__ One thing I forgot to mention was how the code instantiates the Fragments. You will notice that the TabFragment is being brought into the Activity using XML, but that the tab content Fragments are dynamically added to a FrameLayout container. This is important to note, because `FragmentTransaction#replace` will not delete any Fragments that have been brought in using XML. This would cause problems here because the new Fragment added in a `replace()` call would be covering the existing XML Fragment, and both would respond to click events.

##Wrapping Up

Hopefully the above code has demonstrated how simple and powerful Fragments can be. The one thing to note in the above code is that I like to push as much business logic into my Fragments as possible. This helps make them more autonomous and in turn makes them much more easy to reuse in other Activities. However, you can move as much business logic into the Activity as you like. It is really up to you.

In the next part of the tutorial I will be taking this same project, but I will implement it as a tablet UI. The challenge will be to use the larger screen space more intelligently, but not to rewrite much of my existing UI. It just so happens that this is the exact problem Fragments were designed to solve.

Let me know if you have any questions in the comments.