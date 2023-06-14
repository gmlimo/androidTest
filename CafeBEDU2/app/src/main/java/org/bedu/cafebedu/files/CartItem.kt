package org.bedu.cafebedu.files

data class CartItem(
    var imageResourceId: Int,
    var product: String,
    var quantity: Int,
    var price: Float
)