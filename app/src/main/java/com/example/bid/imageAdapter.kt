package com.example.bid

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import android.os.CountDownTimer
import android.os.Handler
import android.os.SystemClock
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import java.util.concurrent.TimeUnit

class imageAdapter(val userList:ArrayList<usserImages>
                   , private val context: Context )
:RecyclerView.Adapter<imageAdapter.imageViewHolder>() {

    private lateinit var listener: OnItemClickListener

    interface OnItemClickListener {
        fun onItemClick(position: Int)
    }

    fun setOnItemClickListener(listener: OnItemClickListener) {
        this.listener = listener
    }


    class imageViewHolder(itemView: View, listener: OnItemClickListener) :
        RecyclerView.ViewHolder(itemView) {


        //   val timerTextView: TextView = itemView.findViewById(R.id.text_diplay)

        val image: ImageView = itemView.findViewById(R.id.imageVi)
        val tv: TextView = itemView.findViewById(R.id.nametext)
       // val tv1: TextView = itemView.findViewById(R.id.Tv1)
       // val tv2: TextView = itemView.findViewById(R.id.Tv2)

        init {


            itemView.setOnClickListener {
                listener?.onItemClick(adapterPosition)
            }

        }


    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): imageViewHolder {

        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.listitemrecycler, parent, false)
        return imageViewHolder(itemView, listener)
    }

    override fun getItemCount(): Int {


        return userList.size
    }


    override fun onBindViewHolder(holder: imageViewHolder, position: Int) {



        Glide.with(context).load(userList[position].ImageUrl).into(holder.image)
        holder.tv.text = userList[position].product
        //holder.tv1.text = userList[position].catagory
        //holder.tv2.text = userList[position].weight


    }


}