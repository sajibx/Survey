package com.ss.survey

import com.github.kittinunf.fuel.core.ResponseDeserializable
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import io.realm.RealmObject
import java.io.Reader

data class survey(
    var options: String,
    var question: String,
    var required: Boolean,
    var type: String
)
{

    class Deserializer : ResponseDeserializable<Array<survey>> {
        override fun deserialize(content: String): Array<survey>? = Gson().fromJson(content, Array<survey>::class.java)!!
    }

    class ListDeserializer : ResponseDeserializable<List<survey>> {
        override fun deserialize(reader: Reader): List<survey> {
            val type = object : TypeToken<List<survey>>() {}.type
            return Gson().fromJson(reader, type)
        }
    }
}

open class answer(
    var ques1:String? = null,
    var ans1:String? = null,
    var ques2:String? = null,
    var ans2:String? = null,
    var ques3:String? = null,
    var ans3:String? = null,
    var ques4:String? = null,
    var ans4:String? = null,
    var ques5:String? = null,
    var ans5:String? = null
):RealmObject()

data class answers(
    var ques:String? = null,
    var ans:String? = null
)