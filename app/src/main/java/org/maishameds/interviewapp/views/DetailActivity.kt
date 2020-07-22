package org.maishameds.interviewapp.views

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_detail.*
import org.maishameds.interviewapp.R
import org.maishameds.interviewapp.models.PostModelItem

class DetailActivity : AppCompatActivity() {

    companion object{
        const val POST_EXTRA = "POST_EXTRA"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        val postModelItem = intent.getParcelableExtra<PostModelItem>(POST_EXTRA)
        if (postModelItem == null){
            finish()
            return
        }

        title_v.text = postModelItem?.title
        userId.text = "${postModelItem?.userId}"
        body.text = postModelItem?.body
    }

    //injected to a view
    fun close(v: View){
        onBackPressed()
    }
}