package com.githukudenis.comlib

import java.text.SimpleDateFormat

object AndroidSdk {
    const val targetSdk = 34
    const val compileSdk = 33
    const val minimumSdk = 27
    const val namespace = "com.githukudenis.comlib"
    const val applicationId = "com.githukudenis.comlib"

    private val code = 0
        .plus(SimpleDateFormat("yyyyMMdd").format(System.currentTimeMillis()).plus("00").toInt())
        .plus(0) // change this if you're shipping for the same day

    val versionCode = code
    val versionName = "v".plus(SimpleDateFormat("yyyy.MM (dd)").format(System.currentTimeMillis()))
}