package com.vodeg.airlines_app.presentation.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doAfterTextChanged
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.afollestad.materialdialogs.LayoutMode
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.bottomsheets.BottomSheet
import com.vodeg.airlines_app.data.model.Airline
import com.vodeg.airlines_app.databinding.FragmentHomeBinding
import com.vodeg.airlines_app.presentation.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_home.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.ArrayList

class HomeFragment : BaseFragment() {
    private lateinit var _binding: FragmentHomeBinding
    private lateinit var airlinesAdapter: AirlinesAdapter
    private lateinit var airlinesAdapterAnim: AirlinesAdapterAnim
    private val homeViewModel: HomeViewModel by viewModel()
    private lateinit var airLinesList: MutableList<Airline>

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
        airlinesAdapterAnim = AirlinesAdapterAnim()
        _binding.rvAirlinesList.apply {
            adapter = airlinesAdapterAnim
            layoutManager = LinearLayoutManager(activity)
        }
    }

    private fun initListener() {
        airlinesAdapter.setOnItemClickListener {
            openDetailsFragment(it)
        }
        airlinesAdapterAnim.setOnItemClickListener {
            openDetailsFragment(it)
        }
        _binding.fbAdd.setOnClickListener {
            addNewAirline()
        }
        _binding.ibSearch.setOnClickListener {

        }
        _binding.etSearch.doAfterTextChanged { s ->
            if (s.toString().isEmpty()) {
                airlinesAdapterAnim.animateTo(airLinesList)
                rv_airlinesList.scrollToPosition(0)
            }
            val filteredList = airlinesAdapterAnim.filter(airLinesList, s.toString())
            filteredList?.let { airlinesAdapterAnim.animateTo(it) }
            rv_airlinesList.scrollToPosition(0)
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
            //airlinesAdapter.differ.submitList(data?.toList())
            airLinesList = data!!
            airlinesAdapterAnim.setData(data?.toList() as ArrayList<Airline>)

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