package org.maishameds.interviewapp.views

import android.nfc.Tag
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import org.maishameds.interviewapp.R
import org.maishameds.interviewapp.network.Status
import org.maishameds.interviewapp.vm.MainViewModel

class MainActivity : AppCompatActivity() {

    val Tag = "MainActivity"
    val vm by lazy { ViewModelProvider(this).get(MainViewModel::class.java) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        vm.getPosts().observe(this, Observer {
            Log.d(Tag, "onCreate: ${it.data}")

            when(it.status){
                Status.SUCCESS -> {
                    Log.d(Tag, "SUCCESS ${it.data}")
                }

                Status.ERROR -> {
                    //loading IUs etc
                    Log.d(Tag, "ERROR ${it.errorCode} ${it.message}")
                }

                Status.LOADING -> {
                    //loading IUs etc
                    Log.d(Tag, "LOADING DATA")
                }
            }

        })

    }
}