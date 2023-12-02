package com.example.mvvmstockapp.stock_features.presentation.company_info

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mvvmstockapp.core.utils.Resources
import com.example.mvvmstockapp.core.utils.UiEvent
import com.example.mvvmstockapp.stock_features.domain.repository.StockRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CompanyInfoViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val repository: StockRepository
) : ViewModel() {

    var state by mutableStateOf(CompanyInfoState())
        private set

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    init {
        viewModelScope.launch {
            val symbol = savedStateHandle.get<String>("symbol") ?: return@launch

            state = state.copy(isLoading = true)

            val companyInfoResult = async { repository.getCompanyInfo(symbol) }
            val intradayInfoResult = async { repository.getIntradayInfo(symbol) }

            when(val result = companyInfoResult.await()){
                is Resources.Success -> {
                    state = state.copy(
                        company = result.data,
                        isLoading = false
                    )
                }
                is Resources.Error -> {
                    state = state.copy(
                        isLoading = false
                    )

                    _uiEvent.send(UiEvent.ShowSnackBar(
                        message = result.message ?: "Unknown Error"
                    ))
                }
                is Resources.Loading -> {
                    state = state.copy(
                        isLoading = result.isLoading
                    )
                }

            }

            when(val result = intradayInfoResult.await()){
                is Resources.Success -> {
                   state = state.copy(
                       stocksInfo = result.data ?: emptyList(),
                       isLoading = false
                   )
                }
                is Resources.Error -> {
                    state = state.copy(
                        isLoading = false
                    )

                    _uiEvent.send(UiEvent.ShowSnackBar(
                        message = result.message ?: "Unknown Error"
                    ))
                }
                is Resources.Loading -> {
                    state = state.copy(
                        isLoading = result.isLoading
                    )
                }
            }
        }
    }

}