package com.myhouse.kotlinsamples

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.myhouse.kotlinsamples.network.*
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import java.util.concurrent.TimeUnit

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
        val serviceClient = ServiceClient()
        val compositeDisposable = CompositeDisposable()
        val observable = serviceClient.getCharacters()

        compositeDisposable.add(
            observable.delay(1, TimeUnit.SECONDS)
                .subscribeOn(Schedulers.io())
                .subscribe({response -> handleResponse(response)}, {failure -> handleError(failure)})
        )
    }

    private fun handleResponse(response: Characters) {
        Log.d(TAG, "Got the list of characters")
        response.results.forEach { result ->
            Log.d(TAG, "\tName ${result.name}, Species ${result.species}, Status ${result.status}")
        }
        runOnUiThread {
            setupCharactersRecyclerView(response.results)
        }
    }

    private fun handleError(failure: Throwable) {
        Log.e(TAG, "Error during the processing of the request, error - ${failure.message}")
    }
}