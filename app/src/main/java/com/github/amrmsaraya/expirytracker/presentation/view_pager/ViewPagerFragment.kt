package com.github.amrmsaraya.expirytracker.presentation.view_pager

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.github.amrmsaraya.expirytracker.databinding.FragmentViewPagerBinding
import com.github.amrmsaraya.expirytracker.presentation.expired.ExpiredFragment
import com.github.amrmsaraya.expirytracker.presentation.home.HomeFragment

class ViewPagerFragment : Fragment() {

    private var _binding: FragmentViewPagerBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentViewPagerBinding.inflate(layoutInflater, container, false)

        val fragmentList = listOf(
            HomeFragment(),
            ExpiredFragment(),
        )

        val adapter = ViewPagerAdapter(
            fragmentList,
            childFragmentManager,
            viewLifecycleOwner.lifecycle
        )

        binding.viewPager.adapter = adapter

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}