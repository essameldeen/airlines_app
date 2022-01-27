package com.vodeg.airlines_app.presentation.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.fragment.app.FragmentManager
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.vodeg.airlines_app.data.model.Airline
import com.vodeg.airlines_app.databinding.DialogBottomSheetBinding

class NewAirlineBottomSheet(supportFragmentManager: FragmentManager) :
    BottomSheetDialogFragment() {
    private lateinit var listner: BottomDialogListener
    private lateinit var binding: DialogBottomSheetBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DialogBottomSheetBinding.inflate(inflater)
        dialog!!.window!!.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        binding.btConfirm.setOnClickListener {
            listner.addNewAirline(
                Airline(
                    name = binding.etName.text.toString(),
                    country = binding.etCountry.text.toString(),
                    slogan = binding.etSlogan.text.toString(),
                    headQuaters = binding.etHeadquarter.text.toString(),
                    established = "",
                    website = "",
                    logo = "",
                )
            )
            this.dismiss()
        }
        binding.btCancel.setOnClickListener {
              this.dismiss()
        }

    }

    init {
        this.show(supportFragmentManager, null)
    }


    fun setClickListener(listener: BottomDialogListener) {
        listner = listener
    }

    interface BottomDialogListener {
        fun addNewAirline(airline: Airline)
    }
}