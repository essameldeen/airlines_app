package com.vodeg.airlines_app.presentation.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.afollestad.materialdialogs.LayoutMode
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.bottomsheets.BottomSheet
import com.vodeg.airlines_app.data.model.Airline
import com.vodeg.airlines_app.databinding.FragmentHomeBinding
import com.vodeg.airlines_app.presentation.base.BaseFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeFragment : BaseFragment() {
    private lateinit var _binding: FragmentHomeBinding
    private lateinit var airlinesAdapter: AirlinesAdapter
    private val homeViewModel: HomeViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        initView()
        initListener()
        return _binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initObservers()
    }

    private fun initView() {
        _binding.rvAirlinesList
        airlinesAdapter = AirlinesAdapter()
        _binding.rvAirlinesList.apply {
            adapter = airlinesAdapter
            layoutManager = LinearLayoutManager(activity)
        }
    }

    private fun initListener() {
        airlinesAdapter.setOnItemClickListener {
            openDetailsFragment(it)
        }
        _binding.fbAdd.setOnClickListener {
            addNewAirline()
        }
    }


    private fun initObservers() {
        homeViewModel.allAirlines.observe(viewLifecycleOwner, {
            setData(it)
        })
        homeViewModel.showErrorMessage.observe(viewLifecycleOwner, {
            showError(it)

        })
        homeViewModel.showProgress.observe(viewLifecycleOwner, {
            if (it) showLoading()
            else dismissLoading()
        })
    }

    private fun setData(data: MutableList<Airline>?) {
        if (data?.isEmpty() == true) {
            showError("No DAtA !!")
        } else {
            airlinesAdapter.differ.submitList(data?.toList())
        }

    }

    private fun openDetailsFragment(airline: Airline) {
        findNavController().navigate(HomeFragmentDirections.navigateToDescriptionFragment(airline))
    }

    private fun addNewAirline() {
        context?.let {
            MaterialDialog(it, BottomSheet(LayoutMode.WRAP_CONTENT)).show {

            }
        }
    }

}