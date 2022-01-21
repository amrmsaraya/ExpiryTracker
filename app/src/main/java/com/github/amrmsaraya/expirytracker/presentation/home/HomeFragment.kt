package com.github.amrmsaraya.expirytracker.presentation.home

import android.Manifest
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.github.amrmsaraya.expirytracker.R
import com.github.amrmsaraya.expirytracker.databinding.FragmentHomeBinding
import com.github.amrmsaraya.expirytracker.utils.isPermissionGranted
import com.github.amrmsaraya.expirytracker.utils.openSettings

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: ValidAdapter

    private val permissionRequest =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { permissions ->
            when (permissions) {
                true -> findNavController().navigate(R.id.barcodeScannerFragment)
                false -> requireActivity().openSettings(Manifest.permission.CAMERA)
            }
        }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initRecyclerView()
        adapter.submitList(List(10) { "" })

        binding.fabAdd.setOnClickListener {
            when (requireContext().isPermissionGranted(Manifest.permission.CAMERA)) {
                true -> findNavController().navigate(R.id.barcodeScannerFragment)
                false -> permissionRequest.launch(Manifest.permission.CAMERA)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun initRecyclerView() {
        binding.rvValid.layoutManager = LinearLayoutManager(context)
        binding.rvValid.adapter = ValidAdapter { TODO() }
        adapter = binding.rvValid.adapter as ValidAdapter
    }
}