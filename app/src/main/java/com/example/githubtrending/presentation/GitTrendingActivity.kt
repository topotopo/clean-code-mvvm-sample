package com.example.githubtrending.presentation

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.example.githubtrending.R
import com.example.githubtrending.databinding.ActivityTrendListBinding
import com.example.githubtrending.presentation.common.BaseActivity
import com.example.githubtrending.presentation.details.TrendDetailsFragment
import com.example.githubtrending.presentation.edit.TrendEditFragment
import com.example.githubtrending.presentation.util.GIT_MODEL_URL

class GitTrendingActivity : BaseActivity() {

    private lateinit var binding: ActivityTrendListBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_trend_list)
        setSupportActionBar(binding.toolbar)
        navigation.loadTrendListFragment(supportFragmentManager)
    }

    fun loadTrendDetailsFragment(url: String) {
        val trendingFragment =
            TrendDetailsFragment()
        val bundle = Bundle()
        bundle.putString(GIT_MODEL_URL, url)
        trendingFragment.arguments = bundle
        supportFragmentManager.beginTransaction().replace(R.id.container, trendingFragment).addToBackStack("details").commit()
    }

    fun loadTrendEditFragment(url: String) {
        val trendingFragment =
            TrendEditFragment()
        val bundle = Bundle()
        bundle.putString(GIT_MODEL_URL, url)
        trendingFragment.arguments = bundle
        supportFragmentManager.beginTransaction().replace(R.id.container, trendingFragment).addToBackStack("edit").commit()
    }
}
