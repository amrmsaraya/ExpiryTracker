package com.github.amrmsaraya.expirytracker.presentation.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.amrmsaraya.expirytracker.domain.entity.Product
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
class DetailsViewModel @Inject constructor(
    private val insertProductsUseCase: InsertProductsUseCase,
    private val dispatcher: CoroutineDispatcher = Dispatchers.Main
) : ViewModel() {
    private var _uiState = MutableStateFlow(DetailsUiState())
    val uiState = _uiState.asStateFlow()

    private val intentFlow = MutableSharedFlow<DetailsIntent>()

    init {
        handleIntent()
    }

    private fun handleIntent() = viewModelScope.launch(dispatcher) {
        intentFlow.collect {
            when (it) {
                is DetailsIntent.UpdateBarcode -> updateBarcode(it.barcode)
                is DetailsIntent.UpdateName -> updateName(it.name)
                is DetailsIntent.UpdateCategory -> updateCategory(it.category)
                is DetailsIntent.UpdateDate -> updateDate(it.date)
                is DetailsIntent.InsertProduct -> insertProduct(it.product, it.onFinish)
            }
        }
    }

    fun sendIntent(intent: DetailsIntent) = viewModelScope.launch(dispatcher) {
        intentFlow.emit(intent)
    }

    private fun updateBarcode(barcode: String) = viewModelScope.launch(dispatcher) {
        _uiState.value = uiState.value.copy(barcode = barcode)
    }

    private fun updateName(name: String) = viewModelScope.launch(dispatcher) {
        _uiState.value = uiState.value.copy(name = name)
    }

    private fun updateCategory(category: String) = viewModelScope.launch(dispatcher) {
        _uiState.value = uiState.value.copy(category = category)
    }

    private fun updateDate(date: Long) = viewModelScope.launch(dispatcher) {
        _uiState.value = uiState.value.copy(expiryDate = date)
    }

    private fun insertProduct(product: Product, onFinish: () -> Unit) =
        viewModelScope.launch(dispatcher) {
            insertProductsUseCase.invoke(product)
            onFinish()
        }

}