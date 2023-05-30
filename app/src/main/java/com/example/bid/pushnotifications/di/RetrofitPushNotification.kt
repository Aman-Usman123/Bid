package com.example.bid


import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitPushNotification {

    fun getAPi(): PushNotificationAPI{
      var e=  Retrofit.Builder().baseUrl(Constatns.FIREBASE_MESSAGE_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

      return  e.create(PushNotificationAPI::class.java)
    }

}