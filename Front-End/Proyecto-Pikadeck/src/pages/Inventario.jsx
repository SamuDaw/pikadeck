/* eslint-disable react-hooks/exhaustive-deps */
/* eslint-disable react/jsx-key */
import { useEffect, useState } from "react"
import { getImagenCarta, getInventarioCartas } from "../Services/Cartas"
import { getImagenSobre, getInventarioSobres } from "../Services/Sobres"
import { Sobre } from "../Components/Sobres/Sobre"
import { Carta } from "../Components/Cartas/Carta"
import './Inventario.css'
import { Loader } from "../Components/commons/Loader"

export default function Inventario(){
  const [cartas, setCartas] = useState([])
  const [sobres, setSobres] = useState([])
  const [cartasLoading, setCartasLoading] = useState(false)
  const [sobresLoading, setSobresLoading] = useState(false)
  const [sobreAbierto, setSobreAbierto] = useState(null)

  
  useEffect(() => {
    setSobresLoading(true)
    getInventarioSobres().then(async data => {

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
    .finally(() => setSobresLoading(false))

    // Recogemos las cartas del inventario  
    setCartasLoading(true)
    getInventarioCartas().then(async data => {
      const cartasConImagenPromises = data.map(carta => {
        try{
          return getImagenCarta(carta.imagen).then(imgUrl => {
            carta.imagen = imgUrl
            return carta
          })
        }
        catch(error)
        {
          console.log('Error en getImagenCarta')
          console.log(error)
        }
      })
      
      const cartasConImagen = await Promise.all(cartasConImagenPromises)
      setCartas(cartasConImagen)
    })
    .finally(() => setCartasLoading(false))
  }, [sobreAbierto])
  
  return(
    <div className="inventario-content">
      <h1 className="title">Inventario</h1>
      <h2 className="contain">Sobres</h2>
      <div className="items">
        {sobresLoading && <Loader/>}
        {sobres && sobres.map(sobre => {
          return(
            <div>
              <h3>{sobre.nombre}</h3>
              <Sobre sobre={sobre} ImagenSobre={sobre.imagen} className="inventarioSobre" setSobreAbierto={setSobreAbierto} sobreAbierto={sobreAbierto}/>
            </div>
          )
        })}
      </div>
      <h1 className="title">Cartas</h1>
      <div className="items">
        {cartasLoading && <Loader/>}
        {cartas && cartas.map(carta => {
        return( 
          <div key={carta.id}>
            <h3>{carta.nombre}</h3>
            <img src={carta.imagen} alt="" className="imagenCarta"/>
          </div>
        )
      })}</div>


    </div>
  )
}