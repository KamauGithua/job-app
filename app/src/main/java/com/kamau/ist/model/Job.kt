package com.kamau.ist.model

data class Job(
    var id: String = "",
    val title: String = "",
    val description: String = "",
    val company: String = "",
    val jobTypes: String = "",
    val salaryRange: String = "",
    val requirements: String = "",
    val deadline: String = "",
//    val companyLogoUrl: String = "",
    val location: String = "",
    val postedDate: String = "",
    val skills: String = "",
    val qualifications: String = ""
//    val alertLocation: String = "",
//    val isAlertActive: Boolean = false,

//    val smallCompanyDescription: String = "", // Add small company description
//    val bigCompanyDescription: String = ""

)
