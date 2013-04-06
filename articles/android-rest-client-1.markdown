Title: Modern techniques for implementing REST clients on Android 4.0 and below - Part 1
Author: Neil Goodman
Category: Android
Date: Sun Dec 25 2011 11:54:00 GMT-0600 (CST)

One of the first tasks I ever had to do as an Android developer was to make a REST client in order to communicate with a web service. It is probably one of the most common problems to solve as a mobile developer. So I thought I would spend some time discussing some of the approaches I've taken and provide some full working examples.

##General Overview

__12/28/2011 Update:__ I have updated the source code and the Android Market binary to use the [Android Compatibility library](http://developer.android.com/sdk/compatibility-library.html), so that the app can be run on Android 1.6 and above.

First off I highly recommend watching this Google I/O video. It was one of the first resources I used when I built my first client:

<iframe src="https://www.youtube.com/embed/xHXn3Kg2IQE" frameborder="0" width="560" height="315"></iframe>

Virgil Dobjanschi lays out a very good framework for consuming web services on Android. To keep things simple, I won't be discussing how to cache fetched data in a SQLite database, but I will probably come back to that in a future blog post. However, I will be discussing some of the implementation details that Virgil did not go into.

In a modern Android app I would argue that these are probably the best ways to approach implementing a REST call:

- Using <a href="http://developer.android.com/guide/topics/fundamentals/loaders.html">Loaders</a>. Namely by subclassing <a href="http://developer.android.com/reference/android/content/AsyncTaskLoader.html">AsyncTaskLoader</a>.

- Using a <a href="http://developer.android.com/guide/topics/fundamentals/services.html">Service</a>, as Virgil discussed in the video. Probably the best method would be to implement a subclass of <a href="http://developer.android.com/reference/android/app/IntentService.html">IntentService</a>.

- <del datetime="2011-12-31T21:10:29+00:00">Using a detached [Fragment](http://developer.android.com/guide/topics/fundamentals/fragments.html) that makes the network request using an [AsyncTask](http://developer.android.com/reference/android/os/AsyncTask.html). This is the approach Google took in their [Google I/O 2011 app](http://code.google.com/p/iosched/). This method is almost identical in function to using Loaders and Loaders are slightly easier to use.</del> __Update:__ A detached Fragment in this context doesn't make much sense and the Google I/O app doesn't do this. It uses a Fragment that calls `setRetainInstance(true)` to make a call to a Service based REST client. I will be covering this in part 2 of the tutorial. What I meant to say is that a Fragment that uses an AsyncTask could be used, but it is so much like a Loader that you should just use Loaders.

- Using a [Content Provider](http://developer.android.com/guide/topics/providers/content-providers.html), another suggestion from Virgil. I've never taken this approach because Content Providers are not naturally threaded and making them threaded always seemed to be a messy idea to me. In today's apps, Content Providers are normally queried from Loaders to help with the threading issue, so you may be better off just doing all the work in the Loader. That's the approach I take at least.

For this tutorial I'm going to be focusing on the first two approaches, because those are the methods I use every day at work and I find them to be reliable solutions. In this first part of the tutorial I am going to focus on using Loaders, since they are one of the newest additions to Android (they were introduced in Honeycomb). If you are not familiar with them, I highly recommend reading the above documentation I've linked to before reading further.

##REST with Loaders

Pros of using a Loader:

- Can be run by an Activity or a Fragment. All of a Loader's state is managed by Android in the LoaderManager class, so we don't have to worry about it once it starts. It will be torn down and rebuilt during lifecycle events.

Cons of using a Loader:

- These types of REST calls are basically one-off. You can fire them to quickly get some data when your Activity or Fragment starts up. However, once the process containing your Activity is pushed into the background, your REST call could get killed off during the Activity lifecycle events.

REST calls are network operations, which means they are probably going to be one of the slowest operations you can make. This means that we need to implement this call on a separate thread to avoid the dreaded ANR crash. We are going to handle that by having our RESTLoader subclass AsyncTaskLoader. An AsyncTaskLoader is a Loader that wraps around an AsyncTask and provides us with a thread to do our network operation. Once we have our RESTLoader built, we can simply call it using the LoaderManager available in Activities or Fragments.

First, let's start off by building our Activity. This project is going to be relatively simple. It's going to make a call to Twitter's search API and pull up the last few tweets that contain the word "android". This API call is documented here: [https://dev.twitter.com/docs/api/1/get/search](https://dev.twitter.com/docs/api/1/get/search). We will be using JSON as our data format as it is one of the most popular and Android has built in support for it.

If you would like to see this app in action, I've published it to the Android Market. Simply download it [here](https://market.android.com/details?id=net.neilgoodman.android.restloadertutorial), or scan the below QR code:

![QR Code](android-rest-client-1/rest-loader-turorial-qr.png)

All of the code for this project is available here on GitHub: [https://github.com/posco2k8/rest_loader_tutorial](https://github.com/posco2k8/rest_loader_tutorial). I recommend you download it as I will only be referring to code snippets from the completed files.

##RESTLoaderActivity.java

This is our Activity, which is a simple ListActivity that is powered by an ArrayAdapter. The first interesting bit of code is in the onCreate() method:

<android-rest-client-1/rest-loader-activity-1.java>

Here we create the `twitterSearchUri` which points to our Twitter rest call. Next we build up the params for the call. In this case, we are giving one parameter named q with the value android. The next few lines place these two pieces of our call in a Bundle called args. When Loaders are started, `args` is saved by the LoaderManager and it is used whenever the Loader needs to be rebuilt during lifecycle events. Finally, we use our LoaderManager to initialize our RESTLoader. The initLoader() method takes three arguments, an ID for our Loader, the args Bundle, and an object that implements `LoaderCallbacks<D>`. Our Activity implements `LoaderCallbacks<RESTLoader.RESTResponse>` so we pass `this`. These callbacks allow us to provide methods that create the Loader and handle what happens when it completes its task.

That brings us to our first `LoaderCallback`:

<android-rest-client-1/rest-loader-activity-2.java>

The above method gets called whenever our Loader is started or restarted by our Activity. When we call `initLoader()` in our Activity's `onCreate()` method, our `onCreateLoader()` method will get called next. We check to see if the args passed to us contain the data we need, then we build our loader.

Our second `LoaderCallback`:

<android-rest-client-1/rest-loader-activity-3.java>

This method is run once our Loader has returned with it's data, in this case the data will be an instance of `RESTLoader.RESTResponse`. This is a public inner class that I created on the `RESTLoader` class. It simply stores two fields: `int code`, and `String data`. The code field will store the HTTP response code and the data will store the response entity. We check to see if the code is 200 (the HTTP code for success) and then we move on to parsing the JSON using a private utility method defined on our Activity. I won't go into all the details of the JSON decoding, but you can take a look at the code yourself if you are interested.

Finally, we update our ListAdapter with the new data, which triggers our ListView to update itself with the tweets.

That's it for the Activity. Let's look at the Loader:

##RESTLoader.java

As I said before, RESTLoader is a subclass of AsyncTaskLoader. AsyncTaskLoader requires that we override only one method: `loadInBackground()`. As its name suggests, this method will be ran inside of a background thread, so we don't have to worry about ANR crashes.

Before we dive into the code, I want to talk about how we perform our HTTP requests. There are currently two ways to do this on Android: using URLConnection or using HttpClient. Virgil suggests using HttpClient and references bugs in URLConnection on Android. Things have changed a bit since then and the Android team has recently thrown their development weight behind URLConnection, which you can read all about here: [http://android-developers.blogspot.com/2011/09/androids-http-clients.html](http://android-developers.blogspot.com/2011/09/androids-http-clients.html).

Despite the Android team's support of URLConnection, I still prefer HttpClient. I simply feel that for REST, HttpClient's APIs are just easier to use. However, I do still use URLConnection to download binary files like images. There are pitfalls with URLConnection, for example performing a multi-part form POST is an absolute nightmare. HttpClient has APIs for all of that and it usually makes my life a lot easier. However, if you do want to make use of HttpClient then I strongly recommend downloading the latest version of the library from the project site [here](http://hc.apache.org/httpcomponents-client-ga/), as it contains a lot of bug fixes. For our simple demo, the bundled version of HttpClient will be fine.

Alright, here is the last method we are going to look at:

<android-rest-client-1/rest-loader.java>

I feel that this piece of code is the most straight forward, so I will let the comments and the code speak for itself. One thing to note is that once we are done with our call, we simply return our RESTResponse object. AsyncTaskLoader will take this object and send it back to our Activity to be handled in our LoaderCallbacks.

That's it. Next time I will talk about implementing REST calls using a Service, which I feel is a more robust solution. However, I really do like the simplicity of the Loader solution and I think it can be a very powerful tool. If you have any questions or find any bugs, please let me know in the comments.

View part two here: [Modern techniques for implementing REST clients on Android 4.0 and below – Part 2](/article/android-rest-client-2/)