package com.example.poligonos

import android.app.Activity
import android.app.Application
import android.os.Bundle
import com.example.poligonos.ui.main.MainActivity
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class PRUEBATECNICAApplication: Application() {

    override fun onCreate() {
        super.onCreate()


        registerActivityLifecycleCallbacks(object : ActivityLifecycleCallbacks {
            override fun onActivityCreated(p0: Activity, p1: Bundle?) {}

            override fun onActivityStarted(p0: Activity) {
                when (p0) {
                    is MainActivity -> {

                    }
                }
            }

            override fun onActivityResumed(p0: Activity) {}

            override fun onActivityPaused(p0: Activity) {}

            override fun onActivityStopped(p0: Activity) {}

            override fun onActivitySaveInstanceState(p0: Activity, p1: Bundle) {}

            override fun onActivityDestroyed(p0: Activity) {}
        })
    }

}