/* eslint-disable no-unused-vars */
/* eslint-disable react/jsx-key */
import { useEffect } from "react"
import { eliminarSobreCarrito, getCarrito } from "../Services/Carrito"
import { useState } from "react"
import { createPedido } from "../Services/Pedido"
import { Loader } from "../Components/commons/Loader"
import { getImagenSobre } from "../Services/Sobres"
import { Sobre } from "../Components/Sobres/Sobre"
import './Carrito.css'

export function Carrito () {
  const [loading, setLoading] = useState(true)
  const [status, setStatus] = useState(null)
  const [sobres, setSobres] = useState(null)
  const [sobreBorrado, setSobreBorrado] = useState(false)

  useEffect(() => {
    setLoading(true)
    getCarrito()
      .then(async data => {
        const sobresConImagenPromises = data.map(sobre => {
          try{
            return getImagenSobre(sobre.imagen).then(imgUrl => {
              sobre.imagen = imgUrl
              return sobre
            })
          }
          catch(error)
          {
            console.log('Error en getImagenSobre')
            console.log(error)
          }
        })
  
        const sobresConImagen = await Promise.all(sobresConImagenPromises)
        
        setSobres(sobresConImagen)
      })
      .catch(err => console.error(err))
      .finally(() => setLoading(false))
  }
  , [status, sobreBorrado])


  const handleClick = () => {
    createPedido().then(status => {
      setStatus(status)
    })
  }

  const precioTotalCarrito = sobres ? sobres.reduce((acc, sobre) => acc + sobre.precio, 0) : 0
  
  const eliminarSobre = (sobre) => {
    eliminarSobreCarrito(sobre.sobreId).then(status => {
      setSobreBorrado(!sobreBorrado)
    })
  }

  return (
    <div className="carrito-content">
      <h1 className="title">Carrito</h1>
      {loading && <Loader />}
      {sobres && <table className="carrito-table">
        <tr>
          <th className="thCarrito">Sobre</th>
          <th className="thCarrito">Nombre</th>
          <th className="thCarrito">Precio</th>
          <th className="thCarrito">Eliminar</th>
        </tr>
        {sobres.map(sobre => (
          <tr>
            <td><Sobre ImagenSobre={sobre.imagen} className="sobre-carrito"/></td>
            <td>{sobre.nombre}</td>
            <td>{sobre.precio} €</td>
            <td>
              <span onClick={() => eliminarSobre(sobre)}>
                <svg className="carrito-eliminar" xmlns="http://www.w3.org/2000/svg" x="0px" y="0px" width="100" height="100" viewBox="0 0 30 30">
                  <path fill="#03024b" d="M 14.984375 2.4863281 A 1.0001 1.0001 0 0 0 14 3.5 L 14 4 L 8.5 4 A 1.0001 1.0001 0 0 0 7.4863281 5 L 6 5 A 1.0001 1.0001 0 1 0 6 7 L 24 7 A 1.0001 1.0001 0 1 0 24 5 L 22.513672 5 A 1.0001 1.0001 0 0 0 21.5 4 L 16 4 L 16 3.5 A 1.0001 1.0001 0 0 0 14.984375 2.4863281 z M 6 9 L 7.7929688 24.234375 C 7.9109687 25.241375 8.7633438 26 9.7773438 26 L 20.222656 26 C 21.236656 26 22.088031 25.241375 22.207031 24.234375 L 24 9 L 6 9 z">
                  </path>
                </svg>
              </span>
            </td>
          </tr>
        ))}
      </table>}
      <div className="carrito-precio">
        <p className="contain">Precio total del carrito: {precioTotalCarrito} euros</p>
      </div>
      <button onClick={handleClick} className="carrito-comprar">Comprar ahora</button>
      {status === 201 && <p>Compra realizada con éxito</p>}
      {status === 403 && <p>Ha habido un error</p>}
    </div>
  )
}