package com.example.screengo.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.screengo.R;
import com.example.screengo.ScreenGoApplication;
import com.example.screengo.model.Place;

import org.w3c.dom.Text;

import java.util.List;

import javax.inject.Inject;

public class MainActivity extends AppCompatActivity implements MainScreen {

    private static final String TAG = "MainActivity_SG";

    @Inject
    MainPresenter mainPresenter;

    private TextView weatherStateTV;

    private boolean isSunny;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        weatherStateTV = findViewById(R.id.weatherStateText);

        /* DEBUG */
        ScreenGoApplication.injector.inject(this);
        boolean injected = (mainPresenter != null) &&
                (mainPresenter.placesInteractor != null) &&
                (mainPresenter.locationInteractor != null) &&
                (mainPresenter.locationInteractor.weatherApi != null) &&
                (mainPresenter.networkExecutor != null);
        Log.d(TAG, "Dependency injection: " + (injected ? "OK" : "FAILED"));

        /* DEBUG */
        mainPresenter.deleteAllPlaces();

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
    }

    @Override
    protected void onStart() {
        super.onStart();
        mainPresenter.attachScreen(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        mainPresenter.detachScreen();
    }

    @Override
    protected void onResume() {
        super.onResume();

        mainPresenter.refreshPlaces();
        mainPresenter.refreshWeather();
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
        Log.d(TAG, "Weather: " + weatherText + " (" + (isSunny ? "Sunny" : "Not sunny") + ")");
    }

    @Override
    public void showPlaces(List<Place> places) {
        Log.d(TAG, "Stored places: " + places.size());
        // TODO
    }

    public void deletePlace(Place place) {
        // TODO: called when a place's delete button is pressed. Pass the place to the presenter
        mainPresenter.deletePlace(place);
    }
}
