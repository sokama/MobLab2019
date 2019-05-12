package com.example.screengo.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.screengo.R;
import com.example.screengo.ScreenGoApplication;
import com.example.screengo.model.Place;

import javax.inject.Inject;

public class NewPlaceActivity extends AppCompatActivity implements NewPlaceScreen{

    private static final String TAG = "NewPlaceActivity_SG";

    private TextView locationText;
    private EditText name;
    private EditText radius;
    private SeekBar brightness;
    private Button cancelButton;
    private Button okButton;

    @Inject
    NewPlacePresenter newPlacePresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_place);

        ScreenGoApplication.injector.inject(this);
        boolean injected = (newPlacePresenter != null) && (newPlacePresenter.placesInteractor != null) && (newPlacePresenter.locationInteractor != null);
        Log.d(TAG, "Dependency injection: " + (injected ? "OK" : "FAILED"));

        locationText = (TextView) findViewById(R.id.newPlaceLocationText);
        name = (EditText) findViewById(R.id.newPlaceName);
        radius = (EditText) findViewById(R.id.newPlaceRadius);
        brightness = (SeekBar) findViewById(R.id.newPlaceBrightness);
        cancelButton = (Button) findViewById(R.id.newPlaceCancelButton);
        okButton = (Button) findViewById(R.id.newPlaceOkButton);

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addPlace(new Place(
                        name.getText().toString(),
                        5, // TODO
                        5, // TODO
                        Float.parseFloat(radius.getText().toString()),
                        brightness.getProgress(),
                        locationText.getText().toString()));
                finish();
            }
        });
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

    public void addPlace(Place place) {
        // TODO: called when the OK button is pressed. Pass the place to the presenter
        newPlacePresenter.addPlace(place);
    }
}
