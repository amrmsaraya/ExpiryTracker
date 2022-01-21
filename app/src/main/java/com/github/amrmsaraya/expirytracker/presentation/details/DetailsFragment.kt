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

        val datePicker = MaterialDatePicker.Builder.datePicker()
            .setCalendarConstraints(
                CalendarConstraints.Builder()
                    .setValidator(DateValidatorPointForward.now())
                    .build()
            )
            .setTitleText("Select expiration date")
            .build()


        datePicker.addOnPositiveButtonClickListener {
            date = it
            println(SimpleDateFormat("y/MM/dd", Locale.getDefault()).format(it))
            binding.tfExpirationDate.editText?.setText(
                SimpleDateFormat("y/MM/dd", Locale.getDefault()).format(it)
            )
        }

        binding.btnDate.setOnClickListener {
            datePicker.show(childFragmentManager, "MATERIAL_DATE_PICKER")
        }

        binding.detailsAppBar.setNavigationOnClickListener {
            findNavController().popBackStack()
        }

        binding.detailsAppBar.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.menuSave -> {
                    true
                }
                else -> false
            }
        }
    }


}