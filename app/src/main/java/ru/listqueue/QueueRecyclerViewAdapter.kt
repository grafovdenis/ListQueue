package ru.listqueue

import android.graphics.Color
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView

class QueueRecyclerViewAdapter constructor(var members: ArrayList<Member>, val longClickListener: (Member) -> Unit) :
    RecyclerView.Adapter<QueueRecyclerViewAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val item_queue_text = itemView.findViewById<TextView>(R.id.item_queue_text)
        var star = itemView.findViewById<ImageView>(R.id.star)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = members[position]
        holder.item_queue_text?.text = item.name
        holder.star.setImageResource(R.drawable.ic_star_30dp)
        if (members[position].followed) {
            holder.star.setColorFilter(Color.GREEN)
        }
        holder.itemView.setOnLongClickListener {
            longClickListener(item)
            true
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, position: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_queue, parent, false)
        return ViewHolder(v)
    }

    override fun getItemCount(): Int {
        return members.size
    }

    fun clear() {
        members.clear()
        notifyDataSetChanged()
    }

    fun addAll(list: ArrayList<Member>) {
        members.addAll(list)
        notifyDataSetChanged()
    }
}