package com.github.amrmsaraya.expirytracker.presentation.expired

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.amrmsaraya.expirytracker.domain.usecase.GetExpiredProductsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ExpiredViewModel @Inject constructor(
    private val getExpiredProductsUseCase: GetExpiredProductsUseCase,
    private val dispatcher: CoroutineDispatcher = Dispatchers.Main
) : ViewModel() {

    private var _uiState = MutableStateFlow(ExpiredUiState())
    val uiState = _uiState.asStateFlow()

    private val intentFlow = MutableSharedFlow<ExpiredIntent>()

    init {
        handleIntent()
        getProducts()
    }

    private fun handleIntent() = viewModelScope.launch(dispatcher) {
        intentFlow.collect {
            when (it) {
                is ExpiredIntent.GetProducts -> getProducts()
            }
        }
    }

    fun sendIntent(intent: ExpiredIntent) = viewModelScope.launch(dispatcher) {
        intentFlow.emit(intent)
    }

    private fun getProducts() = viewModelScope.launch(dispatcher) {
        getExpiredProductsUseCase.invoke().collect {
            _uiState.value = _uiState.value.copy(products = it)
        }
    }

}