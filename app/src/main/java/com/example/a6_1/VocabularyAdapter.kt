package com.example.a6_1

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

import kotlinx.android.synthetic.main.word_item_view.view.*

class WordItemViewHolder(val view: View): RecyclerView.ViewHolder(view)

class VocabularyAdapter: RecyclerView.Adapter<WordItemViewHolder>() {
    var words = listOf<Word>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }
    override fun getItemCount() = words.size

    override fun onBindViewHolder(holder: WordItemViewHolder, position: Int) {
        var word = words[position]
        holder.view.wordText.text = word.text
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WordItemViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.word_item_view, parent, false)
        return WordItemViewHolder(view)
    }
}