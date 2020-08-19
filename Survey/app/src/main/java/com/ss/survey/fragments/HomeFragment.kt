package com.ss.survey.fragments


import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.github.kittinunf.fuel.httpGet
import com.ss.survey.*
import io.realm.Realm
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.coroutines.launch


/**
 * A simple [Fragment] subclass.
 */
class HomeFragment : BaseFragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        load()
        add.setOnClickListener()
        {


            Toast.makeText(context, "Please wait", Toast.LENGTH_LONG).show()

            data.index = 1

            launch {

                "https://example-response.herokuapp.com/getSurvey"
                    .httpGet()
                    .header("Content-Type" to "application/json")
                    //.responseString()
                    .responseObject(survey.Deserializer())
                    { request, response, result ->

                        when (result) {
                            is com.github.kittinunf.result.Result.Failure  -> {

                                val ex = result.getException()
                            }
                            is com.github.kittinunf.result.Result.Success -> {


                                data.list = result.value
                                updateUI {
                                    startActivity(Intent(context, MySurveyActivity::class.java))
                                }

                            }


                        }
                    }

            }


            //startActivity(Intent(context, MySurveyActivity::class.java))
        }


    }

    override fun onResume() {
        super.onResume()
        load()
    }

    fun load()
    {
        data.myans.clear()
        Realm.init(context!!)
        val realm = Realm.getDefaultInstance()
        var data = realm.where(answer::class.java).findAll()

        if (data.size>0)
        {
            message.visibility = View.INVISIBLE
        }
        val recycler  = view!!.findViewById<RecyclerView>(R.id.recycler)
        recycler.layoutManager = LinearLayoutManager(context, LinearLayout.VERTICAL,false) as RecyclerView.LayoutManager?

        val adapter = RealmAdapter(data,recycler,realm,message)

        recycler.adapter = adapter

    }


}
