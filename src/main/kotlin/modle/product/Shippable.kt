package modle.product

interface Shippable {
    val weight: Double
    fun ship(): Product
}