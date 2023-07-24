package com.example.appsessions

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class RulesAdapter(private val rulesList: List<Rule>) : RecyclerView.Adapter<RulesAdapter.RuleViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RuleViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_rule, parent, false)
        return RuleViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: RuleViewHolder, position: Int) {
        val rule = rulesList[position]
        holder.textDescription.text = rule.description
        holder.iconNumber.setImageResource(rule.iconResourceId)
        holder.iconNormalRule.setImageResource(rule.iconNumberResourceId)
    }

    override fun getItemCount(): Int {
        return rulesList.size
    }

    class RuleViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val iconNumber: ImageView = itemView.findViewById(R.id.icon_number)
        val iconNormalRule: ImageView = itemView.findViewById(R.id.icon_normal_rule)
        val textDescription: TextView = itemView.findViewById(R.id.text_description)
    }
}

