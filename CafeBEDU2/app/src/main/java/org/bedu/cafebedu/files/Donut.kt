package org.bedu.cafebedu.files

//Uno de los producto a manejar son las donas, esta clase define las donas

val data = Data()
class Donut(override val flavor: String): Product("Donut") {

    protected var sabor = ""
    override var price = data.productList.getValue("donas")

    init {
        this.sabor = flavor
    }

    //Función para calcular el precio de la cantidad de donas en base a su tamaño
    override fun subTotal(cantidad: Int, tamaño: String): Float {
        var total = super.subTotal(cantidad, tamaño)
        println("Tu pedido son: $cantidad donas $tamaño con un subtotal de: $total ")
        return total
    }

}