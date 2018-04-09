package com.walkud.self.mvp.base

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.widget.Toast

open class BaseActivity:AppCompatActivity(){

    fun showToast(msg :String){
        Toast.makeText(this,msg,Toast.LENGTH_SHORT).show()
    }

    fun forward(cls:Class<*>){
        val intent = Intent(this,cls)
        startActivity(intent)
    }
}