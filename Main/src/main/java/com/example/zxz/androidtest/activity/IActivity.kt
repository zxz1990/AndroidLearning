package com.example.zxz.androidtest.activity

import android.os.Bundle
import android.util.Log
import android.util.Log as L

interface IActivity {

  fun onCreate()
  fun onCreate(savedInstanceState: Bundle?) {
    L.i("","")
    Log.d("", "")
  }
}