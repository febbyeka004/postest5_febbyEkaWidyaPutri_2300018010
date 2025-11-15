package com.febby.postest5

import android.net.Uri

data class Post(
    val username: String,
    val profileImage: Int,
    val postImageRes: Int? = null,
    val postImageUri: Uri? = null,
    val caption: String
)
