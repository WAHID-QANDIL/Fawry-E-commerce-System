package modle.customer


class Customer {
    private val cart: Cart = Cart()
    private val balance: Double = 0.0
    fun checkOut(): Double {
        if (cart.isEmpty()) {
            throw IllegalStateException("Cart is empty")
        }
        synchronized(this) {
            val totalAmount = cart.sumOf { it.totalPrice }
            if (totalAmount > balance) {
                throw IllegalStateException("Insufficient balance")
            }
            // Process payment and return total amount
            return totalAmount
        }
    }
}

// key -> value
// product name -> 2