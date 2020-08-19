package com.ss.survey

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.ss.survey.fragments.SurveyFragment

class MySurveyActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_survey)
        supportActionBar?.hide()



        val transaction = this.supportFragmentManager.beginTransaction()
        var survey_fragment = SurveyFragment()
        transaction.replace(R.id.surveys, survey_fragment)
        //transaction.addToBackStack(null)
        transaction.commit()
    }
}
