import com.example.dubaby.models.Product

object Cart {
    private val productsInCart = mutableListOf<Product>()

    fun addProduct(product: Product) {
        productsInCart.add(product)
    }

    fun getProducts(): List<Product> = productsInCart
}
