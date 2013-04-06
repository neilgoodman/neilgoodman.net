@Override
public void onActivityCreated(Bundle savedInstanceState) {
    super.onActivityCreated(savedInstanceState);
    
    // This gets called each time our Activity has finished creating itself.
    setTweets();
}

private void setTweets() {
    RESTServiceActivity activity = (RESTServiceActivity) getActivity();
    
    if (mTweets == null && activity != null) {
        // This is where we make our REST call to the service. We also pass in our ResultReceiver
        // defined in the RESTResponderFragment super class.
        
        // We will explicitly call our Service since we probably want to keep it as a private
        // component in our app. You could do this with Intent actions as well, but you have
        // to make sure you define your intent filters correctly in your manifest.
        Intent intent = new Intent(activity, RESTService.class);
        intent.setData(Uri.parse("http://search.twitter.com/search.json"));
        
        // Here we are going to place our REST call parameters. Note that
        // we could have just used Uri.Builder and appendQueryParameter()
        // here, but I wanted to illustrate how to use the Bundle params.
        Bundle params = new Bundle();
        params.putString("q", "android");
        
        intent.putExtra(RESTService.EXTRA_PARAMS, params);
        intent.putExtra(RESTService.EXTRA_RESULT_RECEIVER, getResultReceiver());
        
        // Here we send our Intent to our RESTService.
        activity.startService(intent);
    }
    else if (activity != null) {
        // Here we check to see if our activity is null or not.
        // We only want to update our views if our activity exists.
        
        ArrayAdapter<String> adapter = activity.getArrayAdapter();
        
        // Load our list adapter with our Tweets.
        adapter.clear();
        for (String tweet : mTweets) {
            adapter.add(tweet);
        }
    }
}