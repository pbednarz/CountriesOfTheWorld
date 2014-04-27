package pl.solaris.countries.activities;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Handler;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;

import org.json.JSONException;
import org.json.JSONObject;

import pl.solaris.countries.R;
import pl.solaris.countries.model.Geonames;
import pl.solaris.countries.service.DataRequest;
import pl.solaris.countries.service.JSONHelper;

public class SplashActivity extends ActionBarActivity {

    private static int SPLASH_TIME_OUT = 1800;
    private GeonamesTask mAuthTask = null;
    private View mProgressView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_splash);
        mProgressView = findViewById(R.id.login_progress);
        if(!isOnline())
            finishNoInternet();
        else {
            mAuthTask = new GeonamesTask();
            mAuthTask.execute();

        }
    }

    /**
     * Represents an asynchronous login/registration task used to authenticate
     * the user.
     */
    public class GeonamesTask extends AsyncTask<Void, Void, Boolean> {

        private DataRequest dataRequest;

        @Override
        protected Boolean doInBackground(Void... params) {
            dataRequest = new DataRequest();
            return dataRequest.sendGet();
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            mAuthTask = null;
            showProgress(false);

            if (success) {
                Geonames geo = JSONHelper.getGeonames(dataRequest.jOb);
                if(geo==null || (geo!=null && geo.getGeonames()==null))
                    finishProblem();

                Intent i = new Intent(SplashActivity.this, MainActivity.class);
                i.putExtra("geonames", geo);
                startActivity(i);
                finish();
            } else {
                finishProblem();

            }
        }

        @Override
        protected void onCancelled() {
            mAuthTask = null;
            showProgress(false);
        }
    }

    /**
     * Shows the progress UI and hides the login form.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    public void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mProgressView.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
        }
    }


    public void finishNoInternet() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                SplashActivity.this);

        // set title
        alertDialogBuilder.setTitle("Note:");

        // set dialog message
        alertDialogBuilder
                .setMessage("No internet connection! Please try again later.")
                .setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // if this button is clicked, close
                        // current activity
                        SplashActivity.this.finish();
                    }
                });

        // create alert dialog
        AlertDialog alertDialog = alertDialogBuilder.create();

        // show it
        alertDialog.show();
    }

    public void finishProblem() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                SplashActivity.this);

        // set title
        alertDialogBuilder.setTitle("Note:");

        // set dialog message
        alertDialogBuilder
                .setMessage("No internet connection or problem with service connection. Please try again later.")
                .setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // if this button is clicked, close
                        // current activity
                        SplashActivity.this.finish();
                    }
                });

        // create alert dialog
        AlertDialog alertDialog = alertDialogBuilder.create();

        // show it
        alertDialog.show();
    }

    public boolean isOnline() {
        ConnectivityManager cm =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isConnectedOrConnecting()) {
            return true;
        }
        return false;
    }
}
