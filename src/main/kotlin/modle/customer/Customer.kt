package modle.customer

import modle.product.Product


class Customer {
    private val cart: Cart = Cart()
    private var balance: Double = 0.0

    fun addToCart(product: Product, quantity: Int = 1) {
        cart.add(product, quantity)
    }
    fun isCartEmpty() = cart.isEmpty()
    fun getBalance() = balance
    fun setBalance(newBalance: Double) {
        require(newBalance >= 0) { "Balance cannot be negative" }
        // Assuming balance can be set, but in a real-world scenario, this might be managed differently
        this.balance = newBalance
    }

    fun getTotalCartItemsPrice() = cart.sumOf { it.totalPrice }

    fun getCartItems() = cart.getCartItems()

    fun clearCart() = cart.clearCart()
}

// key -> value
// product name -> 2