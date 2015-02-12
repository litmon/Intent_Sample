package com.example.fukuokadota.intent_sample;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.VideoView;


public class MainActivity extends ActionBarActivity {

    private static final int REQUEST_CODE_IMAGE_CAPTURE = 0;
    private static final int REQUEST_CODE_VIDEO_CAPTURE = 1;
    Uri uri;

    VideoView videoView;
    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        videoView = (VideoView) findViewById(R.id.videoView);
        imageView = (ImageView) findViewById(R.id.imageView);
    }


    public void imageCapture(View v) {
        if(videoView.isPlaying()){
            videoView.pause();
        }

        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, REQUEST_CODE_IMAGE_CAPTURE);
    }

    public void videoCapture(View v) {
        if(videoView.isPlaying()){
            videoView.pause();
        }

        Intent intent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);

        // video quality 0: lower, 1: higher
        intent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 0);
        // video duration limit in second
        intent.putExtra(MediaStore.EXTRA_DURATION_LIMIT, 30);
        // video capture orientation value ActivityInfo.SCREEN_ORIENTATION_***(landscape, portrait)
        intent.putExtra(MediaStore.EXTRA_SCREEN_ORIENTATION, ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        startActivityForResult(intent, REQUEST_CODE_VIDEO_CAPTURE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case REQUEST_CODE_IMAGE_CAPTURE:
                    Bitmap photo = (Bitmap) data.getExtras().get("data");
                    imageView.setImageBitmap(photo);

                    break;
                case REQUEST_CODE_VIDEO_CAPTURE:
                    uri = data.getData();

                    videoView.setVideoURI(uri);
                    videoView.start();
                    break;
            }
        } else if (resultCode == RESULT_CANCELED) {
            // do not something
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
