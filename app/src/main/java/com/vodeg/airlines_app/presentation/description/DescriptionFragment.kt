package com.vodeg.airlines_app.presentation.description

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.vodeg.airlines_app.databinding.FragmentDescriptionBinding
import com.vodeg.airlines_app.presentation.base.BaseFragment
import android.content.Intent
import android.net.Uri
import android.util.Log
import androidx.navigation.fragment.navArgs
import com.vodeg.airlines_app.data.model.Airline


class DescriptionFragment : BaseFragment() {
    private lateinit var _binding: FragmentDescriptionBinding
    private val args: DescriptionFragmentArgs by navArgs()
    private lateinit var websiteLink: String
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDescriptionBinding.inflate(inflater, container, false)
        val airline = args.airline
        initView(airline)
        initListener()

        return _binding.root
    }

    private fun initView(airline: Airline) {
        _binding.apply {
            tvAirlineName.text = airline.name ?: "Name"
            tvAirlineCountry.text = airline.country ?: "Country"
            tvAirlineSlogan.text = airline.slogan ?: ""
            tvHeadquartersValues.text = airline.headQuaters ?: ""
            websiteLink = airline.website.toString()
        }
    }

    private fun initListener() {
        _binding.btVisit.setOnClickListener {
            openWebSite()
        }
        _binding.toolbar.setNavigationOnClickListener {
            findNavController().popBackStack()
        }
    }


    private fun openWebSite() {
        websiteLink =
            if (websiteLink.startsWith("http://") || websiteLink.startsWith("https://")) websiteLink else "https://$websiteLink"
        val intent = Intent(
            Intent.ACTION_VIEW,
            Uri.parse(websiteLink)
        )
        startActivity(intent)
    }

}