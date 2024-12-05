package com.example.brofin.presentation.main.financials

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.brofin.data.mapper.toPredictResponse
import com.example.brofin.domain.StateApp
import com.example.brofin.domain.models.PredictResponse
import com.example.brofin.domain.repository.RemoteDataRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import javax.inject.Inject

@HiltViewModel
class FinancialViewModel @Inject constructor(
    private val remoteDataRepository: RemoteDataRepository
): ViewModel() {

    private val _stateFinancial = MutableStateFlow<StateApp<PredictResponse>>(StateApp.Idle)
    val stateFinancial = _stateFinancial.asStateFlow()


    fun predict(
        city: String,
        bedrooms: Int,
        bathrooms: Int,
        landSizeM2: Int,
        buildingSizeM2: Int,
        electricity: Int,
        maidBedrooms: Int,
        floors: Int,
        targetYears: Int
    ) {
        viewModelScope.launch {
            _stateFinancial.value = StateApp.Loading
            try {
                val rawJson = """
                    {
                        "city": "$city",
                        "bedrooms": $bedrooms,
                        "bathrooms": $bathrooms,
                        "land_size_m2": $landSizeM2,
                        "building_size_m2": $buildingSizeM2,
                        "electricity": $electricity,
                        "maid_bedrooms": $maidBedrooms,
                        "floors": $floors,
                        "target_years": $targetYears
                    }
                    """
                val requestBody = rawJson.toRequestBody("application/json".toMediaTypeOrNull())

                val response = remoteDataRepository.predictHouse(
                    predictRequest = requestBody
                )

                Log.d("FinancialViewModel", "predict: $response")
                _stateFinancial.value = StateApp.Success(response.toPredictResponse())
            } catch (e: Exception) {
                Log.e("FinancialViewModel", "predict: ${e.message}")
                _stateFinancial.value = StateApp.Error("Kesalahan tidak terduga saat memprediksi harga rumah")
            }
        }
    }

    fun resetState() {
        _stateFinancial.value = StateApp.Idle
    }

}