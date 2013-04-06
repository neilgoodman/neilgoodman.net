@Override
public RESTResponse loadInBackground() {
    try {
        // At the very least we always need an action.
        if (mAction == null) {
            Log.e(TAG, "You did not define an action. REST call canceled.");
            return new RESTResponse(); // We send an empty response back. The LoaderCallbacks<RESTResponse>
                                       // implementation will always need to check the RESTResponse
                                       // and handle error cases like this.
        }
        
        // Here we define our base request object which we will
        // send to our REST service via HttpClient.
        HttpRequestBase request = null;
        
        // Let's build our request based on the HTTP verb we were
        // given.
        switch (mVerb) {
            case GET: {
                request = new HttpGet();
                attachUriWithQuery(request, mAction, mParams);
            }
            break;
            
            case DELETE: {
                request = new HttpDelete();
                attachUriWithQuery(request, mAction, mParams);
            }
            break;
            
            case POST: {
                request = new HttpPost();
                request.setURI(new URI(mAction.toString()));
                
                // Attach form entity if necessary. Note: some REST APIs
                // require you to POST JSON. This is easy to do, simply use
                // postRequest.setHeader('Content-Type', 'application/json')
                // and StringEntity instead. Same thing for the PUT case 
                // below.
                HttpPost postRequest = (HttpPost) request;
                
                if (mParams != null) {
                    UrlEncodedFormEntity formEntity = new UrlEncodedFormEntity(paramsToList(mParams));
                    postRequest.setEntity(formEntity);
                }
            }
            break;
            
            case PUT: {
                request = new HttpPut();
                request.setURI(new URI(mAction.toString()));
                
                // Attach form entity if necessary.
                HttpPut putRequest = (HttpPut) request;
                
                if (mParams != null) {
                    UrlEncodedFormEntity formEntity = new UrlEncodedFormEntity(paramsToList(mParams));
                    putRequest.setEntity(formEntity);
                }
            }
            break;
        }
        
        if (request != null) {
            HttpClient client = new DefaultHttpClient();
            
            // Let's send some useful debug information so we can monitor things
            // in LogCat.
            Log.d(TAG, "Executing request: "+ verbToString(mVerb) +": "+ mAction.toString());
            
            // Finally, we send our request using HTTP. This is the synchronous
            // long operation that we need to run on this Loader's thread.
            HttpResponse response = client.execute(request);
            
            HttpEntity responseEntity = response.getEntity();
            StatusLine responseStatus = response.getStatusLine();
            int        statusCode     = responseStatus != null ? responseStatus.getStatusCode() : 0;
            
            // Here we create our response and send it back to the LoaderCallbacks<RESTResponse> implementation.
            RESTResponse restResponse = new RESTResponse(responseEntity != null ? EntityUtils.toString(responseEntity) : null, statusCode);
            return restResponse;
        }
        
        // Request was null if we get here, so let's just send our empty RESTResponse like usual.
        return new RESTResponse();
    }
    catch (URISyntaxException e) {
        Log.e(TAG, "URI syntax was incorrect. "+ verbToString(mVerb) +": "+ mAction.toString(), e);
        return new RESTResponse();
    }
    catch (UnsupportedEncodingException e) {
        Log.e(TAG, "A UrlEncodedFormEntity was created with an unsupported encoding.", e);
        return new RESTResponse();
    }
    catch (ClientProtocolException e) {
        Log.e(TAG, "There was a problem when sending the request.", e);
        return new RESTResponse();
    }
    catch (IOException e) {
        Log.e(TAG, "There was a problem when sending the request.", e);
        return new RESTResponse();
    }
}