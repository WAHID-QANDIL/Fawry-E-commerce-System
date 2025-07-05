package modle.customer

import modle.product.Product

class Cart {
    private val cart: MutableMap<Int, CartItem> = mutableMapOf()
    fun add(product: Product, quantity: Int = 1) {
        require(quantity > 0) { "Quantity must be greater than zero" }
        require(product.quantity >= quantity) { "Not enough quantity available for ${product.name}" }

        val existingItem = cart[product.id]
        if (existingItem != null) {
            cart[existingItem.product.id] = existingItem.copy(
                quantity = existingItem.quantity + quantity,
                totalPrice = existingItem.totalPrice + (product.price * quantity)
            )
        } else {
            cart[product.id] = CartItem(
                product = product,
                quantity = quantity,
                totalPrice = product.price * quantity
            )
        }

    }

    fun remove(product: Product, quantity: Int = 1) {
        require(quantity > 0) { "Quantity must be greater than zero" }
        require((cart[product.id]?.quantity ?: 0) >= quantity) { "Not enough quantity in cart for ${product.name}" }


        val existingItem = cart[product.id]
        requireNotNull(existingItem) { "Product ${product.name} not found in cart" }

        if (existingItem.quantity == quantity) {
            cart.remove(product.id)
        }
    }

    fun updateQuantity(product: Product, quantity: Int) {
        require(quantity > 0) { "Quantity must be greater than zero" }
        require((cart[product.id]?.quantity ?: 0) >= quantity) { "Not enough quantity in cart for ${product.name}" }
        val existingItem = cart[product.id]
        requireNotNull(existingItem) { "Product ${product.name} not found in cart" }
        cart[product.id] = existingItem.copy(
            quantity = quantity,
            totalPrice = product.price * quantity
        )

    }

    fun getItemQuantity(product: Product): Int {
        return cart[product.id]?.quantity ?: 0
    }

    fun getItemTotalPrice(product: Product): Double {
        return cart[product.id]?.totalPrice ?: 0.0
    }

    fun getCartItems(): List<CartItem> {
        return cart.entries.map {
            it.value
        }
    }

    fun sumOf(predicate: (CartItem) -> Double): Double {
        return cart.entries.sumOf { entry ->
            predicate(entry.value)
        }
    }

    fun isEmpty(): Boolean {
        return cart.isEmpty()
    }

    fun clearCart() {
        cart.clear()
    }
}