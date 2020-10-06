package com.example.fingularium.controllers

/**
 * Created by Patricie Suppala, 1910042 in 2020.
 */

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.fingularium.R
import com.example.fingularium.adapters.ResultsAdapter

class ResultsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_results)
        val resultsview = findViewById<RecyclerView>(R.id.itemResultsView)
        resultsview.adapter = ResultsAdapter()
        resultsview.layoutManager = LinearLayoutManager(this)
    }
}