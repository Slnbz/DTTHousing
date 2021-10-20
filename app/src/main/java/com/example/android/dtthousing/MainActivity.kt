package com.example.android.dtthousing

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.inputmethod.EditorInfo
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.android.dtthousing.databinding.ActivityMainBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*


class MainActivity : AppCompatActivity() {


    lateinit var adapt: HouseAdapter
    lateinit var binding: ActivityMainBinding

    private var thesearchlist = mutableListOf<String>()
    private var displaylist = mutableListOf<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater).also {
            setContentView(it.root)
        }

        binding.recyclerviewHouses.layoutManager = LinearLayoutManager(this)
        //binding.recyclerviewHouses.adapter = adapt
        val recyclerViewHouses: RecyclerView = findViewById(R.id.recyclerview_houses)

        findViewById<android.widget.SearchView>(R.id.Search).setOnQueryTextListener(object :
            android.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return true
            }
            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText?.isNotEmpty() == true) {
                    displaylist.clear()
                    val search = newText.lowercase(Locale.getDefault())
                    for (houses in thesearchlist) {
                        if (houses.lowercase(Locale.getDefault()).contains(search)) {
                            displaylist.add(houses)
                        }
                    }
                    recyclerViewHouses.adapter!!.notifyDataSetChanged()
                } else {
                    displaylist.clear()
                    setContentView(R.layout.item_empty_dataset)
                    recyclerViewHouses.adapter!!.notifyDataSetChanged()

                }
                return true
            }
        })



/*        val houze = intent.getSerializableExtra("house") as House

        thesearchlist.add(houze.city)
        thesearchlist.add(houze.zip)

        displaylist.addAll(thesearchlist)*/

        val button = findViewById<ImageButton>(R.id.infobutton)
        button.setOnClickListener{
            val intent = Intent(this, InfoActivity::class.java)
            startActivity(intent)

        }


        HousesApi().getHouses().enqueue(object:Callback<List<House>>{
            override fun onResponse(call: Call<List<House>>, response: Response<List<House>>) {
                val housedetails = response.body()
                housedetails?.let {
                    showHouses(it)
                }
            }

            override fun onFailure(call: Call<List<House>>, t: Throwable) {
                Toast.makeText(applicationContext, t.message, Toast.LENGTH_LONG).show()
            }

        })


    }

  /*  override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.options_menu, menu)

        val recyclerViewHouses: RecyclerView = findViewById(R.id.recyclerview_houses)
        val item = menu?.findItem(R.id.search_action)
        val searchView : SearchView = item?.actionView as SearchView



        searchView.setOnQueryTextListener(object: SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {

                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText!!.isNotEmpty()){
                    displaylist.clear()
                    var search= newText.lowercase(Locale.getDefault())

                    for(houses in thesearchlist){
                        if(houses.lowercase(Locale.getDefault()).contains(search)){
                            displaylist.add(houses)
                        }

                    }
                }else{
                    displaylist.clear()
                    setContentView(R.layout.item_empty_dataset)

                }
                return true
            }
        })
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        return true
    }*/
    private fun showHouses(houses: List<House>){
      //val houze = intent.getSerializableExtra("house") as House
        //val sortedHouses = houses.sortedBy { houze.price }
        val recyclerViewHouses: RecyclerView = findViewById(R.id.recyclerview_houses)
        recyclerViewHouses.layoutManager = LinearLayoutManager(this)
        recyclerViewHouses.adapter = HouseAdapter(houses){ House ->
            val houseDetailsActivityIntent = Intent(this, HouseDetails::class.java)
            houseDetailsActivityIntent.putExtra("house", House)
            startActivity(houseDetailsActivityIntent)
        }
    }






}