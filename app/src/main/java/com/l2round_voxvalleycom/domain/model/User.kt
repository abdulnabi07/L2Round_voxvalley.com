package com.l2round_voxvalleycom.domain.model

data class User(
    val uid: String,
    val name: String? = null,
    val phone: String? = null,
    val email: String? = null
)
