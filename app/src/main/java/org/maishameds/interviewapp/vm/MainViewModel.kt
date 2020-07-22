package org.maishameds.interviewapp.vm

import android.util.Log
import androidx.lifecycle.*
import kotlinx.coroutines.*
import org.maishameds.interviewapp.models.PostModel
import org.maishameds.interviewapp.network.Resource
import org.maishameds.interviewapp.repository.MainRepository
import kotlin.coroutines.CoroutineContext

class MainViewModel : ViewModel(), CoroutineScope {

    private val mainRepo: MainRepository = MainRepository()

    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        Log.e("MainViewModel","Error", throwable)
    }

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.IO + SupervisorJob() + exceptionHandler



    fun getPosts(): LiveData<Resource<PostModel>> {
        return liveData(coroutineContext) {
            val data = mainRepo.getPosts()
            emit(Resource.loading(null))
            emit(data)
        }
    }

    override fun onCleared() {
        super.onCleared()
        coroutineContext.cancel()
    }
}