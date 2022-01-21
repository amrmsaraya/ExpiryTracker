package com.github.amrmsaraya.expirytracker.presentation.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.github.amrmsaraya.expirytracker.R
import com.github.amrmsaraya.expirytracker.databinding.FragmentDetailsBinding
import com.github.amrmsaraya.expirytracker.domain.entity.Product
import com.google.android.material.datepicker.CalendarConstraints
import com.google.android.material.datepicker.DateValidatorPointForward
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.util.*


@AndroidEntryPoint
class DetailsFragment : Fragment() {

    private var _binding: FragmentDetailsBinding? = null
    private val binding get() = _binding!!

    private val viewModel: DetailsViewModel by viewModels()

    private var date = 0L
    private var barcode = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailsBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        barcode = arguments?.getString("barcode") ?: ""

        val datePicker = materialDatePicker()

        datePicker.addOnPositiveButtonClickListener {
            date = it
            binding.tfExpiryDate.editText?.setText(
                SimpleDateFormat("y/MM/dd", Locale.getDefault()).format(it)
            )
        }

        binding.btnExpiryDate.setOnClickListener {
            datePicker.show(childFragmentManager, null)
        }

        binding.detailsAppBar.setNavigationOnClickListener {
            MaterialAlertDialogBuilder(requireContext())
                .setTitle(getString(R.string.discard_unsaved_changes))
                .setPositiveButton(getString(R.string.discard)) { _, _ ->
                    findNavController().popBackStack()
                }
                .setNegativeButton(getString(R.string.cancel)) { dialogInterface, _ ->
                    dialogInterface.dismiss()
                }
                .show()
        }

        binding.tfName.editText?.addTextChangedListener {
            when (it.toString().isEmpty()) {
                true -> binding.tfName.error = getString(R.string.error_name_empty)
                false -> binding.tfName.error = ""
            }
        }

        binding.tfExpiryDate.editText?.addTextChangedListener {
            when (it.toString().isEmpty()) {
                true -> binding.tfExpiryDate.error = getString(R.string.error_date_empty)
                false -> binding.tfExpiryDate.error = ""
            }
        }

        binding.detailsAppBar.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.menuSave -> {
                    val name = binding.tfName.editText?.text.toString()
                    val category = binding.tfCategory.editText?.text.toString()
                    val product = Product(
                        barcode = barcode,
                        name = name,
                        category = if (category.isNotEmpty()) category else getString(R.string.other),
                        expiryDate = date
                    )
                    if (validateProduct(product)) {
                        viewModel.insertProduct(product)
                        findNavController().popBackStack()
                    }
                    true
                }
                else -> false
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun materialDatePicker(): MaterialDatePicker<Long> {
        val constraints = CalendarConstraints.Builder()
            .setValidator(DateValidatorPointForward.now())
            .build()

        return MaterialDatePicker.Builder.datePicker()
            .setCalendarConstraints(constraints)
            .setTitleText(getString(R.string.select_expiry_date))
            .build()
    }

    private fun validateProduct(product: Product): Boolean {
        if (product.name.isEmpty()) {
            binding.tfName.error = getString(R.string.error_name_empty)
        }
        if (product.expiryDate == 0L) {
            binding.tfExpiryDate.error = getString(R.string.error_date_empty)
        }
        return barcode.isNotEmpty() &&
                binding.tfName.editText?.text.toString().isNotEmpty() &&
                date != 0L
    }

}