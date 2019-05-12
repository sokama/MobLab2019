package com.example.screengo.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import com.example.screengo.R;
import com.example.screengo.ScreenGoApplication;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;

public class AboutActivity extends AppCompatActivity {

    private Tracker tracker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        // Obtain the shared Tracker instance.
        ScreenGoApplication application = (ScreenGoApplication) getApplication();
        tracker = application.getDefaultTracker();
    }

    @Override
    protected void onResume() {
        super.onResume();

        tracker.setScreenName("Image~About");
        tracker.send(new HitBuilders.ScreenViewBuilder().build());
    }
}
