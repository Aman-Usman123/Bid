package com.example.bid

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy

class imageAdapter(private  val imageslist:ArrayList<imagess>,  val userList:ArrayList<usserImages>,
                   private val context:Context):RecyclerView.Adapter<imageAdapter.imageViewHolder>() {
    class imageViewHolder(itemView:View):RecyclerView.ViewHolder(itemView)
    {

val image:ImageView=itemView.findViewById(R.id.imageView2)
        val tv:TextView=itemView.findViewById(R.id.Tv)
        val tv1:TextView=itemView.findViewById(R.id.Tv1)
        val tv2:TextView=itemView.findViewById(R.id.Tv2)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): imageViewHolder {

        val itemView=LayoutInflater.from(parent.context).inflate(R.layout.listitemrecycler,parent,false)
    return imageViewHolder(itemView)
    }

    override fun getItemCount(): Int {

        return imageslist.size
        return userList.size
    }

    override fun onBindViewHolder(holder: imageViewHolder, position: Int) {

        Glide.with(context).load(imageslist[position].ImageUrl).into(holder.image)
        holder.tv.text=userList[position].product
        holder.tv1.text=userList[position].catagory
        holder.tv2.text=userList[position].weight

    }



}