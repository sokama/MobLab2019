package com.example.screengo;

import com.example.screengo.ui.MainActivity;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component
public interface ScreenGoApplicationComponent {
    void inject (MainActivity mainActivity);
}
