package com.dicoding.courseschedule.ui.add

import android.app.*
import androidx.lifecycle.*
import com.dicoding.courseschedule.data.*

class ViewModelFactory(private val repository: DataRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        try {
            return modelClass.getConstructor(DataRepository::class.java).newInstance(repository)

        } catch (e: InstantiationException) {
            throw RuntimeException("Cannot create an instance of  $modelClass", e)
        } catch (e: InstantiationException) {
            throw RuntimeException("Cannot create an instance of  $modelClass", e)
        } catch (e: InstantiationException) {
            throw RuntimeException("Cannot create an instance of  $modelClass", e)
        } catch (e: InstantiationException) {
            throw RuntimeException("Cannot create an instance of  $modelClass", e)
        }
    }

    companion object {
        fun createViewModelFactory(activity: Activity): ViewModelFactory {
            val context = activity.applicationContext
                ?: throw IllegalArgumentException("Not yet attached to Application")
            return ViewModelFactory(DataRepository.getInstance(context)!!)
        }
    }
}