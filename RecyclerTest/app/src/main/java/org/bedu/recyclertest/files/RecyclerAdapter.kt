package org.bedu.recyclertest.files

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import org.bedu.recyclertest.R

class RecyclerAdapter(val items: List<CartItem>,
                      private val listener: OnItemClickListener
): RecyclerView.Adapter<RecyclerAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerAdapter.ViewHolder, position: Int) {
        val item = items[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int {
        return items.size
    }


    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view), View.OnClickListener {
        private val image = view.findViewById<ImageView>(R.id.imageView)
        private val product = view.findViewById<TextView>(R.id.productText)
        private val quantity = view.findViewById<TextView>(R.id.quantityText)
        private val price = view.findViewById<TextView>(R.id.subtotal)

        init {
            view.setOnClickListener(this)
        }

        fun bind(item: CartItem) {
            image.setImageResource(item.imageResourceId)
            product.text = item.product
            quantity.text = item.quantity.toString()
            price.text = item.price.toString()
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