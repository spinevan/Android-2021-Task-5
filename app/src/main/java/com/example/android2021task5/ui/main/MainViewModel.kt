package com.example.android2021task5.ui.main

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.android2021task5.CAT_API_HOST
import com.example.android2021task5.CAT_API_MAX_COUNT_AGES
import com.example.android2021task5.data.models.CatImage
import com.example.android2021task5.data.repositoryes.CatImagesRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.lang.Exception
import java.net.InetAddress

class MainViewModel : ViewModel() {

    private val catImagesRepository: CatImagesRepository = CatImagesRepository()
    var catsPage = 0

    private val _catImages = MutableLiveData<List<CatImage>>()
    val catImages: LiveData<List<CatImage>> get() = _catImages

    val isLoadingCats = MutableLiveData(false)
    var isInternetAvailable = MutableLiveData(isInternetAvailable())

    init {
        initCatImages()
    }

    fun initCatImages() {
        viewModelScope.launch {
            val hasInternet = withContext(Dispatchers.IO) {
                return@withContext isInternetAvailable()
            }
            isInternetAvailable.value = hasInternet

            if (hasInternet) {
                isLoadingCats.value = true
                _catImages.value = catImagesRepository.getCatImages(catsPage)
                isLoadingCats.value = false
            }
        }
    }

    fun loadNextPageCatImages() {

        catsPage++

        if (catsPage > CAT_API_MAX_COUNT_AGES) {
            return
        }

        viewModelScope.launch {
            val hasInternet = withContext(Dispatchers.IO) {
                return@withContext isInternetAvailable()
            }
            isInternetAvailable.value = hasInternet

            if (hasInternet) {
                isLoadingCats.value = true
                val listCats = catImagesRepository.getCatImages(catsPage)

                val resList: MutableList<CatImage> = mutableListOf()
                _catImages.value?.let { resList.addAll(it) }
                resList.addAll(listCats)
                _catImages.value = resList
                isLoadingCats.value = false
            }
        }
    }

    @WorkerThread
    private fun isInternetAvailable(): Boolean {
        return try {
            val ip: InetAddress = InetAddress.getByName(CAT_API_HOST)
            ip.address.isNotEmpty()
        } catch (e: Exception) {
            false
        }
    }
}
