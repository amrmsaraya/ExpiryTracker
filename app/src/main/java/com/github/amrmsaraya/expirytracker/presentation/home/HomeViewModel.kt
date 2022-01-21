package com.github.amrmsaraya.expirytracker.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.amrmsaraya.expirytracker.domain.entity.Product
import com.github.amrmsaraya.expirytracker.domain.usecase.GetValidProductsUseCase
import com.github.amrmsaraya.expirytracker.domain.usecase.InsertProductsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getValidProductsUseCase: GetValidProductsUseCase,
    private val insertProductsUseCase: InsertProductsUseCase,
    private val dispatcher: CoroutineDispatcher = Dispatchers.Main
) : ViewModel() {

    private var _uiState = MutableStateFlow(HomeUiState())
    val uiState = _uiState.asStateFlow()

    private val intentFlow = MutableSharedFlow<HomeIntent>()

    init {
        println("HOME: $dispatcher")
        handleIntent()
        getProducts()
    }

    private fun handleIntent() = viewModelScope.launch(dispatcher) {
        intentFlow.collect {
            when (it) {
                is HomeIntent.GetProducts -> getProducts()
                is HomeIntent.InsertProduct -> insertProduct(it.product)
            }
        }
    }

    fun sendIntent(intent: HomeIntent) = viewModelScope.launch(dispatcher) {
        intentFlow.emit(intent)
    }

    private fun getProducts() = viewModelScope.launch(dispatcher) {
        getValidProductsUseCase.invoke().collect {
            _uiState.value = uiState.value.copy(products = it)
        }
    }

    private fun insertProduct(product: Product) = viewModelScope.launch(dispatcher) {
        insertProductsUseCase.invoke(product)
    }

}