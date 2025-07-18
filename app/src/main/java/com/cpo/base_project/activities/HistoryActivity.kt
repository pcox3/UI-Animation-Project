package com.cpo.base_project.activities

import android.os.Bundle
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.TextView
import androidx.activity.ComponentActivity
import androidx.activity.addCallback
import androidx.core.content.ContextCompat
import com.cpo.base_project.R
import com.cpo.base_project.adapters.History
import com.cpo.base_project.adapters.HistoryAdapter
import com.cpo.base_project.data.historyDummy
import com.cpo.base_project.databinding.ActivityHistoryBinding
import com.google.android.material.tabs.TabLayout

class HistoryActivity: ComponentActivity() {

    private val binding by lazy { ActivityHistoryBinding.inflate(layoutInflater) }
    private var historyAdapter: HistoryAdapter? = null
    val tabTitles = arrayOf(Tab("All", 15),
        Tab("Completed", 5),
        Tab("In progress", 3),
        Tab("Pending order", 4),
        Tab("Cancelled", 0))

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        onBackPressedDispatcher.addCallback(this) {
            finishAfterTransition()
        }

        with(binding){

            toolbar.setNavigationOnClickListener {
                onBackPressedDispatcher.onBackPressed()
            }

            tabLayout.animation = AnimationUtils.loadAnimation(
                this@HistoryActivity, R.anim.slide_in_from_right
            )
           historyAdapter = HistoryAdapter(this@HistoryActivity).apply {
                historyRv.adapter = this
            }

            setupTabs()

        }

    }


    private fun tabListener(){
        binding.tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                historyAdapter?.setData(getHistory(tab?.text.toString()))
                tab?.customView.apply {
                    this?.findViewById<TextView>(R.id.badge)
                        ?.backgroundTintList = ContextCompat.getColorStateList(
                        this@HistoryActivity, R.color.colorAccentLight)
                    this?.findViewById<TextView>(R.id.title)
                        ?.setTextColor(resources.getColor(R.color.colorWhite))
                }

            }
            override fun onTabUnselected(tab: TabLayout.Tab?) {
                tab?.customView.apply {
                    this?.findViewById<TextView>(R.id.badge)
                        ?.backgroundTintList = ContextCompat.getColorStateList(
                        this@HistoryActivity, R.color.badgeInActiveColor)
                    this?.findViewById<TextView>(R.id.title)
                        ?.setTextColor(resources.getColor(R.color.tabInActiveColor))
                }
            }
            override fun onTabReselected(tab: TabLayout.Tab?) {

            }
        })
    }
    private fun setupTabs(){
        tabListener()
        addTabBadges(tabTitles)

    }

    private fun addTabBadges(tabTitles: Array<Tab>){
        for (i in tabTitles.indices) {
            val tab = binding.tabLayout.newTab()
            tab.text = tabTitles[i].title
            val customView = layoutInflater.inflate(R.layout.custom_tab_item, null)

            val tabTextView = customView.findViewById<TextView>(R.id.title)
            tabTextView.text = tabTitles[i].title

            val badgeView = customView?.findViewById<TextView>(R.id.badge)
            badgeView?.visibility = if (tabTitles[i].count == 0) View.GONE else View.VISIBLE
            badgeView?.text = tabTitles[i].count.toString()

            tab.customView = customView
            binding.tabLayout.addTab(tab)
        }
    }

    data class Tab(
        val title: String,
        val count: Int
    )


    private fun getHistory(title: String): ArrayList<History>{
        when(title){
            "Completed" ->{
                return historyDummy.filter { history -> history.status == "completed" }
                    .toCollection(ArrayList())
            }
            "In progress" ->{
                return historyDummy.filter { history -> history.status == "in-progress" }
                    .toCollection(ArrayList())
            }
            "Pending order" ->{
                return historyDummy.filter { history -> history.status == "loading" }
                    .toCollection(ArrayList())
            }
            "All" -> {
                return historyDummy
            }
            else -> return historyDummy
        }
    }


}