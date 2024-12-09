package com.example.jelajah3.ui.theme.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.jelajah3.R
import com.example.jelajah3.model.RankingItem

class RankingAdapter : ListAdapter<RankingItem, RankingAdapter.RankingViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RankingViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_ranking, parent, false)
        return RankingViewHolder(view)
    }

    override fun onBindViewHolder(holder: RankingViewHolder, position: Int) {
        val item = getItem(position)
        holder.tvRank.text = item.rank.toString()
        holder.tvName.text = item.user
        holder.tvScore.text = item.score.toString()
    }

    class RankingViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvRank: TextView = itemView.findViewById(R.id.tv_rank)
        val tvName: TextView = itemView.findViewById(R.id.tv_name)
        val tvScore: TextView = itemView.findViewById(R.id.tv_score)
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<RankingItem>() {
            override fun areItemsTheSame(oldItem: RankingItem, newItem: RankingItem): Boolean = oldItem.rank == newItem.rank
            override fun areContentsTheSame(oldItem: RankingItem, newItem: RankingItem): Boolean = oldItem == newItem
        }
    }
}
