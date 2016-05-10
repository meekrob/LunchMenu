package madman.david.lunchmenudavidking;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.Region;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;

/**
 * Created by david on 3/21/16.
 */
public class About extends Activity {



    MediaPlayer mediaPlayer = null;

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(this.getLocalClassName() + ".onStart()", "parent: " + getParent());

        // get the preference manager
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        // retrieve setting
        boolean playMusic = sharedPreferences.getBoolean("PlayMusic", false);

        if (playMusic) {
            mediaPlayer = MediaPlayer.create(this, R.raw.dream_of_the_insomniac);
            mediaPlayer.setVolume(.5f, .5f);
            mediaPlayer.setLooping(true);
            mediaPlayer.start();
        }
    }
    protected void onPause() {
        super.onPause();
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.reset();
            mediaPlayer.release();
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(this.getLocalClassName() + ".onCreate():", "savedInstanceState:" + (savedInstanceState == null ? "null" :
                        savedInstanceState.toString()));
        
        setContentView(new GraphicsView(this));

    }
    // monitor some events
    @Override
    public void onStop() {
        super.onStop();
        Log.d(this.getLocalClassName(), ".onStop()");
    }
    @Override
    public void onStart() {
        super.onStart();
        Log.d(this.getLocalClassName(), ".onStart()");
        Log.d(this.getLocalClassName() + ".onStart()", "parent: " + getParent());
    }
    @Override
    public void onRestart() {
        super.onRestart();
        Log.d(this.getLocalClassName(), ".onRestart()");
    }
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        Log.d(this.getLocalClassName(), ".onSaveInstanceState(): outState:" + (outState == null ? "null" :
                outState.toString()));
    }
    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        Log.d(this.getLocalClassName(), "onRestoreInstanceState(): savedInstanceState:" + (savedInstanceState == null ? "null" :
                savedInstanceState.toString()));
    }

    public static class GraphicsView extends View {

        private static final String[] QUOTES = {"These are the times of dreamy quietude,",
                "when beholding the tranquil beauty and brilliancy of the ocean’s skin,",
                "one forgets the tiger heart that pants beneath it;",
                "and would not willingly remember,",
                "that this velvet paw but conceals a remorseless fang."};
        private final Path tPath;
        private final Paint cPaint;
        private final Paint tPaint;
        private Context parentContext;
        public GraphicsView(Context context) {
            super(context);
            parentContext = context;
            tPath = new Path();
            cPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
            tPaint = new Paint();

        }

        @Override
        protected void onDraw(Canvas canvas) {
            String tag = "GraphicsView.onDraw";
            int height = canvas.getHeight();
            int width = canvas.getWidth();
            float density = getResources().getDisplayMetrics().density;

            Log.d(tag, "Canvas w,h:" + width + ", " + height);
            Log.d(tag, "Density:" + density);


            cPaint.setColor(getResources().getColor(R.color.circleStrokeColor));
            cPaint.setStyle(Paint.Style.STROKE);
            cPaint.setStrokeWidth(3);
            int colorPalette[] = {
                    Color.parseColor("#A7DBD8"),
                    Color.parseColor("#E0E4CC"),
                    Color.parseColor("#F38630"),
                    Color.parseColor("#e6c599"),
                    Color.parseColor("#FA6900"),
                    Color.parseColor("#eda666")
            };

            Path[] drawing = getDavidsDrawing();
            // create the transform here to do proper scaling of my drawing (300 by 600)
            Matrix transform = new Matrix();
            float sx = (float)width/300f;
            float sy = (float)height/600f;
            transform.setScale(sx, sy);

            // alternate clip paths and fill with color
            for (int i=0; i < colorPalette.length; i++) {
                drawing[i].transform(transform);
                canvas.clipPath(drawing[i]);
                canvas.drawColor(colorPalette[i]);
            }
            // reset the clipping mask to the draw space
            boolean nonempty = canvas.clipRect(new Rect(0, 0, width, height), Region.Op.REPLACE);
            Log.d(tag, "clip rect is non-empty:" + nonempty);


            // draw the text
            tPaint.setColor(Color.BLACK);
            tPaint.setTextSize(24);
            // deliver the lines on successive curves, moving downward
            float yTop = .05f;
            for (int i=0; i < QUOTES.length; i++) {
                float xLeft = i*.01f * width;
                float xRight = .99f * width;
                float yShift = i * .1f;
                float curvitude = i * i * .1f;
                tPath.moveTo(xLeft, (yTop+yShift) * height);
                tPath.quadTo(0.5f * width, (0.2f+ curvitude) * height, xRight, (yTop+yShift) * height);
                canvas.drawTextOnPath(QUOTES[i], tPath, 0, tPaint.getTextSize(), tPaint);
                tPath.reset();
            }

        }

        private Path[] getDavidsDrawing() {
            /**
             *  I made a drawing in Inkscape using paths. Inkscape beziers are all cubic.
             *  I used a color palette from: http://www.colourlovers.com/palette/92095/Giant_Goldfish
             *  Then, using a python script, I extracted the "d" fields from those paths.
             *  See https://www.w3.org/TR/SVG/paths.html
             *  Then I parsed the data using the module svg.path from
             *  https://pypi.python.org/pypi/svg.path/2.1.1
             *  My script then generated the following code.
             *  This would not have been desirable by hand.
             */
            Path path_0 = new Path();
            path_0.moveTo(0.0f, 0.0f);
            path_0.lineTo(0.0f,600.0f);
            path_0.lineTo(300.0f,600.0f);
            path_0.lineTo(300.0f,0.0f);
            path_0.lineTo(0.0f,0.0f);
            Path path_1 = new Path();
            path_1.moveTo(0.0f, 0.0f);
            path_1.cubicTo(0.4f,200.0f, 100.0f, 800.0f, 300.0f, 500.0f);
            path_1.lineTo(300.0f,0.0f);
            path_1.lineTo(0.0f,0.0f);
            Path path_2 = new Path();
            path_2.moveTo(60.0f, 0.0f);
            path_2.cubicTo(-40.0f,100.0f, 200.0f, 700.0f, 200.0f, 500.0f);
            path_2.cubicTo(200.0f,300.0f, 300.0f, 400.0f, 300.0f, 300.0f);
            path_2.lineTo(300.0f,0.0f);
            path_2.lineTo(60.0f,0.0f);
            Path path_3 = new Path();
            path_3.moveTo(70.0f, 0.0f);
            path_3.cubicTo(7.5f,80.0f, 193.8f, 542.5f, 200.0f, 400.6f);
            path_3.cubicTo(206.2f, 258.8f, 287.5f, 337.5f, 300.0f, 250.0f);
            path_3.cubicTo(300.0f, 250.0f, 300.0f, 0.0f, 300.0f, 0.0f);
            path_3.cubicTo(300.0f, 0.0f, 70.0f, 0.0f, 70.0f, 0.0f);
            Path path_4 = new Path();
            path_4.moveTo(80.0f, 0.0f);
            path_4.cubicTo(55.0f,60.0f, 187.5f, 385.0f, 200.0f, 301.2f);
            path_4.cubicTo(212.5f,217.5f, 275.0f, 275.0f, 300.0f, 200.0f);
            path_4.cubicTo(300.0f,200.0f, 300.0f, 0.0f, 300.0f, 0.0f);
            path_4.cubicTo(300.0f, 0.0f, 80.0f, 0.0f, 80.0f, 0.0f);
            Path path_5 = new Path();
            path_5.moveTo(100.0f, 0.0f);
            path_5.cubicTo(200.0f,40.0f, 200.0f, 200.0f, 300.0f, 100.0f);
            path_5.lineTo(300.0f, 0.0f);
            path_5.lineTo(100.0f, 0.0f);
            Path path_6 = new Path();
            path_6.moveTo(200.0f, 200.0f);
            path_6.cubicTo(200.0f,100.0f, 300.0f, 200.0f, 300.0f, 200.0f);
            path_6.lineTo(300.0f,0.0f);
            path_6.lineTo(90.0f,0.0f);
            path_6.cubicTo(100.0f,40.0f, 200.0f, 200.0f, 200.0f, 200.0f);
            path_6.lineTo(200.0f,200.0f);

            return new Path[]{path_0, path_1, path_2, path_3, path_4, path_5, path_6};
        }

    }
}
