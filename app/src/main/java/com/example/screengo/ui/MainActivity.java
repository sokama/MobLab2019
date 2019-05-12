package com.example.screengo.ui;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.SeekBar;
import android.widget.TextView;

import com.crashlytics.android.Crashlytics;
import com.example.screengo.R;
import com.example.screengo.ScreenGoApplication;
import com.example.screengo.model.Place;
import com.example.screengo.model.PlaceAdapter;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;

import java.util.List;

import javax.inject.Inject;

import io.fabric.sdk.android.Fabric;

public class MainActivity extends AppCompatActivity implements MainScreen {

    private static final String TAG = "MainActivity_SG";

    private static final String SHARED_PREF_BRIGHTNESS_CLOUDY = "BRIGHTNESS_CLOUDY";
    private static final String SHARED_PREF_BRIGHTNESS_SUNNY = "BRIGHTNESS_SUNNY";


    @Inject
    MainPresenter mainPresenter;

    private TextView weatherStateTV;

    private RecyclerView recyclerView;
    private PlaceAdapter adapter;

    private SeekBar outsideBrightnessCloudy;
    private SeekBar outsideBrightnessSunny;

    private boolean isSunny;

    private Tracker tracker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        weatherStateTV = findViewById(R.id.weatherStateText);
        outsideBrightnessCloudy = findViewById(R.id.CloudyBrightness);
        outsideBrightnessSunny = findViewById(R.id.SunnyBrightness);

        /* DEBUG */
        ScreenGoApplication.injector.inject(this);
        boolean injected = (mainPresenter != null) &&
                (mainPresenter.placesInteractor != null) &&
                (mainPresenter.locationInteractor != null) &&
                (mainPresenter.locationInteractor.weatherApi != null) &&
                (mainPresenter.networkExecutor != null);
        Log.d(TAG, "Dependency injection: " + (injected ? "OK" : "FAILED"));

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent launchNewPlacePageIntent = new Intent(MainActivity.this, NewPlaceActivity.class);
                startActivityForResult(launchNewPlacePageIntent, 0);
            }
        });

        // Obtain the shared Tracker instance.
        ScreenGoApplication application = (ScreenGoApplication) getApplication();
        tracker = application.getDefaultTracker();

        Fabric.with(this, new Crashlytics());

        initRecyclerView();
        getOutsideBrightnesses();
    }

    @Override
    protected void onStart() {
        super.onStart();
        mainPresenter.attachScreen(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        saveOutsideBrightnesses();
        mainPresenter.detachScreen();
    }

    @Override
    protected void onResume() {
        super.onResume();

        tracker.setScreenName("Image~Main");
        tracker.send(new HitBuilders.ScreenViewBuilder().build());

        mainPresenter.refreshPlaces();
        mainPresenter.refreshWeather(this);
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

        if (id == R.id.menuItemAbout) {
            Intent launchAboutPageIntent = new Intent(MainActivity.this, AboutActivity.class);
            startActivityForResult(launchAboutPageIntent, 0);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void showWeather(final String weatherText, final boolean isSunny) {

        this.isSunny = isSunny;

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                weatherStateTV.setText(weatherText + " (" + (isSunny ? "Sunny" : "Not sunny") + ")");
            }
        });
    }

    @Override
    public void showPlaces(List<Place> places) {
        loadItemsInBackground();
    }

    private void initRecyclerView() {
        recyclerView = (RecyclerView) findViewById(R.id.placeListRecyclerView);
        adapter = new PlaceAdapter();
        loadItemsInBackground();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }

    private void loadItemsInBackground() {
        new AsyncTask<Void, Void, List<Place>>() {

            @Override
            protected List<Place> doInBackground(Void... voids) {
                return Place.listAll(Place.class);
            }

            @Override
            protected void onPostExecute(List<Place> places) {
                super.onPostExecute(places);
                adapter.update(places);
            }
        }.execute();
    }

    private void getOutsideBrightnesses() {
        int defaultBrightness = 50;
        SharedPreferences sharedPref = getPreferences(Context.MODE_PRIVATE);

        int cloudyBrightness = sharedPref.getInt(SHARED_PREF_BRIGHTNESS_CLOUDY, defaultBrightness);
        int sunnyBrightness = sharedPref.getInt(SHARED_PREF_BRIGHTNESS_SUNNY, defaultBrightness);

        outsideBrightnessCloudy.setProgress(cloudyBrightness);
        outsideBrightnessSunny.setProgress(sunnyBrightness);
    }

    private void saveOutsideBrightnesses() {
        SharedPreferences sharedPref = getPreferences(Context.MODE_PRIVATE);

        sharedPref.edit()
                .putInt(SHARED_PREF_BRIGHTNESS_CLOUDY, outsideBrightnessCloudy.getProgress())
                .putInt(SHARED_PREF_BRIGHTNESS_SUNNY, outsideBrightnessSunny.getProgress())
                .apply();
    }
}
