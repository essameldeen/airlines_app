package com.vodeg.airlines_app.presentation.description

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.vodeg.airlines_app.databinding.FragmentDescriptionBinding
import com.vodeg.airlines_app.databinding.FragmentHomeBinding
import com.vodeg.airlines_app.presentation.base.BaseFragment
import android.content.Intent
import android.net.Uri


class DescriptionFragment : BaseFragment() {
    private lateinit var _binding: FragmentDescriptionBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDescriptionBinding.inflate(inflater, container, false)

        initView()
        initListener()

        return _binding.root
    }

    private fun initView() {

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
        val intent = Intent(
            Intent.ACTION_VIEW,
            Uri.parse("http://www.google.com")
        )
        startActivity(intent)
    }

}