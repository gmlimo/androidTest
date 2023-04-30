package org.bedu.s4reto2

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class RecyclerAdapter(val games: List<Game>): RecyclerView.Adapter<RecyclerAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_game, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerAdapter.ViewHolder, position: Int) {
        val game = games[position]
        holder.bind(game)
    }

    override fun getItemCount(): Int {
        return games.size
    }


    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val image = view.findViewById<ImageView>(R.id.imageView)
        private val name = view.findViewById<TextView>(R.id.nameText)
        private val genre = view.findViewById<TextView>(R.id.genreText)
       // private val rating = view.findViewById<RatingBar>(R.id.ratingBar)
        private val classification = view.findViewById<TextView>(R.id.classText)


        fun bind(game: Game) {
            image.setImageResource(game.imageId)
            name.text = game.name
            genre.text = game.genre
            classification.text = game.classification
        }
    }
}

