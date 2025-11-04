package com.example.pasteleriakotlin.ui.viewModel

import androidx.compose.runtime.State
import androidx.compose.runtime.derivedStateOf // <-- Importante
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.pasteleriakotlin.datos.ItemCarrito
import com.example.pasteleriakotlin.datos.Producto
import kotlin.collections.find
import kotlin.collections.sumOf
import kotlin.collections.toMutableList

class CarritoViewModel : ViewModel() {

    // Lista privada mutable de items en el carrito
    private val _items = mutableStateOf<List<ItemCarrito>>(emptyList())

    // Lista pública inmutable que la UI (pantalla) observará
    val items: State<List<ItemCarrito>> = _items

    // El 'total' se calcula automáticamente cada vez que la lista de items cambia.
    val total: State<Double> = derivedStateOf {
        _items.value.sumOf { it.producto.precio * it.cantidad }
    }

    /**
     * Añade un producto al carrito.
     * Si ya existe, suma la cantidad.
     */
    fun agregarAlCarrito(producto: Producto, cantidad: Int) {
        // No añadir si la cantidad es cero o negativa
        if (cantidad <= 0) return

        val listaActual = _items.value.toMutableList()
        // Buscar si el producto ya está en el carrito
        val itemExistente = listaActual.find { it.producto.id == producto.id }

        if (itemExistente != null) {
            // Si ya existe, sumar la nueva cantidad a la existente
            val nuevaCantidad = itemExistente.cantidad + cantidad
            val itemActualizado = itemExistente.copy(cantidad = nuevaCantidad)

            // Reemplazar el item viejo en la lista
            val indice = listaActual.indexOf(itemExistente)
            listaActual[indice] = itemActualizado
        } else {
            // Si no existe, añadirlo como un nuevo item
            listaActual.add(ItemCarrito(producto = producto, cantidad = cantidad))
        }

        // Actualizar el estado con la nueva lista
        _items.value = listaActual
    }

    /**
     * Elimina un item del carrito.
     */
    fun eliminarDelCarrito(item: ItemCarrito) {
        val listaActual = _items.value.toMutableList()
        listaActual.remove(item)
        _items.value = listaActual
    }

    /**
     * Vacía el carrito (lo usaremos después de "Pagar").
     */
    fun limpiarCarrito() {
        _items.value = emptyList()
    }
}