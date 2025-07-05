package modle.customer

import java.awt.ItemSelectable

data class Receipt(
    val products: List<CartItem>,
    val shippableItemSelectable: List<CartItem>,
    val totalAmount: Double,
    val shippingCost: Double,
    val subTotal: Double,
)