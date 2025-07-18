package com.cpo.base_project.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.recyclerview.widget.RecyclerView
import com.cpo.base_project.R
import com.google.android.material.textview.MaterialTextView

class HistoryAdapter(private val context: Context): RecyclerView.Adapter<HistoryAdapter.HistoryViewHolder>() {

    class HistoryViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){}

    private var lastPosition:Int = -1
    private var historyList: ArrayList<History>? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.itemview_history, parent, false)
    return HistoryViewHolder(view)
    }

    override fun getItemCount(): Int {
        return historyList?.size?:0
    }

    fun setData(newHistoryList: ArrayList<History>){
        historyList = newHistoryList
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: HistoryViewHolder, position: Int) {
        val txtAmount = holder.itemView.findViewById<MaterialTextView>(R.id.txt_amount)
        val txtStatus = holder.itemView.findViewById<MaterialTextView>(R.id.txt_status)
        txtAmount.text = historyList?.get(position)?.amount
        txtStatus.text = historyList?.get(position)?.status

        when(historyList?.get(position)?.status){
            "completed" -> {
                txtStatus.setTextColor(holder
                    .itemView.context.getColor(R.color.colorPrimaryLight))
            }
            "loading" -> {
                txtStatus.setTextColor(holder
                    .itemView.context.getColor(R.color.colorLoading))
            }
            "in-progress" -> {
                txtStatus.setTextColor(holder
                    .itemView.context.getColor(R.color.colorInProgress))
            }
        }


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



data class History(
    val amount: String,
    val status: String

)