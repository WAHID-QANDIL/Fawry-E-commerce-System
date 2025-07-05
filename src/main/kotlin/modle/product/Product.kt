package modle.product

/**
 * Represents a product with a name and price.
 * This is an abstract class that can be extended to create specific product types.
 */
open class Product(
    val id: Int,
    val name: String,
    val price: Double,
    var quantity: Int,
    val canBeShipped: Shippable? = null // Assume the product not has to be shipped by default
) {
    override fun toString(): String {
        return "Product(id=$id, name='$name', price=$price, quantity=$quantity) ${canBeShipped?.let { ", canBeShipped=${it.weight}" } ?: ""}"
    }
}