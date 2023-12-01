package com.example.mvvmstockapp.stock_features.presentation.company_listings

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.mvvmstockapp.core.utils.UiEvent

@Composable
fun CompanyListingScreen(
    onNavigate: (UiEvent.Navigate) -> Unit,
    viewModel: CompanyListingViewModel = hiltViewModel()
) {

    val state = viewModel.state


}