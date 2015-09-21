package com.parse.loginsample.withdispatchactivity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.ViewAnimator;

import com.parse.ParseUser;

import common.activities.SampleActivityBase;



public class UserAppActivity extends SampleActivityBase {
    TextView textViewUserName;

    public static final String TAG = "MainActivity";

    // Whether the Log Fragment is currently shown
    private boolean mLogShown;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_app);
        
        textViewUserName = (TextView)findViewById(R.id.tvid_username);

        if (savedInstanceState == null) {
            android.app.FragmentTransaction transaction = getFragmentManager().beginTransaction();
            SlidingTabsBasicFragment fragment = new SlidingTabsBasicFragment();
            transaction.replace(R.id.sample_content_fragment, fragment);
            transaction.commit();
        }
    }
    
    @Override
    protected void onStart() {
        super.onStart();
        //setup the profile page based on the current user
        ParseUser user = ParseUser.getCurrentUser();
        showBasicInfo(user);
    }
    
      //Shows profile of a given user
    private void showBasicInfo(ParseUser user) {
         if(user!=null){
             String fullname = user.getString("name");
             if(fullname!=null){
                 textViewUserName.setText(fullname);
             }
         }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_user_app, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        MenuItem logToggle = menu.findItem(R.id.menu_toggle_log);
        logToggle.setVisible(findViewById(R.id.sample_output) instanceof ViewAnimator);
        logToggle.setTitle(mLogShown ? R.string.sample_hide_log : R.string.sample_show_log);

        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case R.id.menu_toggle_log:
                mLogShown = !mLogShown;
                ViewAnimator output = (ViewAnimator) findViewById(R.id.sample_output);
                if (mLogShown) {
                    output.setDisplayedChild(1);
                } else {
                    output.setDisplayedChild(0);
                }
                invalidateOptionsMenu();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

/** Create a chain of targets that will receive log data 
   @Override
   public void initializeLogging() {
       // Wraps Android's native log framework.
       LogWrapper logWrapper = new LogWrapper();
       // Using Log, front-end to the logging chain, emulates android.util.log method signatures.
       common.logger.Log.setLogNode(logWrapper);

       // Filter strips out everything except the message text.
       MessageOnlyLogFilter msgFilter = new MessageOnlyLogFilter();
       logWrapper.setNext(msgFilter);

       // On screen logging via a fragment with a TextView.
       LogFragment logFragment = (LogFragment) getSupportFragmentManager()
               .findFragmentById(R.id.log_fragment);
       msgFilter.setNext(logFragment.getLogView());

       Log.i(TAG, "Ready");
   }*/
}
