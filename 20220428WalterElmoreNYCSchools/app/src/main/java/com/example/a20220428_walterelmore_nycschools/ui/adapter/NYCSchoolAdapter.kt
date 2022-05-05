package com.example.a20220428_walterelmore_nycschools.ui.adapter

import android.animation.LayoutTransition
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.a20220428_walterelmore_nycschools.data.model.NYCSchool
import com.example.a20220428_walterelmore_nycschools.databinding.NycSchoolFragmentBinding
import com.example.a20220428_walterelmore_nycschools.databinding.NycSchoolItemBinding

class NYCSchoolAdapter(
    private val schools: List<NYCSchool>,
    private val moveToSat: (String) -> Unit
) : RecyclerView.Adapter<NYCSchoolAdapter.NYCSchoolViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        NYCSchoolViewHolder(
            NycSchoolItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: NYCSchoolViewHolder, position: Int) {
        holder.onBind(schools[position])
    }

    override fun getItemCount() = schools.size

    inner class NYCSchoolViewHolder (private val binding: NycSchoolItemBinding)
        : RecyclerView.ViewHolder(binding.root){
            fun onBind(school: NYCSchool){
                binding.apply {
                    tvSchoolName.text = school.schoolName
                    tvSchoolCityAndZipCode.text = String.format(school.city + ", " + school.zip)

                    btnSatScores.setOnClickListener{
                        moveToSat(school.dbn)
                    }
                }
            }
        }
}
