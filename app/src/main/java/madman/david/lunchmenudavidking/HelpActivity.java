package madman.david.lunchmenudavidking;

import android.app.Activity;
import android.content.Context;
import android.widget.MediaController;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Toast;
import android.widget.VideoView;

import org.xmlpull.v1.XmlPullParser;

/**
 * Created by david on 4/2/16.
 */
public class HelpActivity extends Activity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.help);

        VideoView videoView = (VideoView)findViewById(R.id.videoView);

        // get the URI of the video
        Uri introURI = Uri.parse("android.resource://madman.david.lunchmenudavidking/raw/videoviewdemo");

        // link video to file resource
        videoView.setVideoURI(introURI);

        // create a MediaController to handle video
        MediaController myController = new MediaController(this);
        myController.setAnchorView(videoView);

        videoView.setMediaController(myController);
        videoView.requestFocus();
        try {
            myController.show(0);
        }
        catch(java.lang.NullPointerException ex) {
            Toast.makeText(this, "MediaController has an exception way down inside", Toast.LENGTH_SHORT).show();
        }
        // play video
        //video.start();
    }

}
