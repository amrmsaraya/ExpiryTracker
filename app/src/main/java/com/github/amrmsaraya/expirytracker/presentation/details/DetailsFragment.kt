package com.github.amrmsaraya.expirytracker.presentation.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.github.amrmsaraya.expirytracker.R
import com.github.amrmsaraya.expirytracker.databinding.FragmentDetailsBinding
import com.github.amrmsaraya.expirytracker.domain.entity.Product
import com.github.amrmsaraya.expirytracker.utils.formatDate
import com.google.android.material.datepicker.CalendarConstraints
import com.google.android.material.datepicker.DateValidatorPointForward
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import dagger.hilt.android.AndroidEntryPoint


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

        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
            showBackConfirmationDialog {
                findNavController().popBackStack()
            }
        }

        val datePicker = materialDatePicker()

        datePicker.addOnPositiveButtonClickListener {
            date = it
            binding.tfExpiryDate.editText?.setText(it.formatDate())
        }

        binding.btnExpiryDate.setOnClickListener {
            datePicker.show(childFragmentManager, null)
        }

        binding.detailsAppBar.setNavigationOnClickListener {
            showBackConfirmationDialog {
                findNavController().popBackStack()
            }
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

    private fun showBackConfirmationDialog(onConfirm: () -> Unit) {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle(getString(R.string.discard_unsaved_changes))
            .setPositiveButton(getString(R.string.discard)) { _, _ ->
                onConfirm()
            }
            .setNegativeButton(getString(R.string.cancel)) { dialogInterface, _ ->
                dialogInterface.dismiss()
            }
            .show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun materialDatePicker(): MaterialDatePicker<Long> {
        return MaterialDatePicker.Builder.datePicker()
            .setCalendarConstraints(
                CalendarConstraints.Builder()
                    .setValidator(
                        DateValidatorPointForward.from(
                            System.currentTimeMillis() - (2 * 60 * 60 * 1000)
                        )
                    )
                    .build()
            )
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