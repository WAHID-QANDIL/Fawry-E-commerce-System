package modle.customer

import modle.product.CartItem

data class Receipt(
    val products: List<CartItem>,
    val totalAmount: Double,
    val shipping: Double,
    val subTotal: Double,
)