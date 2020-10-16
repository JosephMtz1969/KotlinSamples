package com.myhouse.kotlinsamples

import android.os.Bundle
import android.util.Log
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
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

        findViewById<FloatingActionButton>(R.id.fab).setOnClickListener { view ->
            Snackbar.make(view, "Lets go get those characters", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
            goGetTheCharacters()
        }
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
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun goGetTheCharacters() {
        val request = ServiceBuilder.buildService(RickMortyCharacterEndpoints::class.java)

        val call = request.getCharacters("my_key")

        call.enqueue(object : Callback<Characters>{
            override fun onResponse(call: Call<Characters>, response: Response<Characters>) {
                if (response.isSuccessful) {
                    response.body()?.let{ characters ->
                        characters.results.forEach { result ->
                            Log.d(TAG, "\tName ${result.name}, Species ${result.species}, Status ${result.status}")
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