package com.example.screengo;

import com.example.screengo.network.NetworkModule;
import com.example.screengo.ui.MainActivity;
import com.example.screengo.ui.NewPlaceActivity;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {NetworkModule.class})
public interface ScreenGoApplicationComponent {
    void inject (MainActivity mainActivity);
    void inject (NewPlaceActivity newPlaceActivity);
}
