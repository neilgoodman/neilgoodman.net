ImageView imageView = (ImageView) findViewById(R.id.image_view);

File file = new File(Environment.getExternalStorageDirectory(), "Android" + 
    File.separator + "data" + 
    File.separator + "my.package.name" + 
    File.separator + "myImage.png");

Uri fileUri = null;

// Android 2.1's internal version number is 7. You can also
// use android.os.Build.VERSION_CODES.ECLAIR_MR1 if you like to use
// constants.
if (android.os.Build.VERSION.SDK_INT == 7) {
    // You must use Uri.parse() on Android 2.1
    fileUri = Uri.parse(file.toString());
}
else {
    // You must use Uri.fromFile() on Android 2.2+
    fileUri = Uri.fromFile(file);
}

imageView.setImageUri(fileUri);