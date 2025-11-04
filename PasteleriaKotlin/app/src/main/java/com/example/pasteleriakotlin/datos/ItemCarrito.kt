package com.example.pasteleriakotlin.datos


// No necesitas importar Producto, porque está en el mismo paquete 'datos'

data class ItemCarrito(
    val producto: Producto,
    val cantidad: Int
)