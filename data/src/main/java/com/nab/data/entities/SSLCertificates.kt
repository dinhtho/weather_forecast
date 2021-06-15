package com.nab.data.entities

import javax.inject.Inject

data class SSLCertificates @Inject constructor(
    val domainPattern: String,
    val cert1: String,
    val cert2: String,
    val cert3: String,
)