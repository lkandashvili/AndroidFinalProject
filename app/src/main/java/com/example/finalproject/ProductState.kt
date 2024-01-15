package com.example.finalproject

data class ProductState(
    val products: List<Product> = emptyList(),
    val productCategory: String = "",
    val productName: String = "",
    val imgLink: String = "",
    val isAddingProduct: Boolean = false,
    val sortType: SortType = SortType.CATEGORY
)
