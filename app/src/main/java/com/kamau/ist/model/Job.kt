package com.kamau.ist.model

data class Job(
    var id: String = "",
    val title: String = "",
    val description: String = "",
    val company: String = "",


    val requirements: String = "",
    val deadline: String = "",
    //added
    val companyLogoUrl: String = "",  // Add the company logo URL
    val location: String = "",        // Add the job location
    val postedDate: String = "",      // Add the posted date
    val category: String = "",        // Add the job category
    val employmentType: String = "",  // Add the employment type
    val skills: List<String> = listOf(), // Add the skills list
    val qualifications: List<String> = listOf(), // Add qualifications
    val alertLocation: String = "",   // Add alert location
    val isAlertActive: Boolean = false, // Add the alert status
    val companyName: String = "",     // Add company name
    val smallCompanyDescription: String = "", // Add small company description
    val bigCompanyDescription: String = ""

)
