package data

import modle.product.Product
import modle.product.Shippable

// This data may come from database or so
// In this project I don't use multi-threading, but in real scenarios it assumes to lock other threads to avoid race-condition <--------------

val dummyData: MutableMap<Int, Product> = mutableMapOf()
fun add(product: Product, quantity: Int = 1) {
    require(product.quantity >= quantity) { "Not enough quantity available for ${product.name}" }
    require(quantity > 0) { "Quantity must be greater than zero" }

    val existingItem = dummyData[product.id]
    if (existingItem != null) {
        dummyData[existingItem.id]?.quantity = dummyData[existingItem.id]?.quantity?.plus(quantity) ?: 0
    } else {
        dummyData[product.id] = product
    }

}
fun remove(product: Product, quantity: Int = 1) {
    require(quantity > 0) { "Quantity must be greater than zero" }
    val existingItem = dummyData[product.id]
    if (existingItem != null) {
        if (existingItem.quantity < quantity) {
            throw IllegalStateException("Not enough quantity available for ${product.name}")
        }
        existingItem.quantity -= quantity
        if (existingItem.quantity <= 0) {
            dummyData.remove(existingItem.id)
        }
    } else {
        throw IllegalStateException("Product with id ${product.id} does not exist")
    }
}

fun addDummyData() {
    // Insert products into the data store
    (0..100).map {
        Product(
            id = it,
            name = "Product $it",
            price = (1..100).random().toDouble(),
            quantity = (100..500).random()
        )
    }.forEach { product ->
        add(product)
    }
    // Insert products that can be shipped

}
fun addDummyDataShippable() {
    // Insert shippable products into the data store
    (101..200).map {
        Product(
            id = it,
            name = "Shippable Product $it",
            price = (1..100).random().toDouble(),
            quantity = (100..500).random(),
            canBeShipped = object:Shippable {
                override val weight: Double = (100..300).random().toDouble()
            }
        )
    }.forEach { product ->
        add(product)
    }
}