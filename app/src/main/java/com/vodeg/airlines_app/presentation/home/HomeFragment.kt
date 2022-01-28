package com.vodeg.airlines_app.presentation.home

import android.os.Bundle
import android.text.Editable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doAfterTextChanged
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.vodeg.airlines_app.R
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
            afterSearchQueryChange(query)
        }
    }


    private fun initObservers() {
        homeViewModel.allAirlines.observe(viewLifecycleOwner, {
            setData(it)
        })
        homeViewModel.addSuccess.observe(viewLifecycleOwner, {
            if (it) {
                showMessage(resources.getString(R.string.success_add))
            } else {
                showError(resources.getString(R.string.general_error))
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
            showError(resources.getString(R.string.no_data))
        } else {
            airlinesAdapter.differ.submitList(data?.toList())
        }

    }

    private fun showBottomSheet() {
        fragmentManager?.let { NewAirlineBottomSheet(it).setClickListener(this) }
    }

    override fun addNewAirline(airline: Airline) {
        homeViewModel.addNewAirline(airline)
    }

    private fun search() {
        val query = _binding.etSearch.text.toString()
        if (query.isNotEmpty()) {
            homeViewModel.filter(query)
        } else {
            showError(resources.getString(R.string.enter_data))
        }
    }

    private fun afterSearchQueryChange(query: Editable?) {
        if (query?.isEmpty() == true) {
            homeViewModel.getAllAirlines()
            rv_airlinesList.scrollToPosition(0)
        }
    }

    private fun openDetailsFragment(airline: Airline) {
        homeViewModel.resetLiveData()
        findNavController().navigate(HomeFragmentDirections.navigateToDescriptionFragment(airline))
    }
}