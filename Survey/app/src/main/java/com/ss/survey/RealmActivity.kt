package com.ss.survey

import android.app.Application
import io.realm.Realm

class RealmActivity : Application() {
    override fun onCreate() {
        super.onCreate()
        Realm.init(this)
    }
}
