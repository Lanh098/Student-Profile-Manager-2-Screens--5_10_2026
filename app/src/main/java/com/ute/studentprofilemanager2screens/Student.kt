package com.ute.studentprofilemanager2screens

import java.io.Serializable
data class Student(
    val id: String = "2415053122123",
    var name: String = "Bùi Tá Lanh",
    var studentClass: String = "24T1",
    var gpa: Float = 3.5f,
    var phone: String = "0375124178"
) : Serializable