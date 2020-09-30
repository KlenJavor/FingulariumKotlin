package com.example.a6_1

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.a6_1.Data.Singleton
import kotlinx.android.synthetic.main.activity_recyclerview.*

class RecyclerviewActivity : AppCompatActivity() {
 /*   override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        VocabularyViewModel =
                ViewModelProviders.of(this).get(VocabularyViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_words, container, false)
        val textView: TextView = root.findViewById(R.id.text_home)
        val adapter = WordAdapter()
        binding.wordList.adapter = adapter
        VocabularyViewModel.response.observe(viewLifecycleOwner, Observer {
            adapter.words = it
        })
*/
 override fun onCreate(savedInstanceState: Bundle?) {
      super.onCreate(savedInstanceState)
      setContentView(R.layout.activity_recyclerview)

      val listview = findViewById<RecyclerView>(R.id.recyclerView)
      recyclerView.layoutManager = LinearLayoutManager(this)
      listview.adapter = MyRecyclerViewAdapter (
              this, Singleton.instance.presidents)


     // listview.onItemClickListener = AdapterView.OnItemClickListener { adapterView, view, i, l ->
       //   Log.d(TAG, "onItemClick($i)")
        //  val nextActivity = Intent(this@RecyclerviewActivity, DetailsActivity::class.java)
         // nextActivity.putExtra(EXTRA, i)
        //  startActivity(nextActivity)
     // }
  }


  companion object {
      const val TAG = "Recycler"
      const val EXTRA = "com.example.lab06"
  }
}



class MyRecyclerViewAdapter(private val context: Context, private val mydata: List<Item>):
RecyclerView.Adapter<MyViewHolder>() {
  override fun onCreateViewHolder(vg: ViewGroup, vt: Int): MyViewHolder {
      Log.d("ZZZ", "onCreateViewHolder()")
      // inflate creates layout including the widget objects in the layout
      val itemView = LayoutInflater.from(context).inflate(R.layout.item_recyclerview, vg, false)
      return MyViewHolder(itemView)
  }

  override fun getItemCount() = mydata.size

  // populate the view, in this case one-to-one mapping between list elements and recyclerView positions
  override fun onBindViewHolder(vh: MyViewHolder, pos: Int) {
      Log.d("ZZZ", "onBindViewHolder($pos)")
      vh.itemView.findViewById<TextView>(R.id.textView2).text = mydata[ pos ].toString()
  }
}

// type for the views
class MyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)