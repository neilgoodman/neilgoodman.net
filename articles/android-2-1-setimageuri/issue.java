ImageView imageView = (ImageView) findViewById(R.id.image_view);

File file = new File(Environment.getExternalStorageDirectory(), "Android" + 
    File.separator + "data" + 
    File.separator + "my.package.name" + 
    File.separator + "my_image.png");

Uri fileUri = Uri.fromFile(file);

// This will work for Android 2.2+, but it will fail
// on Android 2.1 and your ImageView will be empty.
imageView.setImageUri(fileUri);