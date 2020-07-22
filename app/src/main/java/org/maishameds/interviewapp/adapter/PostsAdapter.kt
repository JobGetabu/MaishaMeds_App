package org.maishameds.interviewapp.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import org.maishameds.interviewapp.R
import org.maishameds.interviewapp.models.PostModelItem

/**
 * Created by Job on Nov 7-2019.
 */

class PostsAdapter(
    val itemClick: (position: Int, item: PostModelItem?) -> Unit
) : RecyclerView.Adapter<PostsAdapter.MyVH>() {
    private var models: List<PostModelItem?>? = listOf()


    override fun onCreateViewHolder(parent: ViewGroup, i: Int): MyVH {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.single_list_item, parent, false)

        return MyVH(itemView, parent.context)
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

    inner class MyVH(itemView: View, private val context: Context) :
        RecyclerView.ViewHolder(itemView) {

        private var title: TextView = itemView.findViewById(R.id.title)
        private var userId: TextView = itemView.findViewById(R.id.userId)
        private var body: TextView = itemView.findViewById(R.id.body)
        private var card: CardView = itemView.findViewById(R.id.card)
        private var model: PostModelItem? = null

        init {
            card.setOnClickListener {
                itemClick.invoke(adapterPosition, model)
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
