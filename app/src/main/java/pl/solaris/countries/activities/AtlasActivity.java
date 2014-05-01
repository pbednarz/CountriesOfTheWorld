package pl.solaris.countries.activities;

import android.content.Intent;
import android.graphics.Point;
import android.net.Uri;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;

import java.io.InputStream;

import pl.solaris.countries.R;
import pl.solaris.countries.io.RandomAccessFileInputStream;
import pl.solaris.countries.map.ImageSurfaceView;
import pl.solaris.countries.map.ImageViewerActivity;

public class AtlasActivity extends FragmentActivity {

    private static final String TAG = "ImageViewerActivity";
    private static final String KEY_X = "X";
    private static final String KEY_Y = "Y";
    private static final String KEY_FN = "FN";

    private ImageSurfaceView imageSurfaceView;
    private String filename = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Hide the window title.
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.activity_atlas);
        imageSurfaceView = (ImageSurfaceView) findViewById(R.id.worldview);

        // Setup/restore state
        if (savedInstanceState != null && savedInstanceState.containsKey(KEY_X) && savedInstanceState.containsKey(KEY_Y)) {
            Log.d(TAG, "restoring state");
            int x = (Integer) savedInstanceState.get(KEY_X);
            int y = (Integer) savedInstanceState.get(KEY_Y);

            String fn = null;
            if (savedInstanceState.containsKey(KEY_FN))
                fn = (String) savedInstanceState.get(KEY_FN);

            try {
                if (fn == null || fn.length()==0) {
                    imageSurfaceView.setInputStream(getAssets().open("world_map.jpg"));
                } else {
                    imageSurfaceView.setInputStream(new RandomAccessFileInputStream(fn));
                }
                imageSurfaceView.setViewport(new Point(x, y));
            } catch (java.io.IOException e) {
                Log.e(TAG, e.getMessage());
            }
        } else {
            // Centering the map to start
            Intent intent = getIntent();
            try {
                Uri uri = null;
                if (intent!=null)
                    uri = getIntent().getData();

                InputStream is;
                if (uri != null) {
                    filename = uri.getPath();
                    is = new RandomAccessFileInputStream(uri.getPath());
                } else {
                    is = getAssets().open("world_map.jpg");
                }

                imageSurfaceView.setInputStream(is);
            } catch (java.io.IOException e) {
                Log.e(TAG, e.getMessage());
            }
            imageSurfaceView.setViewportCenter();
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        Point p = new Point();
        imageSurfaceView.getViewport(p);
        outState.putInt(KEY_X, p.x);
        outState.putInt(KEY_Y, p.y);
        if (filename!=null)
            outState.putString(KEY_FN, filename);
        super.onSaveInstanceState(outState);
    }
}
