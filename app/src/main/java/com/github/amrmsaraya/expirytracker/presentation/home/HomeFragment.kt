package com.github.amrmsaraya.expirytracker.presentation.home

import android.Manifest
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.github.amrmsaraya.expirytracker.R
import com.github.amrmsaraya.expirytracker.databinding.FragmentHomeBinding
import com.github.amrmsaraya.expirytracker.utils.collectFlowSafely
import com.github.amrmsaraya.expirytracker.utils.isPermissionGranted
import com.github.amrmsaraya.expirytracker.utils.openSettings
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: ValidAdapter

    private val viewModel: HomeViewModel by viewModels()

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

        viewLifecycleOwner.collectFlowSafely(viewModel.uiState) {
            adapter.submitList(it.products)
            when (it.products.isEmpty()) {
                true -> binding.imgEmpty.emptyListLayout.visibility = View.VISIBLE
                false -> binding.imgEmpty.emptyListLayout.visibility = View.GONE
            }
        }

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
        binding.rvValid.adapter = ValidAdapter { product ->
            mockExpiryDate { expiryDate ->
                viewModel.sendIntent(HomeIntent.InsertProduct(product.copy(expiryDate = expiryDate)))
            }
        }
        adapter = binding.rvValid.adapter as ValidAdapter
    }

    private fun mockExpiryDate(onMockExpiryDate: (Long) -> Unit) {
        val items = listOf(6, 12, 18, 24)

        MaterialAlertDialogBuilder(requireContext())
            .setTitle(getString(R.string.mock_expiry_date))
            .setSingleChoiceItems(
                items.map { "$it hours" }.toTypedArray(),
                0
            ) { dialog, i ->
                val mockedExpiryDate = System.currentTimeMillis() + (items[i] * 60 * 60 * 1000)
                onMockExpiryDate(mockedExpiryDate)
                dialog.dismiss()
            }
            .show()
    }
}