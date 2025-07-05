package modle.product

/**
 * Represents a product with a name and price.
 * This is an abstract class that can be extended to create specific product types.
 */
abstract class Product {
    abstract val id: Int
    abstract val name: String
    abstract val price: Double
    abstract val quantity: Int
    abstract val canBeShipped: Shippable

    override fun toString(): String {
        return "Product(name='$name', price=$price)"
    }
}