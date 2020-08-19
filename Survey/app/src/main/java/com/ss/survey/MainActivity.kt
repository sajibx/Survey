package com.ss.survey

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.ss.survey.fragments.HomeFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar?.hide()

        val transaction = this.supportFragmentManager!!.beginTransaction()
        var home_fragment = HomeFragment()
        transaction.replace(R.id.universal, home_fragment)
        //transaction.addToBackStack(null)
        transaction.commit()
    }
}
