package service.shipping

import modle.customer.CartItem
import modle.product.Shippable

class ShippingService() {
    companion object {
        fun ship(list: List<CartItem?>) {
            if (list.isEmpty()) {
                println("No items to ship.")
                return
            }
            if (list.any { it?.product?.canBeShipped == null }) {
                println("Some items cannot be shipped.")
                return
            }
            println("Shipping the following items:")
            println("Shipping items with weight and cost details:")
            list.forEach { item ->
                item?.let {
                    println(
                        """
                    _____________________________________________________
                    Product ID: ${it.product.id},
                    Product Name: ${getName(item)},
                    Product Weight: ${getWeight(item)}
                    Product Price: ${it.product.price},
                    Quantity: ${it.quantity}
                    Shipping Cost: ${getWeight(item) * 10.0} (10 per kg)
                    Total Weight: ${getWeight(item) * it.quantity} kg
                    Total Cost: ${getWeight(item) * it.quantity * 10.0}
                    _____________________________________________________
                """.trimIndent()
                    )
                }
            }
        }

        private fun getName(cartItem: CartItem) = cartItem.product.name
        private fun getWeight(cartItem: CartItem): Double {
            return cartItem.product.canBeShipped?.weight ?: 0.0
        }

    }


}