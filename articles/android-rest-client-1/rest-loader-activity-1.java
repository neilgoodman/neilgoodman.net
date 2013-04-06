@Override
public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_rest_loader);

    // Since we are using the Android Compatibility library
    // we have to use FragmentActivity. So, we use ListFragment
    // to get the same functionality as ListActivity.
    FragmentManager fm = getSupportFragmentManager();

    ListFragment list = new ListFragment();

    FragmentTransaction ft = fm.beginTransaction();
    ft.add(R.id.fragment_content, list);
    ft.commit();

    mAdapter = new ArrayAdapter<String>(this, R.layout.item_label_list);

    // Let's set our list adapter to a simple ArrayAdapter.
    list.setListAdapter(mAdapter);

    // This is our REST action.
    Uri twitterSearchUri = Uri.parse("http://search.twitter.com/search.json");

    // Here we are going to place our REST call parameters. Note that
    // we could have just used Uri.Builder and appendQueryParameter()
    // here, but I wanted to illustrate how to use the Bundle params.
    Bundle params = new Bundle();
    params.putString("q", "android");

    // These are the loader arguments. They are stored in a Bundle because
    // LoaderManager will maintain the state of our Loaders for us and
    // reload the Loader if necessary. This is the whole reason why
    // we have even bothered to implement RESTLoader.
    Bundle args = new Bundle();
    args.putParcelable(ARGS_URI, twitterSearchUri);
    args.putParcelable(ARGS_PARAMS, params);

    // Initialize the Loader.
    getSupportLoaderManager().initLoader(LOADER_TWITTER_SEARCH, args, this);
}