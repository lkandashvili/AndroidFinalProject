package com.example.finalproject

import android.media.Image
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView

class ViewPagerAdapter(private var title: List<String>, private var name: List<String>, private var image: List<Int>): RecyclerView.Adapter<ViewPagerAdapter.Pager2ViewHolder>(){

    inner class Pager2ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

        val productTitle: TextView = itemView.findViewById(R.id.productTitle)
        val productName: TextView = itemView.findViewById(R.id.productName)
        val productImage: ImageView = itemView.findViewById(R.id.image)

        init {
            productImage.setOnClickListener{v: View ->
                val position = adapterPosition
                Toast.makeText(itemView.context, "You clicked on item#${position + 1}", Toast.LENGTH_LONG).show()
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewPagerAdapter.Pager2ViewHolder {
        return Pager2ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_page, parent, false))
    }

    override fun onBindViewHolder(holder: ViewPagerAdapter.Pager2ViewHolder, position: Int) {
        holder.productTitle.text = title[position]
        holder.productName.text = name[position]
        holder.productImage.setImageResource(image[position])
    }

    override fun getItemCount(): Int {
        return title.size
    }
}