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
:RecyclerView.Adapter<imageAdapter.imageViewHolder>()

{  private val timers: MutableList<CountDownTimer> = mutableListOf()
         private lateinit var listener: OnItemClickListener

         interface OnItemClickListener {
         fun onItemClick(position: Int)
     }
         fun setOnItemClickListener(listener: OnItemClickListener) {
             this.listener = listener
         }




         class imageViewHolder(itemView:View,listener: OnItemClickListener):RecyclerView.ViewHolder(itemView)
         {    private val timeTextView: TextView = itemView.findViewById(R.id.counterrr)

             private var countDownTimer: CountDownTimer? = null
             private lateinit var sharedPreferences: SharedPreferences

             fun bind(startTime: Long) {
                 stopCountdown()

                 val intent = Intent(itemView.context, CountdownService::class.java)
                 intent.putExtra(CountdownService.EXTRA_START_TIME, startTime)
                 itemView.context.startService(intent)

                 sharedPreferences = itemView.context.getSharedPreferences("CountdownPrefs", Context.MODE_PRIVATE)
                 val timeLeft = sharedPreferences.getLong("timeLeft", startTime)
                 startCountdown(timeLeft)
             }

             fun stopCountdown() {
                 countDownTimer?.cancel()
             }

             private fun startCountdown(startTime: Long) {
                 countDownTimer = object : CountDownTimer(startTime, 1000) {
                     override fun onTick(millisUntilFinished: Long) {
                         val minutes = TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished)
                         val seconds = TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) % 60
                         timeTextView.text = String.format("%02d:%02d", minutes, seconds)
                     }

                     override fun onFinish() {
                         timeTextView.text = "00:00"
                     }
                 }
                 countDownTimer?.start()
             }

          //   val timerTextView: TextView = itemView.findViewById(R.id.text_diplay)

             val image:ImageView=itemView.findViewById(R.id.imageView2)
             val tv:TextView=itemView.findViewById(R.id.Tv)
             val tv1:TextView=itemView.findViewById(R.id.Tv1)
             val tv2:TextView=itemView.findViewById(R.id.Tv2)

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

       holder.bind( TimeUnit.MINUTES.toMillis(5))

        Glide.with(context).load(userList[position].ImageUrl).into(holder.image)
        holder.tv.text=userList[position].product
        holder.tv1.text=userList[position].catagory
        holder.tv2.text=userList[position].weight

    }
         override fun onViewRecycled(holder: imageViewHolder) {
             // Cancel the countdownTimer for this item when it is recycled
             val countdownTimer = holder.itemView.tag as? CountDownTimer
             countdownTimer?.cancel()
             holder.itemView.tag = null
             super.onViewRecycled(holder)
         }
}



