package modle.customer

import modle.product.Product

data class CartItem(
    val product: Product,
    val quantity: Int,
    val totalPrice: Double = product.price * quantity
)