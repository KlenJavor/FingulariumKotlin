package com.example.fingularium.Activities

import android.content.Intent
import android.media.MediaPlayer
import android.os.Bundle
import android.util.Log
import android.widget.AdapterView.OnItemClickListener
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import com.example.fingularium.Item
import com.example.fingularium.R
import com.example.fingularium.Data.Singleton

class MainActivity : AppCompatActivity() {
    var mediaPlayer: MediaPlayer? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val listview = findViewById<ListView>(R.id.itemListView)
        listview.adapter = ArrayAdapter<Item>( //list of items we want to print
                this,  //contect in which activity we are
                //android.R.layout.simple_list_item_1, //define specific layout for one item
                R.layout.item_listview,
                Singleton.instance.presidents)

        listview.onItemClickListener = OnItemClickListener { adapterView, view, i, l ->
            Log.d(TAG, "onItemClick($i)")
            val nextActivity = Intent(this@MainActivity, DetailsActivity::class.java)
            nextActivity.putExtra(EXTRA, i)
            startActivity(nextActivity)
        }
    }

    companion object {
        const val TAG = "DBG"
        const val EXTRA = "com.example.lab06"
    }
}