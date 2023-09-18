package com.imgurgallery

import android.os.Bundle
import android.view.Menu
import android.view.View
import androidx.activity.ComponentActivity
import androidx.recyclerview.widget.LinearLayoutManager
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

    private fun initGalleryAdapter() {
        bindView.galleryList.layoutManager = LinearLayoutManager(this)
        bindView.galleryList.adapter = adapter
    }

    override fun onPrepareOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.home_menu, menu)
        return super.onPrepareOptionsMenu(menu)
    }
}