package com.example.zxz.androidtest.activity

import android.app.Activity
import android.os.Bundle

class KTActivity: Activity(), IActivity {
  override fun onCreate(savedInstanceState: Bundle?) {
    super<Activity>.onCreate(savedInstanceState)
    super<IActivity>.onCreate(savedInstanceState)
  }
  override fun onCreate() {
  }
}