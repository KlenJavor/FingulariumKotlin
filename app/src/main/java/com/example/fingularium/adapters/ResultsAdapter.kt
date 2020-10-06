package com.example.fingularium.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import com.example.fingularium.R
import com.example.fingularium.data.ResultsSingleton
import com.example.fingularium.model.Result
import com.example.fingularium.view.ResultsActivity
import kotlinx.android.synthetic.main.item_results.view.*

/**
 * @Adapter sends data to the particular fields in the recyclerview whenever live data changes (that is observed in the ResultsActivity)
 */

class ResultsAdapter(resultsActivity: ResultsActivity, results: MutableLiveData<MutableList<Result>>) : RecyclerView.Adapter<ResultsAdapter.MyViewHolder>(){

    inner class MyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_results, parent, false))
    }

    override fun getItemCount(): Int {
        return ResultsSingleton.results.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.itemView.question.text = ResultsSingleton.results[position].question
        holder.itemView.answer.text = ResultsSingleton.results[position].answers
        holder.itemView.passes.text = ResultsSingleton.results[position].passes.toString()
        holder.itemView.fails.text = ResultsSingleton.results[position].fails.toString()
    }

    fun setData(newList: MutableList<Result>){
        ResultsSingleton.results = newList
        notifyDataSetChanged()
    }
}