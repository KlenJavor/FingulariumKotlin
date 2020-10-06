package com.example.fingularium.view

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.fingularium.R
import com.example.fingularium.adapters.ResultsAdapter
import com.example.fingularium.data.ResultsSingleton.results
import kotlinx.android.synthetic.main.activity_vocabulary.*

class ResultsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_results)
        val resultsview = findViewById<ListView>(R.id.itemResultsView)
        resultsview.adapter = ArrayAdapter(this, R.layout.item_alternative, results)

    }
}