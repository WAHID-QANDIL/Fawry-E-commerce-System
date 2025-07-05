package modle.product

import java.time.LocalDate


/**
 * Represents a product that can expire.
 * This is an abstract class that extends Product and implements CanExpired.
 *
 * @property expiredDate The date when the product expires.
 */

abstract class ExpiredbleProduct(private val expiredDate: LocalDate) : Product(), CanExpired {
    override fun isExpired(): Boolean = LocalDate.now().isAfter(expiredDate)
}