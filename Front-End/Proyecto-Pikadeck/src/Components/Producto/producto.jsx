/* eslint-disable react/prop-types */
import { Sobre } from "../Sobres/Sobre"
import './producto.css'
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome'
import { faCartShopping } from '@fortawesome/free-solid-svg-icons'
import { addSobreToCarrito } from '../../Services/Carrito'
import { Link } from 'react-router-dom'


export function Producto({sobre}) {
    const handleClick = () => {
        addSobreToCarrito(sobre.sobreId)
    }
    {console.log("Producto",sobre.imagen)}
    return (
        <div className="producto">
            <div className="imagenProducto">
            <Link to={`/shop/${sobre.sobreId}`}><Sobre ImagenSobre={sobre.imagen}></Sobre></Link>
                
            </div>
            <div className="infoProducto">
                <p className="nombreProducto">Sobre: {sobre.nombre}</p>
                <div className="preciosProducto">
                    <p className="precioProductoSinDescuento">{sobre.precio} € </p>
                    <p className="precioProductoActual"> { sobre.precio - (sobre.precio * 0.1) } € </p>
                </div>
                <div>
                    <button className="boton-carrito" onClick={handleClick}><FontAwesomeIcon icon={faCartShopping} /></button>
                </div>
            </div>
        </div>
    )
}