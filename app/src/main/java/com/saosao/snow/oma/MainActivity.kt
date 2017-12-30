package com.saosao.snow.oma

import android.support.v7.app.AppCompatActivity
import android.os.Bundle

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var fragment = GameMainFragment();
        getSupportFragmentManager().beginTransaction().add(R.id.framelayout,fragment).commit();
    }
}
