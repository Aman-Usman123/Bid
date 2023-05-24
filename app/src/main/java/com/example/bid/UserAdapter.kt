package com.example.bid

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class UserAdapter(val userList:ArrayList<Bidderposts>
, private val context: Context )
: RecyclerView.Adapter<UserAdapter.getviewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserAdapter.getviewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.listitemrecycler, parent, false)
        return getviewHolder(itemView)
    }
class getviewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
{
    val tv: TextView = itemView.findViewById(R.id.nametext)
val image:ImageView=itemView.findViewById(R.id.imageVi)
}

    override fun getItemCount(): Int {
        return userList.size
    }

    override fun onBindViewHolder(holder:getviewHolder, position: Int) {
        Glide.with(context).load(userList[position].Productimage).into(holder.image)
        holder.tv.text = userList[position].Amount

    }
}