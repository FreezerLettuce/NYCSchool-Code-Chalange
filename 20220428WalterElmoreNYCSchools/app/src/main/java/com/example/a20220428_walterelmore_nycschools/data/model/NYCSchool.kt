package com.example.a20220428_walterelmore_nycschools.data.model

import com.google.gson.annotations.SerializedName

data class NYCSchool(
    @SerializedName("dbn")
    val dbn: String,
    @SerializedName("school_name")
    val schoolName: String,
    @SerializedName("primary_address_line_1")
    val address: String,
    @SerializedName("city")
    val city: String,
    @SerializedName("zip")
    val zip: String
)
