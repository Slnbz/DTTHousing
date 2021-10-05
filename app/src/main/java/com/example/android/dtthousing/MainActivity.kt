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


class MainActivity : AppCompatActivity() {




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)



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

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.options_menu, menu)

        val item = menu?.findItem(R.id.search_action)
        val searchView : SearchView = item?.actionView as SearchView

        searchView.imeOptions = EditorInfo.IME_ACTION_DONE

        searchView.setOnQueryTextListener(object: SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return true
            }
        })
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        return true
    }
    private fun showHouses(houses: List<House>){
        val recyclerViewHouses: RecyclerView = findViewById(R.id.recyclerview_houses)
        recyclerViewHouses.layoutManager = LinearLayoutManager(this)
        recyclerViewHouses.adapter = HouseAdapter(houses){ House ->
            val houseDetailsActivityIntent = Intent(this, HouseDetails::class.java)
            houseDetailsActivityIntent.putExtra("house", House)
            startActivity(houseDetailsActivityIntent)
        }
    }






}