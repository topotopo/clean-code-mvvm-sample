package com.example.githubtrending.presentation.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.example.githubtrending.R
import com.example.githubtrending.databinding.FragmentTrendDetailsBinding
import com.example.githubtrending.presentation.common.BaseFragment
import com.example.githubtrending.presentation.util.GIT_MODEL_URL
import com.example.githubtrending.presentation.util.PageState
import com.example.githubtrending.presentation.util.extensions.addDrawableStartWithCustomSize
import com.example.githubtrending.presentation.util.extensions.convertDpToInt
import com.example.githubtrending.presentation.util.extensions.loadCircularAvatar
import com.example.githubtrending.presentation.util.extensions.setLanguageIcon

class TrendDetailsFragment : BaseFragment() {

    private val detailsViewModel: TrendDetailsViewModel by viewModels()
    lateinit var binding: FragmentTrendDetailsBinding
    lateinit var url: String

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_trend_details,
            container,
            false
        )
        initializeView()
        initializeObservers()
        url = arguments?.getString(GIT_MODEL_URL) ?: ""
        detailsViewModel.getTrendingDetails(url)

        return binding.root
    }

    private fun initializeView() {
        binding.edit.setOnClickListener {
            navigation.loadTrendEditFragment(url, parentFragmentManager)
        }
    }

    private fun initializeObservers() {
        detailsViewModel.trendingModel.observe(viewLifecycleOwner, Observer { gitTrendingModel ->
            binding.data = gitTrendingModel
            gitTrendingModel.languageColor?.let {
                binding.language.setLanguageIcon(it, 4f.convertDpToInt(context))
            }
            context?.let { context ->
                val star = ContextCompat.getDrawable(context, R.drawable.ic_star)
                val copy = ContextCompat.getDrawable(context, R.drawable.ic_copy)
                binding.stars.addDrawableStartWithCustomSize(
                    star, 4f.convertDpToInt(context),
                    16f.convertDpToInt(context)
                )
                binding.forks.addDrawableStartWithCustomSize(
                    copy, 4f.convertDpToInt(context),
                    16f.convertDpToInt(context)
                )
                gitTrendingModel.avatar?.let {
                    loadAvatar(it)
                }
            }
        })

        detailsViewModel.pageState.observe(viewLifecycleOwner, Observer { pageState ->
            when (pageState) {
                is PageState.Success -> {
                    binding.loading.visibility = View.GONE
                }
                is PageState.Error -> {
                    binding.loading.visibility = View.GONE
                    showErrorDialog()
                }
                is PageState.Loading -> {
                    binding.loading.visibility = View.VISIBLE
                }
            }
        })
    }

    private fun loadAvatar(url: String) {
        binding.avatar.loadCircularAvatar(url)
    }
}