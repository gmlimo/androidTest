package org.bedu.cafebedu.files

//El otro producto es el café y se define en esta clase
class Coffee(override val flavor: String): Product("Coffee") {

    protected var sabor = ""
    override var price = data.productList.getValue("café")

    init {
        this.sabor = flavor
    }

    //Esta función calcula el precio total del café basado en la cantidad y el tamaño
    override fun subTotal(cantidad: Int, tamaño: String): Float {
        var total = super.subTotal(cantidad, tamaño)
        println("Tu pedido son: $cantidad tazas de café $tamaño con un subtotal de: $total ")
        return total
    }

}