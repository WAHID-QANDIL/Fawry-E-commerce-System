package modle.product

interface CanExpired {
    /**
     * Checks if the product is expired.
     * @return true if the product is expired, false otherwise.
     */
    fun isExpired(): Boolean
}