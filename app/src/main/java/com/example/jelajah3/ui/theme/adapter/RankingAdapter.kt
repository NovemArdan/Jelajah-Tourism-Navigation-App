package com.example.jelajah3.ui.theme.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.jelajah3.R
import com.example.jelajah3.model.RankingItem


class RankingAdapter(private val rankingList: List<RankingItem>) :
    RecyclerView.Adapter<RankingAdapter.RankingViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RankingViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_ranking, parent, false)
        return RankingViewHolder(view)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: RankingViewHolder, position: Int) {
        val item = rankingList[position]
        holder.tvRank.text = (position + 1).toString()
        holder.tvName.text = item.user
        holder.tvScore.text = item.score.toString()
    }

    override fun getItemCount(): Int = rankingList.size

    class RankingViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvRank: TextView = itemView.findViewById(R.id.tv_rank)
        val tvName: TextView = itemView.findViewById(R.id.tv_name)
        val tvScore: TextView = itemView.findViewById(R.id.tv_score)
    }
}
