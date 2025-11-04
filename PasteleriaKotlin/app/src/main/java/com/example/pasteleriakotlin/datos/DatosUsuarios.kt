package com.example.pasteleria.data

// Usamos 'object' para la lista de usuarios.
// Es mutable para poder "simular" el registro de nuevos usuarios.
object DatosUsuarios {

    val listaUsuarios = mutableListOf<Usuario>(
        // añadir usuario de prueba
        Usuario(email = "test@test.com", contrasena = "1234")
    )
}