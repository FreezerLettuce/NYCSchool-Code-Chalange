package com.example.a20220428_walterelmore_nycschools.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.a20220428_walterelmore_nycschools.R
import com.example.a20220428_walterelmore_nycschools.data.model.SatScore
import com.example.a20220428_walterelmore_nycschools.data.model.UIState
import com.example.a20220428_walterelmore_nycschools.databinding.SatScoreFragmentBinding
import com.example.a20220428_walterelmore_nycschools.vm.SchoolViewModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class SATScoreFragment: Fragment() {

    private var dbn = ""
    private var _binding: SatScoreFragmentBinding? = null
    private val binding: SatScoreFragmentBinding
        get() = _binding!!

    private val schoolViewModel: SchoolViewModel by sharedViewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = SatScoreFragmentBinding.inflate(layoutInflater)

        dbn = arguments?.getString(DBN).toString()
        schoolViewModel.getScores()

        configureObserver()
        return  binding.root
    }

    private fun configureObserver() {
        schoolViewModel.satData.observe(viewLifecycleOwner) {state ->
            when(state) {
                is UIState.Loading -> {
                    binding.apply {
                        tvSatLoadingText.visibility = View.VISIBLE
                    }
                }
                is UIState.Error -> {
                    binding.apply {
                        tvSatLoadingText.text = state.msg
                    }
                }
                is UIState.Success -> {
                    val satScore = findSchoolByDBN(state.response as List<SatScore>)
                    if (satScore == null) {
                        binding.apply {
                            tvSatLoadingText.text = "No SAT data for school"
                        }
                    } else {
                        binding.apply {
                            tvSatLoadingText.visibility = View.GONE
                            tvSatSchoolName.apply {
                                text = satScore.schoolName
                                visibility = View.VISIBLE
                            }
                            tvAveragesTitle.visibility = View.VISIBLE
                            tvTakers.apply {
                                text = "Takers: " + satScore.testTakers
                                visibility = View.VISIBLE
                            }
                            tvAvgReading.apply {
                                text = "Reading: " + satScore.avgReading
                                visibility = View.VISIBLE
                            }
                            tvAvgMath.apply {
                                text = "Math: " + satScore.avgMath
                                visibility = View.VISIBLE
                            }
                            tvAvgWriting.apply {
                                text =  "Wrighting: " + satScore.avgWriting
                                visibility = View.VISIBLE
                            }
                        }
                    }
                }
            }
        }
    }

    private fun findSchoolByDBN(satScores: List<SatScore>): SatScore?{
        return  satScores.singleOrNull { it.dbn == dbn }
    }

    companion object{
        private const val DBN = "dbn"
        fun newInstance(dbn : String) : SATScoreFragment{
            val fragment = SATScoreFragment()
            val bundle = Bundle()
            bundle.putString(DBN, dbn)
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}