import android.content.Intent
import android.media.MediaPlayer
import android.os.Bundle
import android.util.Log
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import com.example.a6_1.DetailsActivity
import com.example.a6_1.Item
import com.example.a6_1.R
import com.example.a6_1.Singleton

class RecyclerviewActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recyclerview)

        val recyclerview = findViewById<ListView>(R.id.recyclerView)
        recyclerview.adapter = ArrayAdapter<Item>( //list of items we want to print
                this,  //contect in which activity we are
                //android.R.layout.simple_list_item_1, //define specific layout for one item
                R.layout.item_recyclerview,
                Singleton.instance.presidents)
        recyclerview.onItemClickListener = AdapterView.OnItemClickListener { adapterView, view, i, l ->
            Log.d(TAG, "onItemClick($i)")
            val nextActivity = Intent(this@RecyclerviewActivity, DetailsActivity::class.java)
            nextActivity.putExtra(EXTRA, i)
            startActivity(nextActivity)
        }
    }

    companion object {
        const val TAG = "Recycler"
        const val EXTRA = "com.example.lab06"
    }
}