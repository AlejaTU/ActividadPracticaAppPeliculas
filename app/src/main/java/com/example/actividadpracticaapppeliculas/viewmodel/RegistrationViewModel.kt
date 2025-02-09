package com.example.actividadpracticaapppeliculas.viewmodel

import androidx.lifecycle.LiveData
import kotlinx.coroutines.launch

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.actividadpracticaapppeliculas.model.User
import com.example.actividadpracticaapppeliculas.repository.UserRepository

class RegistrationViewModel(private val repository: UserRepository): ViewModel() {

    private val _registrationResult = MutableLiveData<Boolean>()

    val registrationResult: LiveData<Boolean> get()  = _registrationResult


    //funcion para registrar al usuario
    fun registerUser(email: String, password: String) {
        //corrutina
        viewModelScope.launch {
            try {
                repository.registerUser(User(email, password))
                _registrationResult.value = true
            } catch (e: Exception) {
                _registrationResult.postValue(false)
            }
        }
    }

}