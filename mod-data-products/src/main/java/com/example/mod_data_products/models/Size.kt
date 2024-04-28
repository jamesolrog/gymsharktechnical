package com.example.mod_data_products.models

import com.google.gson.annotations.SerializedName

enum class Size {
    @SerializedName("xs")
    XS,
    @SerializedName("s")
    S,
    @SerializedName("m")
    M,
    @SerializedName("l")
    L,
    @SerializedName("xl")
    XL,
    @SerializedName("xxl")
    XXL,
}