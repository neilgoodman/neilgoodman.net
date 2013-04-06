@Override
public void onRESTResult(int code, String result) {
    // Here is where we handle our REST response. This is similar to the 
    // LoaderCallbacks<D>.onLoadFinished() call from the previous tutorial.
    
    // Check to see if we got an HTTP 200 code and have some data.
    if (code == 200 && result != null) {
        
        // For really complicated JSON decoding I usually do my heavy lifting
        // with Gson and proper model classes, but for now let's keep it simple
        // and use a utility method that relies on some of the built in
        // JSON utilities on Android.
        mTweets = getTweetsFromJson(result);
        setTweets();
    }
    else {
        Activity activity = getActivity();
        if (activity != null) {
            Toast.makeText(activity, "Failed to load Twitter data. Check your internet settings.", Toast.LENGTH_SHORT).show();
        }
    }
}