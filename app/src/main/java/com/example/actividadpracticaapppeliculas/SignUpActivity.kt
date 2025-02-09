package com.example.actividadpracticaapppeliculas

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.actividadpracticaapppeliculas.database.AppDataBase
import com.example.actividadpracticaapppeliculas.databinding.SignupActivityBinding
import com.example.actividadpracticaapppeliculas.repository.UserRepository
import com.example.actividadpracticaapppeliculas.viewmodel.RegistrationViewModel

class SignUpActivity: AppCompatActivity() {

    private lateinit var binding: SignupActivityBinding
    //declarar el viewmodel que gestione la logica
    private lateinit var viewModel: RegistrationViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //inicializar el binding
        binding = SignupActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)


        //inicializar base de datos y repositorio
        val database = AppDataBase.getDatabase(applicationContext)
        val repository = UserRepository(database.userDao())
        // Creamos el RegistrationViewModel usando un ViewModelProvider con un Factory simple para inyectar el Repository
        viewModel = ViewModelProvider(this, object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                @Suppress("UNCHECKED_CAST")
                return RegistrationViewModel(repository) as T
            }
        }).get(RegistrationViewModel::class.java)

        // Configuramos el botón de registro
        binding.btnCrearCuenta.setOnClickListener {
            val email = binding.etRegistraremail.text.toString().trim()
            val password = binding.etRegistrarpassword.text.toString().trim()
            if (email.isNotEmpty() && password.isNotEmpty()) {
                // Llamamos a la función del ViewModel para registrar el usuario
                viewModel.registerUser(email, password)
            } else {
                Toast.makeText(this, "Por favor ingrese email y contraseña", Toast.LENGTH_SHORT).show()
            }
        }

        // Observamos el LiveData que expone el resultado del registro para reaccionar ante el resultado
        viewModel.registrationResult.observe(this, Observer { success ->
            if (success) {
                Toast.makeText(this, "Registro exitoso", Toast.LENGTH_SHORT).show()
                // Una vez registrado, navegamos a la pantalla de login
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish() // Opcional: cierra la Activity de registro
            } else {
                Toast.makeText(this, "Error en el registro", Toast.LENGTH_SHORT).show()
            }
        })
    }
}