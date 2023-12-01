package com.example.mvvmstockapp.stock_features.presentation.company_listings

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mvvmstockapp.core.utils.Resources
import com.example.mvvmstockapp.core.utils.UiEvent
import com.example.mvvmstockapp.stock_features.domain.repository.StockRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class CompanyListingViewModel @Inject constructor(
    private val repository: StockRepository
) : ViewModel() {

    var state by mutableStateOf(CompanyListingState())
        private set

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    private var searchJob: Job? = null

    fun onEvent(event: CompanyListingEvent){
        when(event){
            is CompanyListingEvent.Refresh -> {
                getCompanyListing(fetchFromRemote = true)
            }
            is CompanyListingEvent.SearchQueryChange -> {
                state = state.copy(
                    searchQuery = event.query
                )
                searchJob?.cancel()
                searchJob = viewModelScope.launch {
                    delay(500L)
                    getCompanyListing()
                }
            }
        }
    }

    private fun getCompanyListing(
        query: String = state.searchQuery.lowercase(),
        fetchFromRemote: Boolean = false
    ){
         viewModelScope.launch {
            repository.getCompanyListing(fetchFromRemote, query)
                .onEach{ result ->
                    when(result){
                        is Resources.Success -> {
                            state = state.copy(
                                companies = result.data ?: emptyList(),
                                isLoading = false
                            )
                        }
                        is Resources.Error -> {
                            state = state.copy(
                                companies = result.data ?: emptyList(),
                                isLoading = false
                            )
                            _uiEvent.send(UiEvent.ShowSnackBar(
                                message = "Unknown Error"
                            ))

                        }
                        is Resources.Loading -> {
                            state = state.copy(
                                companies = result.data ?: emptyList(),
                                isLoading = true
                            )
                        }
                    }
                }
        }

    }

}