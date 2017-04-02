package com.allebd.dispatchsystem.dagger;

import android.content.Context;

import com.allebd.dispatchsystem.DispatchApplication;
import com.allebd.dispatchsystem.data.AppDataManager;
import com.allebd.dispatchsystem.data.DataManager;
import com.allebd.dispatchsystem.location.LocationHelper;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Digz on 02/04/2017.
 */
@Module
public class AppModule {

    private final DispatchApplication app;

    public AppModule(DispatchApplication app){
        this.app = app;
    }

    @Provides
    @Singleton
    public Context providesContext(){
        return app;
    }

    @Provides
    @Singleton
    public LocationHelper providesLocationHelper(Context context){
        return new LocationHelper(context);
    }

    @Provides
    @Singleton
    public DataManager.Operations providesDataManagerOps(){
        return new AppDataManager();
    }
}
