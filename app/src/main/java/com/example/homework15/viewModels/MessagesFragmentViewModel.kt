package com.example.homework15.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.homework15.data.ItemModel
import com.example.homework15.network.RetrofitClient.apiService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class MessagesFragmentViewModel: ViewModel()  {

    private val _itemModels = MutableStateFlow<List<ItemModel>>(emptyList())
    val itemModels: StateFlow<List<ItemModel>> get() = _itemModels

    private val _loading = MutableStateFlow(false)
    val loading: StateFlow<Boolean> get() = _loading

    private val _error = MutableStateFlow("")
    val error: StateFlow<String> get() = _error

    init {
        fetchData()
    }

    private fun fetchData() {
        viewModelScope.launch {
            try {
                _loading.value = true
                val data = apiService.getItemModels()
                _itemModels.value = data
            } catch (e: Exception) {
                _error.value = e.message ?: ""
                // Handle error
            } finally {
                _loading.value = false
            }
        }
    }

    fun search(query: String): List<ItemModel> {
        return itemModels.value.filter { it.owner?.contains(query) ?: false }
    }
}