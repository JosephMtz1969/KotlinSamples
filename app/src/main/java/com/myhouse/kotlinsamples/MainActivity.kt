package com.myhouse.kotlinsamples

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.myhouse.kotlinsamples.network.CharacterResult
import com.myhouse.kotlinsamples.network.Characters
import com.myhouse.kotlinsamples.network.RickMortyCharacterEndpoints
import com.myhouse.kotlinsamples.network.ServiceBuilder
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    private val TAG = MainActivity::class.simpleName

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(findViewById(R.id.toolbar))

        goGetTheCharacters()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.fetch_the_characters -> {
                goGetTheCharacters()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun setupCharactersRecyclerView(results: List<CharacterResult>) {
        val layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        val charactersRecyclerView = findViewById<RecyclerView>(R.id.characters_recycler_view)
        charactersRecyclerView.addItemDecoration(DividerItemDecoration(this, layoutManager.orientation))
        val charactersAdapter = CharactersAdapter(results, this)
        charactersRecyclerView.layoutManager = layoutManager
        charactersRecyclerView.adapter = charactersAdapter
    }

    private fun goGetTheCharacters() {
        val request = ServiceBuilder.buildService(RickMortyCharacterEndpoints::class.java)

        val call = request.getCharacters()

        call.enqueue(object : Callback<Characters>{
            override fun onResponse(call: Call<Characters>, response: Response<Characters>) {
                if (response.isSuccessful) {
                    response.body()?.let{ characters ->
                        characters.results.forEach { result ->
                            Log.d(TAG, "\tName ${result.name}, Species ${result.species}, Status ${result.status}")
                            setupCharactersRecyclerView(characters.results)
                        }
                    } ?: kotlin.run {
                        Log.e(TAG, "goGetTheCharacters: we got an empty response")
                    }
                } else {
                   Log.e(TAG, "goGetTheCharacters: call was not successful")
                }
            }

            override fun onFailure(call: Call<Characters>, t: Throwable) {
                Log.e(TAG, "goGetTheCharacters: we got a failures, ${t.message}")
            }
        })
    }
}