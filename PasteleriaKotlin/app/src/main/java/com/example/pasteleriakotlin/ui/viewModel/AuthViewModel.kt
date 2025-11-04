package com.example.pasteleriakotlin.ui.viewModel

import androidx.lifecycle.ViewModel
import com.example.pasteleria.data.DatosUsuarios
import com.example.pasteleria.data.Usuario
import kotlin.collections.any
import kotlin.collections.find

// Para crear este ViewModel, necesitas añadir una dependencia más.
// Ve a tu build.gradle.kts (Module :app) y añade:
// implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.7.0")
// Y presiona "Sync Now".

class AuthViewModel : ViewModel() {

    // Variable para guardar el email del usuario que inició sesión (opcional pero útil)
    var emailUsuarioLogueado: String? = null
        private set

    /**
     * Función para intentar iniciar sesión.
     * @return Boolean: 'true' si fue exitoso, 'false' si no.
     */
    fun login(email: String, contrasena: String): Boolean {
        // Busca en la lista de usuarios si alguno coincide
        val usuarioEncontrado = DatosUsuarios.listaUsuarios.find {
            it.email == email && it.contrasena == contrasena
        }

        if (usuarioEncontrado != null) {
            emailUsuarioLogueado = email // Guardamos quién inició sesión
            return true // ¡Éxito!
        }

        return false // Falló
    }

    /**
     * Función para registrar un nuevo usuario.
     * @return Boolean: 'true' si se registró, 'false' si el email ya existía.
     */
    fun registrar(email: String, contrasena: String): Boolean {
        // 1. Verificar si el email ya existe
        val emailYaExiste = DatosUsuarios.listaUsuarios.any { it.email == email }

        if (emailYaExiste) {
            return false // El email ya está tomado
        }

        // 2. Si no existe, crear el nuevo usuario
        val nuevoUsuario = Usuario(email = email, contrasena = contrasena)

        // 3. Añadirlo a nuestra "base de datos"
        DatosUsuarios.listaUsuarios.add(nuevoUsuario)

        return true // ¡Registro exitoso!
    }
}