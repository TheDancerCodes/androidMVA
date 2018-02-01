package com.thedancercodes.knownspies;

import android.app.Application;
import android.util.Log;
import com.thedancercodes.knownspies.Dependencies.DependencyRegistry;
import com.thedancercodes.knownspies.ModelLayer.Network.MockWebServer;
import java.io.IOException;
import io.realm.Realm;


public class KnownSpiesApplication extends Application {

    private static final String TAG = "KnownSpiesApplication";

    MockWebServer server;
    private DependencyRegistry registry;

    @Override
    public void onCreate() {
        super.onCreate();

        // Realm.init must be called before assigning the Dependency Registry
        Realm.init(this);

        // Forces Initialization of Dependency Registry
        registry = DependencyRegistry.shared;
        try {
            server = new MockWebServer();
            Log.d(TAG, "Web Server Initialized");
        } catch (IOException e) {
            e.printStackTrace();
            Log.d(TAG, "Failed to create Web Server");
        }
    }
}
