package org.bedu.cafebedu.files

//Clase abstracta que se para definir el producto en su aspecto general
abstract class Product(open val name: String) {

    //Todos los productos tienen un sabor, tamaño, precio y se especifíca una cantidad
    abstract val flavor: String
    open protected var price: Float = 0.0f

    //Función que calcula la cantidad a pagar de un producto génerico en base a la cantidad y tamaño

    open fun subTotal(cantidad: Int, tamaño: String) =
        when (tamaño) {
            "Small" -> cantidad * price
            "Medium" -> 1.2f * cantidad * price
            else -> 1.5f * cantidad * price
        }
}