package com.jalloft.cryptoprices.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jalloft.cryptoprices.model.Currency
import com.jalloft.cryptoprices.remote.CryptoCurrencyRepository
import com.jalloft.cryptoprices.remote.CurrencyDataSource
import com.jalloft.cryptoprices.remote.DataResource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch


class CryptoCurrencyViewModel constructor(
    private val repository: CryptoCurrencyRepository = CryptoCurrencyRepository(
        CurrencyDataSource()
    )
) : ViewModel() {

    private val _dataResource = MutableLiveData<DataResource<List<Currency>>>()

    val dataResource: LiveData<DataResource<List<Currency>>>
        get() = _dataResource

    init {
        viewModelScope.launch {
            repository.getCrytpoCurrency().flowOn(Dispatchers.IO).collect { result ->
                _dataResource.postValue(result)
            }
        }
    }

    suspend fun getCrytpoCurrency() = repository.getCrytpoCurrency().flowOn(Dispatchers.IO)

}