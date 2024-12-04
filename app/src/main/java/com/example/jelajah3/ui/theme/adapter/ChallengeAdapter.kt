package com.example.jelajah3.ui.challenge

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.jelajah3.R
import com.example.jelajah3.model.ChallengeItem

class ChallengeAdapter(private val challengeList: List<ChallengeItem>) :
    RecyclerView.Adapter<ChallengeAdapter.ChallengeViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChallengeViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_challenge, parent, false)
        return ChallengeViewHolder(view)
    }

    override fun onBindViewHolder(holder: ChallengeViewHolder, position: Int) {
        val item = challengeList[position]
        holder.tvNumber.text = (position + 1).toString()
        holder.tvTitle.text = item.title
        holder.tvProgress.text = "${item.progress} Tap"
        holder.tvPoints.text = item.points.toString()
        holder.tvStatus.text = if (item.completed) "Selesai" else "Belum"
    }

    override fun getItemCount(): Int = challengeList.size

    class ChallengeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvNumber: TextView = itemView.findViewById(R.id.tv_number)
        val tvTitle: TextView = itemView.findViewById(R.id.tv_title)
        val tvProgress: TextView = itemView.findViewById(R.id.tv_progress)
        val tvPoints: TextView = itemView.findViewById(R.id.tv_points)
        val tvStatus: TextView = itemView.findViewById(R.id.tv_status)
    }
}
