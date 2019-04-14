package com.example.screengo.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.example.screengo.R;
import com.example.screengo.ScreenGoApplication;

import javax.inject.Inject;

public class NewPlaceActivity extends AppCompatActivity implements NewPlaceScreen{

    @Inject
    NewPlacePresenter newPlacePresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_place);

        ScreenGoApplication.injector.inject(this);
        boolean injected = (newPlacePresenter != null) && (newPlacePresenter.placesInteractor != null) && (newPlacePresenter.locationInteractor != null);
        TextView debugTextView = findViewById(R.id.newPlaceDebugText);
        debugTextView.append("Dependency injection: " + (injected ? "OK" : "FAILED") + "\n");
    }

    @Override
    protected void onStart() {
        super.onStart();
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

    public void addPlace() {
        // TODO: called when the OK button is pressed. Pass the palce to the presenter
        newPlacePresenter.addPlace();
    }
}
