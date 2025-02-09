package com.example.actividadpracticaapppeliculas

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModel
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.room.InvalidationTracker
import com.example.actividadpracticaapppeliculas.database.AppDataBase
import com.example.actividadpracticaapppeliculas.databinding.ActivityMainBinding
import com.example.actividadpracticaapppeliculas.repository.UserRepository
import com.example.actividadpracticaapppeliculas.viewmodel.LoginViewModel

class MainActivity : AppCompatActivity() {
    //declaramos binding para acceder
    private lateinit var binding: ActivityMainBinding

    //declaramos el viewmodel que gestionara la logica de login
    private lateinit var viewModel: LoginViewModel
    override fun onCreate(savedInstanceState: Bundle?) {

        binding = ActivityMainBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // 4. Inicializamos la base de datos, obtenemos el DAO y creamos el Repository.
        val database = AppDataBase.getDatabase(applicationContext)
        val repository = UserRepository(database.userDao())

        // 5. Creamos el ViewModel. Usamos un ViewModelProvider con un Factory simple para inyectar el Repository.
        viewModel = ViewModelProvider(this, object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                @Suppress("UNCHECKED_CAST")
                return LoginViewModel(repository) as T
            }
        }).get(LoginViewModel::class.java)

        // 6. Configuramos el botón de login. Al pulsarlo se extraen las credenciales y se llama a la función del ViewModel.
        binding.btnLogin.setOnClickListener {
            val email = binding.etEmail.text.toString().trim()
            val password = binding.etpassword.text.toString().trim()
            if(email.isNotEmpty() && password.isNotEmpty()){
                viewModel.loginUser(email, password)
            } else {
                Toast.makeText(this, "Por favor ingrese email y contraseña", Toast.LENGTH_SHORT).show()
            }
        }

        // 7. Observamos el LiveData del login en el ViewModel para reaccionar ante el resultado.
        viewModel.loginResult.observe(this, androidx.lifecycle.Observer { user ->
            if (user != null) {
                // Login exitoso: mostrar mensaje y navegar a la siguiente Activity.
                Toast.makeText(this, "Login exitoso", Toast.LENGTH_SHORT).show()
                val intent = Intent(this, MoviesListActivity::class.java)
                startActivity(intent)
                finish() // Opcional: cierra la Activity de login.
            } else {
                // Login fallido: mostrar mensaje de error.
                Toast.makeText(this, "Credenciales incorrectas", Toast.LENGTH_SHORT).show()
            }
        })

        binding.btnRegister.setOnClickListener {
            val intent = Intent(this, SignUpActivity::class.java)
            startActivity(intent)
        }

    }
}