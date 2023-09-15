package edu.upbc.sra.utils

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import edu.upbc.sra.R

class RecyclerAdapter(val items: List<Option>,
                      private val listener: OnItemClickListener
): RecyclerView.Adapter<RecyclerAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int {
        return items.size
    }


    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view), View.OnClickListener {
        private val image = view.findViewById<ImageView>(R.id.imageView)
        private val option = view.findViewById<TextView>(R.id.optionText)

        init {
            view.setOnClickListener(this)
        }

        fun bind(item: Option) {
            image.setImageResource(item.imageResource)
            option.text = item.option
        }

        override fun onClick(v: View?) {
            val position: Int = adapterPosition
            if (position != RecyclerView.NO_POSITION){
                listener.onItemClick(position)
            }
        }
    }
    interface OnItemClickListener {
        fun onItemClick(position: Int)
    }
}