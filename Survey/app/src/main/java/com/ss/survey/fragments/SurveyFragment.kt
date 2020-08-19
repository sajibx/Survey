package com.ss.survey.fragments


import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import com.github.kittinunf.fuel.httpGet
import com.github.kittinunf.fuel.httpPost
import com.ss.survey.*

import io.realm.Realm
import kotlinx.android.synthetic.main.fragment_survey.*
import kotlinx.coroutines.launch
import kotlin.math.sign

/**
 * A simple [Fragment] subclass.
 */
class SurveyFragment : BaseFragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_survey, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //val realm = Realm.getDefaultInstance()


        count.text = data.index.toString()+" of "+data.list.size.toString()
        Toast.makeText(context, data.myans.size.toString(), Toast.LENGTH_LONG).show()

        load()

        if (data.index<data.list.size)
        {
            next.setOnClickListener()
            {
                if (data.list[data.index-1].required)
                {
                    if (data.state == "not null")
                    {
                        data.index++
                        data.state = "null"
                        //data.myans.add(answers(data.list[data.index-1].question, data.ans))
                        //data.myans.add(answers("sdsdsd","dsdsdsd"))
                        data.myans.add(answers(data.list[data.index-2].question,data.ans))
                        data.ans = "null"

                        val transaction = fragmentManager!!.beginTransaction()
                        var survey_fragment = SurveyFragment()
                        transaction.replace(R.id.surveys, survey_fragment)
                        //transaction.addToBackStack(null)
                        transaction.commit()
                    }
                    else
                    {
                        Toast.makeText(context, "input mandetory", Toast.LENGTH_LONG).show()
                    }
                }
                else if (!data.list[data.index-1].required)
                {
                    data.index++
                    data.state = "null"
                    //data.myans.add(answers(data.list[data.index].question, data.ans))
                    data.myans.add(answers(data.list[data.index-2].question,data.ans))
                    data.ans = "null"

                    val transaction = fragmentManager!!.beginTransaction()
                    var survey_fragment = SurveyFragment()
                    transaction.replace(R.id.surveys, survey_fragment)
                    //transaction.addToBackStack(null)
                    transaction.commit()
                }

            }
        }
        else if (data.index==data.list.size)
        {
            next.visibility = View.INVISIBLE
            save.visibility = View.VISIBLE

            save.setOnClickListener()
            {



                if (data.list[data.index-1].required)
                {
                    if (data.state == "not null")
                    {
                        data.state = "null"
                        data.myans.add(answers(data.list[data.index-1].question,data.ans))

                        activity!!.finish()
                        data.index = 1
                        save()
                    }
                    else
                    {
                        Toast.makeText(context, "input mandetory", Toast.LENGTH_LONG).show()
                    }
                }
                else if (!data.list[data.index-1].required)
                {
                    data.state = "null"
                    //data.myans.add(answers(data.list[data.index].question, data.ans))
                    data.myans.add(answers(data.list[data.index-1].question,data.ans))

                    activity!!.finish()
                    startActivity(Intent(context, MainActivity::class.java))
                    data.index = 1
                    save()
                }



            }
        }

    }


    fun load()
    {
        ques.text = data.list[data.index-1].question

        if (data.list[data.index-1].type=="Checkbox")
        {
            checkbox.visibility = View.VISIBLE
            loadcheckbox()
        }
        else if (data.list[data.index-1].type=="dropdown")
        {
            dropdown.visibility = View.VISIBLE
            loaddropdown()
        }
        else if(data.list[data.index-1].type=="multiple choice")
        {
            multiple.visibility = View.VISIBLE
            loadmultiple()
        }
        else if(data.list[data.index-1].type=="number")
        {
            number.visibility = View.VISIBLE
            loadnumber()
        }
        else if (data.list[data.index-1].type=="text")
        {
            text.visibility = View.VISIBLE
            loadtext()
        }
    }


    fun loadcheckbox()
    {
        val strs = data.list[data.index-1].options.split(",").toTypedArray()
        ch1.text = strs[0]
        ch2.text = strs[1]

        ch1.setOnClickListener()
        {
            data.ans = strs[0]
            ch2.isChecked = false
            data.state = "not null"
        }

        ch2.setOnClickListener()
        {
            data.ans = strs[1]
            ch1.isChecked = false
            data.state = "not null"
        }
    }

    fun loaddropdown()
    {
        val strs = data.list[data.index-1].options.split(",").toTypedArray()

        spinner.adapter = ArrayAdapter<String>(context!!,android.R.layout.simple_list_item_1, strs)

        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(p0: AdapterView<*>?) {
                TODO("Not yet implemented")
            }

            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                data.ans = strs.get(p2)
                data.state = "not null"
            }

        }
    }

    fun loadmultiple()
    {
        val strs = data.list[data.index-1].options.split(",").toTypedArray()
        r1.text = strs[0]
        r2.text = strs[1]
        r3.text = strs[2]
        r4.text = strs[3]
        r5.text = strs[4]

        r1.setOnClickListener()
        {
            data.ans = strs[0]
            r2.isChecked
            r3.isChecked = false
            r4.isChecked = false
            r5.isChecked = false
            data.state = "not null"
        }
        r2.setOnClickListener()
        {
            data.ans = strs[1]
            r1.isChecked = false
            r3.isChecked = false
            r4.isChecked = false
            r5.isChecked = false
            data.state = "not null"
        }
        r3.setOnClickListener()
        {
            data.ans = strs[2]
            r1.isChecked = false
            r2.isChecked = false
            r4.isChecked = false
            r5.isChecked = false
            data.state = "not null"
        }
        r4.setOnClickListener()
        {
            data.ans = strs[3]
            r1.isChecked = false
            r2.isChecked = false
            r3.isChecked = false
            r5.isChecked = false
            data.state = "not null"
        }
        r5.setOnClickListener()
        {
            data.ans = strs[4]
            r1.isChecked = false
            r2.isChecked = false
            r3.isChecked = false
            r4.isChecked = false
            data.state = "not null"
        }
    }

    fun loadnumber()
    {
        mynumber.addTextChangedListener(object : TextWatcher {

            override fun afterTextChanged(s: Editable) {}

            override fun beforeTextChanged(
                s: CharSequence, start: Int,
                count: Int, after: Int
            ) {
            }

            override fun onTextChanged(
                s: CharSequence, start: Int,
                before: Int, count: Int
            ) {
                if (mynumber.text.toString().length>=2)
                {
                    data.ans = s.toString()
                    data.state = "not null"
                }
                else
                {
                    data.state = "null"
                }
            }
        })
    }

    fun loadtext()
    {
        mytext.addTextChangedListener(object : TextWatcher {

            override fun afterTextChanged(s: Editable) {}

            override fun beforeTextChanged(
                s: CharSequence, start: Int,
                count: Int, after: Int
            ) {
            }

            override fun onTextChanged(
                s: CharSequence, start: Int,
                before: Int, count: Int
            ) {
                if (mytext.text.toString().length>=2)
                {
                    data.ans = s.toString()
                    data.state = "not null"
                }
                else
                {
                    data.state = "null"
                }
            }
        })
    }

    fun save()
    {



        Realm.init(context!!)
        val realm = Realm.getDefaultInstance()
        realm.beginTransaction()
        val datas = answer(data.myans[0].ques,data.myans[0].ans,data.myans[1].ques,data.myans[1].ans,data.myans[2].ques,data.myans[2].ans,data.myans[3].ques,data.myans[3].ans,data.myans[4].ques,data.myans[4].ans)
        realm.copyToRealm(datas)
        realm.commitTransaction()

    }


    fun some()
    {
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

                        }


                    }
                }

        }
    }


}
