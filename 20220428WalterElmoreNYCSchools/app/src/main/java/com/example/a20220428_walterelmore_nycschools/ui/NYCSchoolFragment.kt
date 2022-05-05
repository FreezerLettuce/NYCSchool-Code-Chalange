package com.example.a20220428_walterelmore_nycschools.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.a20220428_walterelmore_nycschools.R
import com.example.a20220428_walterelmore_nycschools.data.model.NYCSchool
import com.example.a20220428_walterelmore_nycschools.data.model.UIState
import com.example.a20220428_walterelmore_nycschools.databinding.NycSchoolFragmentBinding
import com.example.a20220428_walterelmore_nycschools.ui.adapter.NYCSchoolAdapter
import com.example.a20220428_walterelmore_nycschools.vm.SchoolViewModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class NYCSchoolFragment: Fragment() {

    private var _binding: NycSchoolFragmentBinding? = null
    private val binding: NycSchoolFragmentBinding
        get() = _binding!!

    private val schoolViewModel: SchoolViewModel by sharedViewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = NycSchoolFragmentBinding.inflate(layoutInflater)
        configureObserver()
        return binding.root
    }

    private fun configureObserver() {
        schoolViewModel.schoolData.observe(viewLifecycleOwner) {state ->
            when(state){
                is UIState.Loading -> {
                    binding.apply {
                        tvLoadingText.visibility = View.VISIBLE
                        rvSchools.visibility = View.GONE
                    }
                }
                is UIState.Error -> {
                    binding.apply {
                        tvLoadingText.text = state.msg
                    }
                }
                is UIState.Success ->{
                    binding.apply {
                        tvLoadingText.visibility = View.GONE
                        rvSchools.visibility = View.VISIBLE
                        rvSchools.apply {
                            adapter = NYCSchoolAdapter(state.response as List<NYCSchool>, ::moveToSAT)
                            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
                        }
                    }
                }
            }
        }
    }
    private fun moveToSAT(schoolName: String){
        parentFragmentManager.beginTransaction()
            .addToBackStack(null)
            .replace(R.id.container_fragment, SATScoreFragment.newInstance(schoolName))
            .commit()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}