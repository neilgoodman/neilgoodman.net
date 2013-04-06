@Override
public void onLoadFinished(Loader<RESTLoader.RESTResponse> loader, RESTLoader.RESTResponse data) {
    int    code = data.getCode();
    String json = data.getData();
    
    // Check to see if we got an HTTP 200 code and have some data.
    if (code == 200 && !json.equals("")) {
        
        // For really complicated JSON decoding I usually do my heavy lifting
        // Gson and proper model classes, but for now let's keep it simple
        // and use a utility method that relies on some of the built in
        // JSON utilities on Android.
        List<String> tweets = getTweetsFromJson(json);
        
        // Load our list adapter with our Tweets.
        mAdapter.clear();
        for (String tweet : tweets) {
            mAdapter.add(tweet);
        }
    }
    else {
        Toast.makeText(this, "Failed to load Twitter data. Check your internet settings.", Toast.LENGTH_SHORT).show();
    }
}