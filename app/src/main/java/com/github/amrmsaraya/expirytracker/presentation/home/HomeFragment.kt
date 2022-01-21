package com.github.amrmsaraya.expirytracker.presentation.home

import android.Manifest
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.github.amrmsaraya.expirytracker.R
import com.github.amrmsaraya.expirytracker.databinding.FragmentHomeBinding
import com.github.amrmsaraya.expirytracker.utils.isPermissionGranted
import com.github.amrmsaraya.expirytracker.utils.openSettings
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import java.text.SimpleDateFormat
import java.util.*

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
        binding.rvValid.adapter = ValidAdapter {
            mockExpiryDate { expiryDate ->
                Toast.makeText(
                    context,
                    SimpleDateFormat("y/MM/dd h:mm a", Locale.getDefault()).format(expiryDate),
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
        adapter = binding.rvValid.adapter as ValidAdapter
    }

    private fun mockExpiryDate(onMockExpiryDate: (Long) -> Unit) {
        val items = listOf(6, 12, 18, 24)

        MaterialAlertDialogBuilder(requireContext())
            .setTitle("Mock Expiry Date")
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