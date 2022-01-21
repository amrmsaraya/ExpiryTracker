package com.github.amrmsaraya.expirytracker.presentation.barcode_scanner

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.github.amrmsaraya.expirytracker.R
import com.github.amrmsaraya.expirytracker.databinding.FragmentBarcodeScannerBinding
import com.google.zxing.client.android.BeepManager

class BarcodeScannerFragment : Fragment() {

    private var _binding: FragmentBarcodeScannerBinding? = null
    private val binding get() = _binding!!

    private lateinit var beepManager: BeepManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        beepManager = BeepManager(requireActivity())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentBarcodeScannerBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var flash = false

        binding.btnBack.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.fabFlash.setOnClickListener {
            flash = !flash
            when (flash) {
                true -> {
                    binding.fabFlash.setImageResource(R.drawable.ic_baseline_flash_off_24)
                    binding.barcodeScanner.setTorchOn()
                }
                false -> {
                    binding.fabFlash.setImageResource(R.drawable.ic_baseline_flash_on_24)
                    binding.barcodeScanner.setTorchOff()
                }
            }
        }

        binding.barcodeScanner.decodeContinuous { result ->
            val x = result.text
            beepManager.playBeepSound()
            findNavController().navigate(R.id.detailsFragment)
        }
    }

    override fun onResume() {
        super.onResume()
        binding.barcodeScanner.resume()
    }

    override fun onPause() {
        super.onPause()
        binding.barcodeScanner.pause()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}