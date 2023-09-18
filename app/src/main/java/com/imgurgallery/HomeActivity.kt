package com.imgurgallery

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.activity.ComponentActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.imgurgallery.databinding.HomeActivityBinding
import com.imgurgallery.network.APIFactory

class HomeActivity : ComponentActivity() {

    private val galleryViewModel: GalleryViewModel = GalleryViewModel(APIFactory.restApi)
    private val adapter: GalleryAdapter = GalleryAdapter()
    private lateinit var bindView: HomeActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindView = HomeActivityBinding.inflate(layoutInflater)
        setContentView(bindView.root)

        galleryViewModel.galleryList.observe(this) {
            bindView.galleryList.visibility = View.VISIBLE
            bindView.loader.visibility = View.GONE
            adapter.loadImages(it)
        }

        initGalleryAdapter()
        galleryViewModel.fetchGallery()
    }

    /**
     * Initialise the adapter for the recycler view.
     */
    private fun initGalleryAdapter() {
        bindView.galleryList.layoutManager = GridLayoutManager(this, 1)
        bindView.galleryList.adapter = adapter
    }

    /**
     * Change the UI of the recycler view between list and grid.
     */
    private fun toggleRecyclerUI(item: MenuItem) {
        val recyclerViewLayoutManager =
            bindView.galleryList.layoutManager as GridLayoutManager
        if (recyclerViewLayoutManager.spanCount == GalleryAdapter.listSize) {
            recyclerViewLayoutManager.spanCount = GalleryAdapter.gridSize
            item.title = getString(R.string.grid_menu_item)
        } else {
            recyclerViewLayoutManager.spanCount = GalleryAdapter.listSize
            item.title = getString(R.string.list_menu_item)
        }
        adapter.notifyItemRangeChanged(0, adapter.itemCount)
    }

    /**
     * Inflate the menu item in toolbar.
     */
    override fun onPrepareOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.home_menu, menu)
        return super.onPrepareOptionsMenu(menu)
    }

    /**
     * Toggle the UI between list view and grid view when menu item is tapped.
     */
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.list_menu) toggleRecyclerUI(item)
        return super.onOptionsItemSelected(item)
    }
}