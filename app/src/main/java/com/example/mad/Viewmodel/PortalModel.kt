package com.example.mad.Viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.ViewModelFactoryDsl
import com.example.mad.model.Portal

class PortalModel : ViewModel() {
    private val _portalList = MutableLiveData<List<Portal>>(listOf())
    val portalList: LiveData<List<Portal>> = _portalList

    fun addPortal(portal: Portal) {
        val newList = _portalList.value?.plus(portal) ?: listOf(portal)
        _portalList.value = newList
    }

}
