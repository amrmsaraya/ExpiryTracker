package com.github.amrmsaraya.expirytracker.presentation.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.github.amrmsaraya.expirytracker.R
import com.github.amrmsaraya.expirytracker.databinding.FragmentDetailsBinding
import com.google.android.material.datepicker.CalendarConstraints
import com.google.android.material.datepicker.DateValidatorPointForward
import com.google.android.material.datepicker.MaterialDatePicker
import java.text.SimpleDateFormat
import java.util.*


class DetailsFragment : Fragment() {

    private var _binding: FragmentDetailsBinding? = null
    private val binding get() = _binding!!
    private var date = 0L

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailsBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val datePicker = materialDatePicker()

        datePicker.addOnPositiveButtonClickListener {
            date = it
            binding.tfExpiryDate.editText?.setText(
                SimpleDateFormat("y/MM/dd hh:mm:ss", Locale.getDefault()).format(it)
            )
        }

        binding.btnExpiryDate.setOnClickListener {
            datePicker.show(childFragmentManager, "MATERIAL_DATE_PICKER")
        }

        binding.detailsAppBar.setNavigationOnClickListener {
            findNavController().popBackStack()
        }

        binding.detailsAppBar.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.menuSave -> {
                    val name = binding.tfName.editText?.text.toString()
                    val category = binding.tfCategory.editText?.text.toString()
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

}