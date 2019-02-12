package ru.listqueue

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView

class QueueRecyclerViewAdapter constructor(val members: ArrayList<Member>, val longClickListener: (Member) -> Unit) :
    RecyclerView.Adapter<QueueRecyclerViewAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val item_queue_text = itemView.findViewById<TextView>(R.id.item_queue_text)
        val star = itemView.findViewById<ImageView>(R.id.star)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.item_queue_text?.text = members[position].name
        holder.star.setImageResource(R.drawable.ic_star_30dp)
    }

    override fun onCreateViewHolder(parent: ViewGroup, position: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_queue, parent, false)
        return ViewHolder(v)
    }

    override fun getItemCount(): Int {
        return members.size
    }

    override fun onBindViewHolder(
        holder: QueueRecyclerViewAdapter.ViewHolder,
        position: Int,
        payloads: MutableList<Any>
    ) {
        var item = members[position]
        holder.itemView.setOnLongClickListener {
            longClickListener(item)
            true
        }
        super.onBindViewHolder(holder, position, payloads)
    }
}