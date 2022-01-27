package com.vodeg.airlines_app.presentation.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doAfterTextChanged
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.vodeg.airlines_app.data.model.Airline
import com.vodeg.airlines_app.databinding.FragmentHomeBinding
import com.vodeg.airlines_app.presentation.base.BaseFragment
import com.vodeg.airlines_app.presentation.dialog.NewAirlineBottomSheet
import kotlinx.android.synthetic.main.fragment_home.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeFragment : BaseFragment(), NewAirlineBottomSheet.BottomDialogListener {
    private lateinit var _binding: FragmentHomeBinding
    private lateinit var airlinesAdapter: AirlinesAdapter
    private val homeViewModel: HomeViewModel by viewModel()
    private lateinit var newAirLine: Airline


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
            showBottomSheet()
        }
        _binding.ibSearch.setOnClickListener {
            search()
        }
        _binding.etSearch.doAfterTextChanged { query ->
            if (query?.isEmpty() == true) {
                homeViewModel.getAllAirlines()
                rv_airlinesList.scrollToPosition(0)
            }
        }

    }

    private fun initObservers() {
        homeViewModel.allAirlines.observe(viewLifecycleOwner, {
            setData(it)
        })

        homeViewModel.addSuccess.observe(viewLifecycleOwner, {
            if (it) {
                showError("New AirLine Added Successfully")
                updateList()
            } else {
                showError("failed To Add New Airline , please try Again ")
            }
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
            showError("No Data find!!")
        } else {
            airlinesAdapter.differ.submitList(data?.toList())
        }

    }

    private fun showBottomSheet() {
        fragmentManager?.let { NewAirlineBottomSheet(it).setClickListener(this) }
    }

    override fun addNewAirline(airline: Airline) {
        newAirLine = airline
        homeViewModel.addNewAirline(airline)

    }

    private fun updateList() {
        //airlinesAdapter.differ.currentList.add(newAirLine)
        airlinesAdapter.addItem(newAirLine)
    }

    private fun search() {
        val query = _binding.etSearch.text.toString()
        if (query.isNotEmpty()) {
            homeViewModel.filter(query)
        } else {
            showError("Please Enter Search Value!")
        }
    }

    private fun openDetailsFragment(airline: Airline) {
        findNavController().navigate(HomeFragmentDirections.navigateToDescriptionFragment(airline))
    }
}