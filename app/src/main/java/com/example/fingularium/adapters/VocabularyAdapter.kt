package com.example.fingularium.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.fingularium.R
import com.example.fingularium.model.Word
import kotlinx.android.synthetic.main.item_vocabulary.view.*
/**
 * @Adapter sends data to the particular fields in the recyclerview whenever live data changes (that is observed in the VocabularyActivity)
 */

class VocabularyAdapter: RecyclerView.Adapter<VocabularyAdapter.MyViewHolder>(){

    private var myList = emptyList<List<Word>>()

    inner class MyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_vocabulary, parent, false))
    }

    override fun getItemCount(): Int {
        return myList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.itemView.english_txt.text = myList[position][0].text
        holder.itemView.finnish_txt.text = myList[position][1].text
    }

    fun setData(newList: List<List<Word>>){
        myList = newList
        notifyDataSetChanged()
    }
}