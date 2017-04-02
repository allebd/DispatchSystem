package com.allebd.dispatchsystem;

import android.app.Application;

import com.allebd.dispatchsystem.dagger.AppComponent;
import com.allebd.dispatchsystem.dagger.AppModule;
import com.allebd.dispatchsystem.dagger.DaggerAppComponent;
import com.google.firebase.auth.FirebaseAuth;


public class DispatchApplication extends Application{
    private static DispatchApplication instance = new DispatchApplication();
    private static AppComponent appComponent;

    public static DispatchApplication getInstance(){
        return instance;
    }

    @Override
    public void onCreate(){
        super.onCreate();
        getAppComponent();
        FirebaseAuth.getInstance();
    }

    public AppComponent getAppComponent(){
        if (appComponent ==null){
            appComponent = DaggerAppComponent.builder()
                    .appModule(new AppModule(this))
                    .build();
        }
        return appComponent;
    }
}
