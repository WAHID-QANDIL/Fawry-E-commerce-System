package service.checkout

import data.remove
import modle.customer.CartItem
import modle.customer.Customer
import modle.customer.Receipt
import modle.product.CanExpired
import service.shipping.ShippingService

class CheckoutService {
    private fun calculateCustomerItemsSubTotal(customer: Customer): Double {
        if (customer.isCartEmpty()) {
            throw IllegalStateException("Cart is empty")
        }

        val validItems = customer.getCartItems().filterNot { isProductExpired(it) }
        if (validItems.isEmpty()) {
            throw IllegalStateException("All products in cart are expired")
        }

        val subtotal = validItems.sumOf { it.totalPrice }
        require(subtotal <= customer.getBalance()) { "Insufficient balance" }
        return subtotal
    }

    private fun isProductExpired(cartItem: CartItem): Boolean {
        return (cartItem.product as? CanExpired)?.isExpired() ?: false
    }

    private fun shippingCustomerItems(customer: Customer): List<CartItem> {
        val items = customer.getCartItems()
        if (items.isEmpty()) return emptyList()

        val validShippableItems = items.filter {
            it.product.canBeShipped != null && !isProductExpired(it)
        }
        ShippingService.ship(validShippableItems)

        return validShippableItems
    }

    fun createCustomerReceipt(customer: Customer): Receipt {
        val validItems = customer.getCartItems().filterNot { isProductExpired(it) }
        if (validItems.isEmpty()) {
            throw IllegalStateException("All products in cart are expired")
        }

        val subTotal = calculateCustomerItemsSubTotal(customer)
        val customerShippableItems = shippingCustomerItems(customer)
        val shippingCost = customerShippableItems.sumOf { it.quantity * 10.0 }
        val totalAmount = subTotal + shippingCost
        customer.setBalance(customer.getBalance() - totalAmount)
        customer.clearCart()

        return Receipt(
            products = validItems,
            shippingCost = shippingCost,
            subTotal = subTotal,
            totalAmount = totalAmount,
            shippableItemSelectable = customerShippableItems
        ).also { receipt->
            receipt.products.forEach {cartItem->
                remove(cartItem.product, cartItem.quantity)
            }
        }
    }
}