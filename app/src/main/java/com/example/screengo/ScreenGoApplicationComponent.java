package com.example.screengo;

import com.example.screengo.ui.MainActivity;
import com.example.screengo.ui.NewPlaceActivity;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component
public interface ScreenGoApplicationComponent {
    void inject (MainActivity mainActivity);
    void inject (NewPlaceActivity newPlaceActivity);
}
