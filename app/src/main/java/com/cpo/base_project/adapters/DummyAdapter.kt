package com.cpo.base_project.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.compose.animation.core.animateDpAsState
import androidx.recyclerview.widget.RecyclerView
import com.cpo.base_project.R

class DummyAdapter(private val layout: Int): RecyclerView.Adapter<DummyAdapter.SearchResultViewHolder>() {
    class SearchResultViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)

    private var lastPosition = -1

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchResultViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(layout, parent, false)
        return SearchResultViewHolder(view)
    }

    override fun getItemCount(): Int {
        return 25
    }

    override fun onBindViewHolder(holder: SearchResultViewHolder, position: Int) {

        if (holder.adapterPosition > lastPosition) {
            holder.itemView.animation = AnimationUtils.loadAnimation(
                holder.itemView.context, R.anim.slide_up
            )
            lastPosition = holder.adapterPosition
        } else {
            holder.itemView.animation = null // Remove animation for recycled views
        }

        lastPosition = holder.adapterPosition
    }



}