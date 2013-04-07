Title: Modern techniques for implementing REST clients on Android 4.0 and below – Part 2
Author: Neil Goodman
Category: Android
Date: Sun Jan 01 2012 12:00:00 GMT-0600 (CST)

This is the second and last part of a tutorial on implementing REST clients using modern APIs introduce in Honeycomb and Ice Cream Sandwich. In the [last part](http://neilgoodman.net/2011/12/26/modern-techniques-for-implementing-rest-clients-on-android-4-0-and-below-part-1/) of this tutorial I covered how to make REST calls using Loaders and the LoaderManager class. In this part of the tutorial, I will be covering how to make REST calls using Services and the motivations behind this approach.

##Why use a Service?

The Loader approach worked well last time. LoaderManager has a very simple API and it manages the state of Loaders so that they are released and restarted when they need to be. However, as I pointed out in the previous blog post, they are inherently short lived tasks. If the Activity or Fragment containing the Loader is destroyed (which may happen when the user opens another app or gets a call), then the REST call will also get destroyed, whether or not the call completed.

This short lived behavior may not be bad for your application. In the demo Twitter app I built last time, this behavior wouldn't really affect much. Once the Activity has been destroyed and recreated, the Loader would also be recreated and it would fetch a new batch of tweets. However, maybe a REST response returns a large file or maybe you need to ensure that the call completes as part of your system. For this you need to have a part of your app that can last longer than your Activities in order to do its work. This is the exact use-case for Android [Services](http://developer.android.com/guide/topics/fundamentals/services.html).

Another benefit of using a Service is that it is not tied to an Activity or Fragment like a Loader. That allows you to make a REST call from all of your Android components, including other Services and BroadcastReceivers.

While a Service is more powerful, the trade off is a more complicated API with more edge cases to handle.

##REST with Services

I will be returning to the simple Twitter demo so that it will be easier to compare the two approaches. I will also be using the [Android Compatibility Library](http://developer.android.com/sdk/compatibility-library.html) so that the app can make use of new Android APIs like [Fragments](http://developer.android.com/guide/topics/fundamentals/fragments.html) all the way back to Android 1.6. The app is going to contain a RESTService that will replace the RESTLoader from last time, and an abstract RESTResponderFragment that will interact with the RESTService. I am modeling this framework after the [Google I/O 2011 app](http://code.google.com/p/iosched/).

You can get the finished app from this tutorial on the Android Market [here](https://market.android.com/details?id=net.neilgoodman.android.restservicetutorial), or by scanning the QR code below:

![QR Code](android-rest-client-2/rest-service-qr.png)

You will want to get the source code on GitHub here: [https://github.com/posco2k8/rest_service_tutorial](https://github.com/posco2k8/rest_service_tutorial), as I will only be referencing code snippets below.

##RESTService.java

RESTService has been implemented as a subclass of [IntentService](http://developer.android.com/reference/android/app/IntentService.html). While Services are meant to encapsulate longer running operations in an Android app, they are not inherently threaded. This is something that often confuses new Android developers. IntentService, on the other hand, does provide a thread. It does this with the `onIntent()` method by calling it on a new thread. So, RESTService will override this method and do its work there:

<android-rest-client-2/rest-service.java>

If you have read the previous tutorial, you will notice that this code is almost exactly the same as RESTLoader. The fundmental approach to sending the HTTP request has not changed, but where the Android component sending the request has. Instead of using LoaderManager, we now have to send an Intent to start this Service, which will in turn cause the `onIntent()` method to be called.

Another difference is how we deliver the results back to the caller. With Loaders we could make use of the convient LoaderCallbacks<D> interface, but that no longer exists. Now we have what is more like an IPC (Inter-Process Communication) problem. Android provides an API to make IPC a little easier on the platform, but the good news is that we don't even have to bother with that. IntentService and another class called [ResultReceiver](http://developer.android.com/reference/android/os/ResultReceiver.html) abstract away those details. So, in the Intent that gets sent to the Service, we can just attach a ResultReceiver which will handle all the IPC details for us. More on how we do that later.

__Note:__ While I mention IPC above, don't take that to mean that we are dealing with two processes here. Unless you specify otherwise in your AndroidManifest.xml file, a Service runs under the same process as all of your other app code. However, the above code is running in a separate thread, so we are communicating across threads. It makes little difference at the end of the day, since the two problems are so similar, but I just wanted to remove any ambiguity. Again, we don't have to worry about any of this since the IntentService/ResultReceiver pattern makes it a trivial problem to solve.

##RESTResponderFragment.java

Now that we have our RESTService, we need a way to interface with it. I described how ResultReceiver will be used to handle communication between the Service and the caller, but we actually need a more robust solution if we are going to be making this call from an Activity. Here is why:

- As I have mentioned before, Activities are fleeting things, but Services are not. What happens when our REST call completes but our Activity is gone? We need a way to handle this.

- What happens in the case that our Activity switches from landscape to portrait? When this happens on Android, the Activity is actually recreated. What if there is a call being processed during the time the Activity is being recreated? LoaderManager helped us out last time, but now we need to do a little extra work.

The common theme above is that the Activity can go away, but the Service will remain. This problem is solved by using another new Android API that was introduced during Honeycomb: a Fragment. If you read through the code in the last part of the tutorial, you may have noticed the use of FragmentManager and ListFragment. We are going to be using these again, but in addition we are going to be using a new custom fragment called RESTResponderFragment. It's a small class, so I'll post all of it below:

<android-rest-client-2/rest-responder.java>

Notice that this Fragment contains a ResultReceiver, so now this Fragment is capable of communicating with the RESTService. The `onCreate()` method is also important to note. It calls `setRetainInstance(true)` to ensure that an Activity only ever stores a single instance to this Fragment during its lifecycle.

Finally, this is an abstract class, because it is meant to be subclassed by other Fragments that can better process specific REST calls.

##TwitterSearchResponderFragment.java

There are three methods that are important to cover in our subclass of RESTResponderFragment. Here are the first two:

<android-rest-client-2/twitter-search-responder-fragment-1.java>

Here the fragment makes use of the `onActivityCreated()` lifecycle event. The Fragment uses this method to send the REST call once it has been added to the Activity and the Activity has finished being creating. The method is simple, it just calls the `setTweets()` helper method.

The `setTweets()` method does all the work. First it checks to see if it has any cached results, if so then it just uses those. Otherwise it builds the Intent for the RESTService. The Intent contains the REST action and the parameters for that action. The Intent could also store an HTTP verb like POST, PUT, or DELETE, but it is not necessary here since RESTService defaults to GET. Another piece to notice is that the method checks if the Activity is null before doing any of its work. This is to catch the case that the Activity doesn't exist when the REST response completes.

The last method for TwitterSearchResponderFragment:

<android-rest-client-2/twitter-search-responder-fragment-2.java>

This method gets called once the RESTService call has completed. It is very much like the `onLoadFinished()` method from the last tutorial. Once the JSON has been parsed, it uses the `setTweets()` utility method to finish processing the data.

##RESTServiceActivity.java

<android-rest-client-2/rest-service-activity.java>

The Activity code is pretty simple. It creates the two Fragments it needs, ListFragment and TwitterSearchResponderFragment, and then commits those to the FragmentManager using a FragmentTransaction. Before the Activity makes an instance of TwitterSearchResponderFragment, it checks to see if it exists because the FragmentManager could already be storing an instance of the Fragment.

That's it. Once the TwitterSearchResponderFragment has been committed, it will automatically make the REST call using RESTService and then process the results once they come it. Note that using a Fragment only really makes sense here in the Activity. If I needed to make a REST call from another Android component, like a Service or a BroadcastReceiver, I would just make the Intent for RESTService directly and pass in a ResultReceiver. The Fragment is only needed when the results from the REST call are going to be inserted in a View on an Activity.

If you find any bugs or have any questions, let me know in the comments.