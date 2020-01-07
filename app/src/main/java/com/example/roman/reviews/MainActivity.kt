package com.example.roman.reviews

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.SearchView
import android.util.Log
import android.view.Menu
import com.example.roman.reviews.ui.fragments.RevListFragment

class MainActivity : AppCompatActivity(), SearchView.OnQueryTextListener {

    companion object {
        var F_LIST_TAG = "list_fragment"
        const val TAG = "myLogs"
    }

    private lateinit var revListFragment: RevListFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Log.d(TAG, "--- mainActivity onCreate")
        if (savedInstanceState == null) {
            revListFragment = RevListFragment()
            val transaction = supportFragmentManager.beginTransaction()
            transaction.replace(R.id.frame_list, revListFragment, F_LIST_TAG)
            transaction.addToBackStack(null)
            transaction.commit()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.toolbar_menu, menu)
        val searchView = (menu.findItem(R.id.action_search).actionView as SearchView)
        searchView.setOnQueryTextListener(this)
        return true
    }

    override fun onQueryTextSubmit(searchQuery: String?): Boolean {
        searchQuery?.let { revListFragment.loadItems(it) }
        return true
    }

    override fun onQueryTextChange(p0: String?): Boolean {
        return false
    }

}
