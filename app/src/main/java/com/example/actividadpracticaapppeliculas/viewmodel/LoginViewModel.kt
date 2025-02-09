package com.example.actividadpracticaapppeliculas.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.actividadpracticaapppeliculas.model.User
import com.example.actividadpracticaapppeliculas.repository.UserRepository
import kotlinx.coroutines.launch

class LoginViewModel(private val repository: UserRepository): ViewModel()  {


    //variable para almacenar el resultado del login
    private val _loginResult = MutableLiveData<User?>()

    //variable de solo lectura
    val loginResult: LiveData<User?> get() = _loginResult


    //funcion para realizar el login recibiendo los datos
    fun loginUser(email: String, password: String) {
        // Lanzamos una corrutina para ejecutar esta operación en segundo plano.
        viewModelScope.launch {
            try {
                // Llamamos al méto do login del repository. Este mét odo, en el DAO, realiza una consulta
                // que devuelve el objeto User si las credenciales son correctas, o null si no hay coincidencia.
                val user = repository.login(email, password)
                // Actualizamos el LiveData con el resultado obtenido.
                _loginResult.postValue(user)
            } catch (e: Exception) {
                // En caso de error, publicamos null o podrías optar por manejar el error de otra forma.
                _loginResult.postValue(null)
            }
        }
    }
}