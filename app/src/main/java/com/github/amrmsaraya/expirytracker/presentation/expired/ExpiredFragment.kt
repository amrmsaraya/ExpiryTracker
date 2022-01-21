package com.github.amrmsaraya.expirytracker.presentation.expired

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.github.amrmsaraya.expirytracker.databinding.FragmentExpiredBinding
import com.github.amrmsaraya.expirytracker.utils.collectFlowSafely
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ExpiredFragment : Fragment() {

    private var _binding: FragmentExpiredBinding? = null
    private val binding get() = _binding!!

    private val viewModel: ExpiredViewModel by viewModels()
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

        viewLifecycleOwner.collectFlowSafely(viewModel.uiState) {
            adapter.submitList(it.products)
            when (it.products.isEmpty()) {
                true -> binding.imgEmptyExpired.emptyListLayout.visibility = View.VISIBLE
                false -> binding.imgEmptyExpired.emptyListLayout.visibility = View.GONE
            }
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun initRecyclerView() {
        binding.rvExpired.layoutManager = LinearLayoutManager(context)
        binding.rvExpired.adapter = ExpiredAdapter()
        adapter = binding.rvExpired.adapter as ExpiredAdapter
    }

}