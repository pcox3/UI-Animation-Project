package com.cpo.base_project.activities

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.ComponentActivity
import androidx.core.app.ActivityOptionsCompat
import com.cpo.base_project.R
import com.cpo.base_project.adapters.DummyAdapter
import com.cpo.base_project.databinding.ActivityMainBinding


class MainActivity : ComponentActivity() {

    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)


        binding.apply {


            DummyAdapter(R.layout.itemview_vehicles).apply {
                categoriesRv.adapter = this
            }

            searchBar.setOnClickListener {
                launch(SearchActivity(), searchBar, "search_bar")
            }


            bottomNavigation.setOnItemSelectedListener { item ->
                when (item.itemId) {
                    R.id.nav_calculate -> {
                        launch(CalculateActivity(), toolbar)
                        true
                    }
                    R.id.nav_history -> {
                        launch(HistoryActivity(), toolbar)
                        true
                    }
                    else -> {
                        false
                    }
                }
            }



        }

    }



    private fun launch(activity: Activity, view: View, transitionName: String? = null) {
        if (transitionName != null) {
            Intent(this@MainActivity, activity::class.java).apply {
                val options = ActivityOptionsCompat.makeSceneTransitionAnimation(
                    this@MainActivity, view, transitionName
                )
                startActivity(this, options.toBundle())
            }
        }else{
            startActivity(Intent(this@MainActivity, activity::class.java))
        }
    }


}
