package org.maishameds.interviewapp.adapter

import android.app.Activity
import android.app.ActivityOptions
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.single_list_item.*
import org.maishameds.interviewapp.R
import org.maishameds.interviewapp.models.PostModelItem
import org.maishameds.interviewapp.views.DetailActivity
import org.maishameds.interviewapp.views.DetailActivity.Companion.POST_EXTRA

/**
 * Created by Job on Nov 7-2019.
 */

class PostsAdapter(private val context: Activity) : RecyclerView.Adapter<PostsAdapter.MyVH>() {
    private var models: List<PostModelItem?>? = listOf()


    override fun onCreateViewHolder(parent: ViewGroup, i: Int): MyVH {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.single_list_item, parent, false)

        return MyVH(itemView, context)
    }

    override fun onBindViewHolder(myVH: MyVH, i: Int) {
        val model = models!![i]
        myVH.setUI(model)
    }

    override fun getItemCount(): Int {
        return models?.size ?: 0
    }

    fun setItems(newModels: List<PostModelItem?>?) {
        models = newModels
        notifyDataSetChanged()
    }

    fun getItem(position: Int): PostModelItem? {
        return models?.get(position)
    }

    inner class MyVH(itemView: View, private val context: Activity) :
        RecyclerView.ViewHolder(itemView) {

        private var title: TextView = itemView.findViewById(R.id.title)
        private var userId: TextView = itemView.findViewById(R.id.userId)
        private var badge: CardView = itemView.findViewById(R.id.badge)
        private var body: TextView = itemView.findViewById(R.id.body)
        private var card: CardView = itemView.findViewById(R.id.card)
        private var model: PostModelItem? = null

        init {
            card.setOnClickListener {
                //itemClick.invoke(adapterPosition, model)

                //Toast.makeText(context, "model", Toast.LENGTH_SHORT).show()

                val pair1: android.util.Pair<View, String> =
                    android.util.Pair.create(title, "t1")

                val pair2: android.util.Pair<View, String> =
                    android.util.Pair.create(badge, "t2")

                val pair3: android.util.Pair<View, String> =
                    android.util.Pair.create(body, "t3")

                val activityOptions = ActivityOptions.makeSceneTransitionAnimation(
                    context, pair1, pair2, pair3
                )

                val intent = Intent(context, DetailActivity::class.java)
                intent.putExtra(POST_EXTRA, model)
                context.startActivity(intent, activityOptions.toBundle())
            }
        }

        fun setUI(model: PostModelItem?) {
            this.model = model
            title.text = model?.title
            userId.text = "${model?.userId}"
            body.text = model?.body
        }
    }
}
