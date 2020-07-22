package org.maishameds.interviewapp.views

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import kotlinx.android.synthetic.main.activity_main.*
import org.maishameds.interviewapp.R
import org.maishameds.interviewapp.adapter.PostsAdapter
import org.maishameds.interviewapp.network.Status
import org.maishameds.interviewapp.vm.MainViewModel

class MainActivity : AppCompatActivity() {

    private val Tag = "MainActivity"
    private val vm by lazy { ViewModelProvider(this).get(MainViewModel::class.java) }

    private lateinit var adapter: PostsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        adapter = PostsAdapter { position, item ->
            Toast.makeText(this@MainActivity, "${item?.title}", Toast.LENGTH_SHORT).show()
        }

        post_list.setHasFixedSize(true)
        post_list.adapter = adapter



        vm.getPosts().observe(this, Observer {
            Log.d(Tag, "onCreate: ${it.data}")

            when (it.status) {
                Status.SUCCESS -> {
                    Log.d(Tag, "SUCCESS ${it.data}")

                    adapter.setItems(it.data)

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