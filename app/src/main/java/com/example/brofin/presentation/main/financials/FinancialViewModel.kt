package com.example.brofin.presentation.main.financials

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.brofin.data.mapper.toPredictEntity
import com.example.brofin.data.mapper.toPredictResponse
import com.example.brofin.domain.StateApp
import com.example.brofin.domain.models.GadgetRecommendation
import com.example.brofin.domain.models.GameRecommendation
import com.example.brofin.domain.models.LuxuryRecommendation
import com.example.brofin.domain.models.MobilRecommendation
import com.example.brofin.domain.models.MotorRecommendation
import com.example.brofin.domain.models.PredictResponse
import com.example.brofin.domain.repository.BrofinRepository
import com.example.brofin.domain.repository.RemoteDataRepository
import com.example.brofin.utils.Constant
import com.example.brofin.utils.generateUniqueId
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import javax.inject.Inject

@HiltViewModel
class FinancialViewModel @Inject constructor(
    private val remoteDataRepository: RemoteDataRepository,
    private val brofinRepository: BrofinRepository
): ViewModel() {

    private val _stateFinancial = MutableStateFlow<StateApp<PredictResponse>>(StateApp.Idle)
    val stateFinancial = _stateFinancial.asStateFlow()

    private val _stateInsertAndRemove = MutableStateFlow<StateApp<PredictResponse>>(StateApp.Idle)
    val stateInsertAndRemove = _stateInsertAndRemove.asStateFlow()

    private val _gameState = MutableStateFlow<StateApp<List<GameRecommendation?>>>(StateApp.Idle)
    val gameState = _gameState.asStateFlow()

    private val _motorState = MutableStateFlow<StateApp<List<MotorRecommendation?>>>(StateApp.Idle)
    val motorState = _motorState.asStateFlow()

    private val _mobilState = MutableStateFlow<StateApp<List<MobilRecommendation?>>>(StateApp.Idle)
    val mobilState = _mobilState.asStateFlow()

    private val _luxuryState = MutableStateFlow<StateApp<List<LuxuryRecommendation?>>>(StateApp.Idle)
    val luxuryState = _luxuryState.asStateFlow()

    private val _gadgetState = MutableStateFlow<StateApp<List<GadgetRecommendation?>>>(StateApp.Idle)
    val gadgetState = _gadgetState.asStateFlow()


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

                Log.d(TAG, "predict: $response")
                _stateFinancial.value = StateApp.Success(response.toPredictResponse())
            } catch (e: Exception) {
                Log.e("FinancialViewModel", "predict: ${e.message}")
                _stateFinancial.value = StateApp.Error("Kesalahan tidak terduga saat memprediksi harga rumah")
            }
        }
    }

    fun insertPredict(predict: PredictResponse){
        viewModelScope.launch {
            _stateInsertAndRemove.value = StateApp.Loading
            try {
                val data = predict.copy(
                    id = generateUniqueId(),
                    datePredict = System.currentTimeMillis()
                )
                brofinRepository.insertPredict(data.toPredictEntity())
                _stateInsertAndRemove.value = StateApp.Success(data)
            } catch (e: Exception){
                Log.d(TAG, "insertPredict: $e")
                _stateInsertAndRemove.value = StateApp.Error("Error saat menyimpan prediksi")
            }
        }
    }

    fun deletePredict(predict: PredictResponse){
        viewModelScope.launch {
            try {
                brofinRepository.deletePredict(predict.toPredictEntity())
                StateApp.Success(null)
            } catch (e: Exception){
                Log.e(TAG,"Error saat menghapus prediksi")
            }
        }
    }

    fun resetStateInserAndRemove(){
        viewModelScope.launch {
            _stateInsertAndRemove.value = StateApp.Idle
        }
    }


    private fun predictGameRecommendation(budget: Double, category: String) {
        viewModelScope.launch {
            _gameState.value = StateApp.Loading
            try {
                val rawJson = """
                { 
                    "category": "$category", 
                    "budget": $budget   
                }
            """
                val requestBody = rawJson.toRequestBody("application/json".toMediaTypeOrNull())
                val responseList = remoteDataRepository.getRecommendationGame(requestBody)
                if (responseList.isEmpty()) {
                    _gameState.value = StateApp.Error("Tidak Ada Rekomendasi")
                } else {
                    _gameState.value = StateApp.Success(responseList)
                }

            } catch (e: Exception) {
                Log.e("FinancialViewModel", "predictGameRecommendation: ${e.message}")
                _gameState.value = StateApp.Error("Terjadi kesalahan: ${e.message}")
            }
        }
    }



    private fun predictMobilRecommendation(budget: Double, category: String){
        viewModelScope.launch {
            _mobilState.value = StateApp.Loading
            try {
                val rawJson = """
                { 
                    "category": "$category", 
                    "budget": $budget   
                }
            """
                val requestBody = rawJson.toRequestBody("application/json".toMediaTypeOrNull())
                val responseList = remoteDataRepository.getRecommendationMobil(requestBody)
                if (responseList.isEmpty()) {
                    _mobilState.value = StateApp.Error("Tidak Ada Rekomendasi")
                } else {
                    _mobilState.value = StateApp.Success(responseList)
                }

            } catch (e: Exception) {
                _mobilState.value = StateApp.Error("Terjadi kesalahan: ${e.message}")
            }
        }
    }

    private fun predictMotorRecommendation(budget: Double, category: String){
        viewModelScope.launch {
            _motorState.value = StateApp.Loading
            try {
                val rawJson = """
                { 
                    "category": "$category", 
                    "budget": $budget   
                }
            """
                val requestBody = rawJson.toRequestBody("application/json".toMediaTypeOrNull())
                val responseList = remoteDataRepository.getRecommendationMotor(requestBody)
                if (responseList.isEmpty()) {
                    _motorState.value = StateApp.Error("Tidak Ada Rekomendasi")
                } else {
                    _motorState.value = StateApp.Success(responseList)
                }

            } catch (e: Exception) {
                _motorState.value = StateApp.Error("Terjadi kesalahan: ${e.message}")
            }
        }
    }

    private fun predictLuxuryRecommendation(budget: Double, category: String){
        viewModelScope.launch {
            _luxuryState.value = StateApp.Loading
            try {
                val rawJson = """
                { 
                    "category": "$category", 
                    "budget": $budget   
                }
            """
                val requestBody = rawJson.toRequestBody("application/json".toMediaTypeOrNull())
                val responseList = remoteDataRepository.getRecommendationLuxury(requestBody)
                if (responseList.isEmpty()) {
                    _luxuryState.value = StateApp.Error("Tidak Ada Rekomendasi")
                } else {
                    _luxuryState.value = StateApp.Success(responseList)
                }

            } catch (e: Exception) {
                _luxuryState.value = StateApp.Error("Terjadi kesalahan: ${e.message}")
            }
        }
    }


    private fun predictGadgetRecommendation(budget: Double, category: String){
        viewModelScope.launch {
            _gadgetState.value = StateApp.Loading
            try {
                val rawJson = """
                { 
                    "category": "$category", 
                    "budget": $budget   
                }
            """
                val requestBody = rawJson.toRequestBody("application/json".toMediaTypeOrNull())
                val responseList = remoteDataRepository.getRecommendationGadget(requestBody)
                if (responseList.isEmpty()) {
                    _gadgetState.value = StateApp.Error("Tidak Ada Rekomendasi")
                } else {
                    _gadgetState.value = StateApp.Success(responseList)
                }
            } catch (e: Exception) {
                _gadgetState.value = StateApp.Error("Terjadi kesalahan: ${e.message}")
            }
        }
    }

    fun recommendationPredict(index: Int, budget: Double) {
        viewModelScope.launch {
            resetState2()
            val options = Constant.options
            when (val selected = options[index]) {
                "mobil" -> predictMobilRecommendation(budget, selected)
                "gadget" -> predictGadgetRecommendation(budget, selected)
                "motor" -> predictMotorRecommendation(budget, selected)
                "luxury" -> predictLuxuryRecommendation(budget, selected)
                "game" -> predictGameRecommendation(budget, selected)

            }

        }
    }

    fun resetState() {
        _stateFinancial.value = StateApp.Idle
    }

    fun resetState2() {
        _gameState.value = StateApp.Idle
        _motorState.value = StateApp.Idle
        _mobilState.value = StateApp.Idle
        _luxuryState.value = StateApp.Idle
        _gadgetState.value = StateApp.Idle
    }

    companion object{
        const val TAG = "FinancialViewModel"
    }
}