package service.checkout

import modle.customer.CartItem
import modle.customer.Customer
import modle.customer.Receipt
import service.shipping.ShippingService

class CheckoutService {
    private fun calculateCustomerItemsSubTotal(customer: Customer): Double {
        if (customer.isCartEmpty()) {
            throw IllegalStateException("Cart is empty")
        }
        val subtotal = customer.getTotalCartItemsPrice()
        require(subtotal <= customer.getBalance()) { "Insufficient balance" }
        return subtotal
    }

  private fun shippingCustomerItems(customer: Customer): List<CartItem> {
      val items = customer.getCartItems()
      if (items.isEmpty()) return emptyList()

      val shippableItems = items.filter {
          it.product.canBeShipped != null
      }
      ShippingService.ship(shippableItems)

      return shippableItems
  }

    fun createCustomerReceipt(customer: Customer): Receipt {

        val subTotal = calculateCustomerItemsSubTotal(customer)
        val customerShippableItems = shippingCustomerItems(customer)
        val shippingCost =
            customerShippableItems.sumOf { it.quantity * 10.0 } // Assume that every item will multiplied by 10 for shipping service
        val totalAmount = subTotal + shippingCost
        customer.setBalance(customer.getBalance() - totalAmount)
        customer.clearCart()

        return Receipt(
            products = customer.getCartItems(),
            shippingCost = shippingCost,
            subTotal = subTotal,
            totalAmount = totalAmount,
            shippableItemSelectable = customerShippableItems
        )


    }


}