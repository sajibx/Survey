package com.ss.survey

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import io.realm.Realm
import io.realm.RealmResults
import java.lang.Exception

class RealmAdapter(
    var listdata: RealmResults<answer>,
    var recycler: RecyclerView,
    var realm: Realm,
    var message: TextView
) :RecyclerView.Adapter<RealmAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RealmAdapter.ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.listitem, parent, false)
        return ViewHolder(v)
    }

    override fun getItemCount(): Int {
        return listdata.size
    }

    override fun onBindViewHolder(holder: RealmAdapter.ViewHolder, position: Int) {
        var data = listdata[position]
        holder.q1.text = data!!.ques1
        holder.q2.text = data!!.ques2
        holder.q3.text = data!!.ques3
        holder.q4.text = data!!.ques4
        holder.q5.text = data!!.ques5

        holder.a1.text = "- "+data.ans1
        holder.a2.text = "- "+data.ans2
        holder.a3.text = "- "+data.ans3
        holder.a4.text = "- "+data.ans4
        holder.a5.text = "- "+data.ans5

        holder.delete.setOnClickListener()
        {
            try {
                realm.beginTransaction()
                realm.where(answer::class.java).equalTo("ans1",data.ans1).equalTo("ans2",data.ans2).equalTo("ans3",data.ans3).equalTo("ans4",data.ans4).equalTo("ans5",data.ans5).findFirst()!!.deleteFromRealm()
                var mydata  = realm.where(answer::class.java).findAll()
                val adapter = RealmAdapter(mydata, recycler, realm, message)
                recycler.adapter = adapter
                realm.commitTransaction()
                if (mydata.size<1)
                {
                    message.visibility = View.VISIBLE
                }
                Toast.makeText(holder.itemView.context, "Removed", Toast.LENGTH_LONG).show()
            }catch (e:Exception)
            {
                realm.beginTransaction()
                realm.where(answer::class.java).equalTo("ans1",data.ans1).equalTo("ans2",data.ans2).equalTo("ans3",data.ans3).equalTo("ans4",data.ans4).equalTo("ans5",data.ans5).findFirst()!!.deleteFromRealm()
                var mydata  = realm.where(answer::class.java).findAll()
                val adapter = RealmAdapter(mydata, recycler, realm, message)
                recycler.adapter = adapter
                realm.commitTransaction()
                if (mydata.size<1)
                {
                    message.visibility = View.VISIBLE
                }
                Toast.makeText(holder.itemView.context, "Removed", Toast.LENGTH_LONG).show()
            }
        }
    }

    class ViewHolder(item: View):RecyclerView.ViewHolder(item)
    {
        var q1 = item.findViewById(R.id.q1) as TextView
        var q2 = item.findViewById(R.id.q2) as TextView
        var q3 = item.findViewById(R.id.q3) as TextView
        var q4 = item.findViewById(R.id.q4) as TextView
        var q5 = item.findViewById(R.id.q5) as TextView

        var a1 = item.findViewById(R.id.a1) as TextView
        var a2 = item.findViewById(R.id.a2) as TextView
        var a3 = item.findViewById(R.id.a3) as TextView
        var a4 = item.findViewById(R.id.a4) as TextView
        var a5 = item.findViewById(R.id.a5) as TextView

        var delete = item.findViewById(R.id.delete) as ImageView
    }
}