package service.shipping

import modle.customer.CartItem
import modle.product.Shippable

class ShippingService() {
    companion object {
        fun ship(list: List<CartItem?>) {
            list.forEach { item ->
                item?.let {
                    println(
                        """
                    Product that can be shipped:
                    Product ID: ${it.product.id},
                    Product Name: ${getName(item)},
                    Product Weight: ${getWeight(item)}
                """.trimIndent()
                    )
                }
            }
        }

        private fun getName(cartItem: CartItem) = cartItem.product.name
        private fun getWeight(cartItem: CartItem): Double {
            return (cartItem.product.canBeShipped as? Shippable)?.weight ?: 0.0
        }

    }


}