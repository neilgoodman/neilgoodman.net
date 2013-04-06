@Override
public Loader<RESTLoader.RESTResponse> onCreateLoader(int id, Bundle args) {
    if (args != null && args.containsKey(ARGS_URI) && args.containsKey(ARGS_PARAMS)) {
        Uri    action = args.getParcelable(ARGS_URI);
        Bundle params = args.getParcelable(ARGS_PARAMS);
        
        return new RESTLoader(this, RESTLoader.HTTPVerb.GET, action, params);
    }
    
    return null;
}