package com.example.finalproject

sealed interface ProductEvent {
    object SaveContact: ProductEvent
    data class SetCategory(val category: String): ProductEvent
    data class SetName(val name: String): ProductEvent
    data class setImageLink(val imgLink: String): ProductEvent
    object ShowDialog: ProductEvent
    object HideDialog:ProductEvent
    data class SortProducts(val sortType: SortType): ProductEvent
    data class DeleteProduct(val product: Product): ProductEvent
}