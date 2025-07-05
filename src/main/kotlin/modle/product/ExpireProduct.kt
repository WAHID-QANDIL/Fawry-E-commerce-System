package modle.product

import java.time.LocalDate


/**
 * Represents a product that can expire.
 * This is an abstract class that extends Product and implements CanExpired.
 *
 * @property expiredDate The date when the product expires.
 */

abstract class ExpireProduct(private val expiredDate: LocalDate, id: Int, name: String, price: Double, quantity: Int) :
    Product(
        id,
        name,
        price,
        quantity
    ), CanExpired {
    override fun isExpired(): Boolean = LocalDate.now().isAfter(expiredDate)
}