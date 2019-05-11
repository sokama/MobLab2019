package com.example.screengo.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.TextView;

import com.example.screengo.R;
import com.example.screengo.ScreenGoApplication;
import com.example.screengo.model.Place;

import javax.inject.Inject;

public class NewPlaceActivity extends AppCompatActivity implements NewPlaceScreen{

    private static final String TAG = "NewPlaceActivity";

    @Inject
    NewPlacePresenter newPlacePresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_place);

        ScreenGoApplication.injector.inject(this);
        boolean injected = (newPlacePresenter != null) && (newPlacePresenter.placesInteractor != null) && (newPlacePresenter.locationInteractor != null);
        Log.d(TAG, "Dependency injection: " + (injected ? "OK" : "FAILED"));
    }

    @Override
    protected void onStart() {
        super.onStart();

        /* DEBUG */
        addPlace(new Place("asd", 5, 5, 5f, 5));

        newPlacePresenter.attachScreen(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        newPlacePresenter.detachScreen();
    }

    @Override
    protected void onResume() {
        super.onResume();

        newPlacePresenter.refreshLocation();
    }

    @Override
    public void showLocation(String locationName) {
        // TODO
    }

    public void addPlace(Place place) {
        // TODO: called when the OK button is pressed. Pass the place to the presenter
        newPlacePresenter.addPlace(place);
    }
}
