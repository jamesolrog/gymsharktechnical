package com.example.mod_data_products.models

import com.google.gson.annotations.SerializedName

enum class ProductLabel {
    @SerializedName("new") NEW,
    @SerializedName("going-fast") GOING_FAST,
    @SerializedName("popular") POPULAR,
    @SerializedName("limited-edition") LIMITED_EDITION,
}