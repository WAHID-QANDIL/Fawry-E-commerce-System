import data.addDummyData
import data.addDummyDataShippable
import data.dummyData
import modle.customer.Customer
import modle.product.Shippable
import service.checkout.CheckoutService

fun main() {
    try {
        val products = dummyData
        addDummyData()
        addDummyDataShippable()

        val customer = Customer()
        customer.setBalance(1000.0) // Set initial balance for the customer

        customer.addToCart(
            products.values.first(),
            quantity = 6
        )
        //add shippable product
        customer.addToCart(
            products.values.first { it.canBeShipped != null },
            quantity = 6
        )
        val checkoutService = CheckoutService()
        val receipt = checkoutService.createCustomerReceipt(customer)
        println("\n** Shipment notice **")
        receipt.products.forEach {
            val product = it.product
            if (product.canBeShipped is Shippable) {
                println("${it.quantity}x ${product.name} ${product.canBeShipped.weight.toInt()}Kg")
            }
        }
        if (receipt.shippableItemSelectable.isEmpty()) {
            println("No products to ship.")
        } else {
            println("Total package weight ${receipt.shippableItemSelectable.sumOf { (it.quantity * it.product.canBeShipped?.weight!!) }}kg")
        }

        println("\n** Checkout receipt **")
        receipt.products.forEach {
            val product = it.product
            println("${it.quantity}x ${product.name} ${product.price.toInt()}")
        }
        println("----------------------")
        println("Subtotal ${receipt.subTotal.toInt()}")
        println("Shipping ${receipt.shippingCost.toInt()}")
        println("Amount ${receipt.totalAmount.toInt()}")
        println("Checkout completed successfully.")
        println("Balance after checkout: ${customer.getBalance().toInt()}")
    } catch (e: Exception) {
        println("Error: ${e.message}")
    }
}
//CONSOLE OUTPUT
//** Shipment notice **
//2x Cheese 400g
//1x Biscuits 700g
//Total package weight 1.1kg
//** Checkout receipt **
//2x Cheese 200
//1x Biscuits 150
//----------------------
//Subtotal 350
//Shipping 30
//Amount 380
