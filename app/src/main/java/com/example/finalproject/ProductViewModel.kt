package com.example.finalproject

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@OptIn(ExperimentalCoroutinesApi::class)
class ProductViewModel(private val dao: ProductDao): ViewModel() {
    private val _sortType = MutableStateFlow(SortType.CATEGORY)
    private val _state = MutableStateFlow(ProductState())
    private val _products = _sortType
        .flatMapLatest { sortType ->
            when(sortType) {
                SortType.CATEGORY -> dao.getProductsOrderedByCategory()
                SortType.NAME -> dao.getProductsOrderedByName()
            }
        }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), emptyList())

    val state = combine(_state, _sortType, _products) {
        state, sortype, products ->
        state.copy(
            products = products,
            sortType = sortype,
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), ProductState())

    fun onEvent(event: ProductEvent) {
        when(event) {
            is ProductEvent.DeleteProduct -> {
                viewModelScope.launch {
                    dao.deleteProduct(event.product)
                }
            }
            ProductEvent.HideDialog -> {
                _state.update { it.copy(
                    isAddingProduct = false
                )}
            }
            ProductEvent.SaveContact -> {

                val category = state.value.productCategory
                val name = state.value.productName
                val imglink = state.value.imgLink

                if (category.isBlank() || name.isBlank() || imglink.isBlank()) {
                    return
                }

                val product = Product(
                    category = category,
                    name = name,
                    image = imglink
                )
                viewModelScope.launch {
                    dao.upsertProduct(product)
                }
                _state.update { it.copy(
                    isAddingProduct = false,
                    productCategory = "",
                    productName = "",
                    imgLink = ""
                ) }

            }
            is ProductEvent.SetCategory -> {
                _state.update { it.copy(
                    productCategory = event.category
                ) }
            }
            is ProductEvent.SetName -> {
                _state.update { it.copy(
                    productName = event.name
                ) }
            }
            ProductEvent.ShowDialog -> {
                _state.update { it.copy(
                    isAddingProduct = true
                ) }
            }
            is ProductEvent.setImageLink -> {
                _state.update { it.copy(
                    imgLink = event.imgLink
                ) }
            }

            else -> {}
        }
    }

}