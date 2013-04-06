@Override
protected void onHandleIntent(Intent intent) {
    // When an intent is received by this Service, this method
    // is called on a new thread.
    
    Uri    action = intent.getData();
    Bundle extras = intent.getExtras();
    
    if (extras == null || action == null || !extras.containsKey(EXTRA_RESULT_RECEIVER)) {
        // Extras contain our ResultReceiver and data is our REST action.  
        // So, without these components we can't do anything useful.
        Log.e(TAG, "You did not pass extras or data with the Intent.");
        
        return;
    }
    
    // We default to GET if no verb was specified.
    int            verb     = extras.getInt(EXTRA_HTTP_VERB, GET);
    Bundle         params   = extras.getParcelable(EXTRA_PARAMS);
    ResultReceiver receiver = extras.getParcelable(EXTRA_RESULT_RECEIVER);
    
    try {            
        // Here we define our base request object which we will
        // send to our REST service via HttpClient.
        HttpRequestBase request = null;
        
        // Let's build our request based on the HTTP verb we were
        // given.
        switch (verb) {
            case GET: {
                request = new HttpGet();
                attachUriWithQuery(request, action, params);
            }
            break;
            
            case DELETE: {
                request = new HttpDelete();
                attachUriWithQuery(request, action, params);
            }
            break;
            
            case POST: {
                request = new HttpPost();
                request.setURI(new URI(action.toString()));
                
                // Attach form entity if necessary. Note: some REST APIs
                // require you to POST JSON. This is easy to do, simply use
                // postRequest.setHeader('Content-Type', 'application/json')
                // and StringEntity instead. Same thing for the PUT case 
                // below.
                HttpPost postRequest = (HttpPost) request;
                
                if (params != null) {
                    UrlEncodedFormEntity formEntity = new UrlEncodedFormEntity(paramsToList(params));
                    postRequest.setEntity(formEntity);
                }
            }
            break;
            
            case PUT: {
                request = new HttpPut();
                request.setURI(new URI(action.toString()));
                
                // Attach form entity if necessary.
                HttpPut putRequest = (HttpPut) request;
                
                if (params != null) {
                    UrlEncodedFormEntity formEntity = new UrlEncodedFormEntity(paramsToList(params));
                    putRequest.setEntity(formEntity);
                }
            }
            break;
        }
        
        if (request != null) {
            HttpClient client = new DefaultHttpClient();
            
            // Let's send some useful debug information so we can monitor things
            // in LogCat.
            Log.d(TAG, "Executing request: "+ verbToString(verb) +": "+ action.toString());
            
            // Finally, we send our request using HTTP. This is the synchronous
            // long operation that we need to run on this thread.
            HttpResponse response = client.execute(request);
            
            HttpEntity responseEntity = response.getEntity();
            StatusLine responseStatus = response.getStatusLine();
            int        statusCode     = responseStatus != null ? responseStatus.getStatusCode() : 0;
            
            // Our ResultReceiver allows us to communicate back the results to the caller. This
            // class has a method named send() that can send back a code and a Bundle
            // of data. ResultReceiver and IntentService abstract away all the IPC code
            // we would need to write to normally make this work.
            if (responseEntity != null) {
                Bundle resultData = new Bundle();
                resultData.putString(REST_RESULT, EntityUtils.toString(responseEntity));
                receiver.send(statusCode, resultData);
            }
            else {
                receiver.send(statusCode, null);
            }
        }
    }
    catch (URISyntaxException e) {
        Log.e(TAG, "URI syntax was incorrect. "+ verbToString(verb) +": "+ action.toString(), e);
        receiver.send(0, null);
    }
    catch (UnsupportedEncodingException e) {
        Log.e(TAG, "A UrlEncodedFormEntity was created with an unsupported encoding.", e);
        receiver.send(0, null);
    }
    catch (ClientProtocolException e) {
        Log.e(TAG, "There was a problem when sending the request.", e);
        receiver.send(0, null);
    }
    catch (IOException e) {
        Log.e(TAG, "There was a problem when sending the request.", e);
        receiver.send(0, null);
    }
}