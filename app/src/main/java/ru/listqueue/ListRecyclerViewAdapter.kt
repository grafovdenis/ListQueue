package ru.listqueue

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

class ListRecyclerViewAdapter constructor(val queueList: ArrayList<Queue>, val clickListener: (Queue) -> Unit) :
    RecyclerView.Adapter<ListRecyclerViewAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val txtName = itemView.findViewById<TextView>(R.id.txtName)
        val txtNum = itemView.findViewById<TextView>(R.id.txtNum)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.txtName?.text = queueList[position].name
        holder.txtNum?.text = queueList[position].numInQueue
    }

    override fun onCreateViewHolder(parent: ViewGroup, position: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_layout, parent, false)
        return ViewHolder(v)
    }

    override fun getItemCount(): Int {
        return queueList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int, payloads: MutableList<Any>) {
        var item = queueList[position]
        holder.itemView.setOnClickListener { clickListener(item) }
        super.onBindViewHolder(holder, position, payloads)
    }

//    override fun onBindViewHolder(holder: ViewHolder, position: Int, payloads: MutableList<Any>) {
//        holder.itemView.setOnClickListener {
//            super.onBindViewHolder(holder, position, payloads)
//        }
//        holder.itemView.setOnLongClickListener{
//            true
//        }
//        super.onBindViewHolder(holder, position, payloads)
//    }
}