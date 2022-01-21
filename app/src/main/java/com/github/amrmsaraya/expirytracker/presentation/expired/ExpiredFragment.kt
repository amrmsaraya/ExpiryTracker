package com.github.amrmsaraya.expirytracker.presentation.expired

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.github.amrmsaraya.expirytracker.databinding.FragmentExpiredBinding

class ExpiredFragment : Fragment() {

    private var _binding: FragmentExpiredBinding? = null
    private val binding get() = _binding!!

    private lateinit var adapter: ExpiredAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentExpiredBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initRecyclerView()
        adapter.submitList(List(10) { "" })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun initRecyclerView() {
        binding.rvExpired.layoutManager = LinearLayoutManager(context)
        binding.rvExpired.adapter = ExpiredAdapter { TODO() }
        adapter = binding.rvExpired.adapter as ExpiredAdapter
    }

}