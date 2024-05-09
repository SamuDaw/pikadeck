import './Buttons.css'

export function BuyButton() {
  // Esperar a que el producto se añada el carrito para poder añadir mas productos
  return (
    <button className="buy-button">Comprar</button>
  )
}

export function AddToCartButton() {
  
  return (
    <button className="add-to-cart-button">Añadir al carrito</button>
  )
}