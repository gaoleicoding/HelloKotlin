package com.gl.kotlin

import android.app.Activity
import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.os.Bundle
import android.util.Log


class App : Application() {

    override fun onCreate() {
        super.onCreate()
        connectivityManager = applicationContext
                .getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        context = this


    }

    init {
        registerActivityLifecycleCallbacks(object : ActivityLifecycleCallbacks {
            override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
                Log.e("Lifecycle", activity.localClassName + " was Created" + "activity==null   "
                        + (activity == null) + "     activity.isFinishing()  " + activity.isFinishing + "    activity.isDestroyed()  " + activity.isDestroyed)
            }

            override fun onActivityStarted(activity: Activity) {
                Log.e("Lifecycle", activity.localClassName + " was Started" + "activity==null   "
                        + (activity == null) + "     activity.isFinishing()   " + activity.isFinishing + "   activity.isDestroyed()  " + activity.isDestroyed)
            }

            override fun onActivityResumed(activity: Activity) {
                Log.e("Lifecycle", activity.localClassName + " was oResumed" + "activity==null   "
                        + (activity == null) + "activity.isFinishing()   " + activity.isFinishing + "activity.isDestroyed() " + activity.isDestroyed)
            }

            override fun onActivityPaused(activity: Activity) {
                Log.e("Lifecycle", activity.localClassName + " was Pauseed" + "activity==null   "
                        + (activity == null) + "activity.isFinishing()   " + activity.isFinishing + "activity.isDestroyed()  " + activity.isDestroyed)
            }

            override fun onActivityStopped(activity: Activity) {
                Log.e("Lifecycle", activity.localClassName + " was Stoped" + "activity==null    "
                        + (activity == null) + "activity.isFinishing()   " + activity.isFinishing + "activity.isDestroyed() " + activity.isDestroyed)
            }

            override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle?) {
                Log.e("Lifecycle", activity.localClassName + " was SaveInstanceState" + "activity==null "
                        + (activity == null) + "activity.isFinishing()   " + activity.isFinishing + "activity.isDestroyed()  " + activity.isDestroyed)
            }

            override fun onActivityDestroyed(activity: Activity) {
                Log.e("Lifecycle", activity.localClassName + " was Destroyed" + "activity==null"
                        + (activity == null) + "  activity.isFinishing()  " + activity.isFinishing + "  activity.isDestroyed()" + activity.isDestroyed)
            }
        })
    }

    //伴生对象（Companion object），在Kotlin中是没有static关键字的，也就是意味着没有了静态方法和静态成员。
    // 那么在kotlin中如果要想表示这种概念，取而代之的是包级别函数（package-level function）和我们这里提到的伴生对象
    companion object {
        lateinit var connectivityManager: ConnectivityManager
        lateinit var context: Context

    }


}
