import React, { useEffect, useState } from 'react'
import { getImagenSobre, getSobre } from '../../Services/Sobres'
import { getCartas, getImagenCarta } from '../../Services/Cartas'
import { Carta } from '../Cartas/Carta'
import { Producto } from '../Producto/producto'
import { Link, useParams } from 'react-router-dom'
import { Loader } from '../commons/Loader'


export function Details() {
  const { sobreId } = useParams()
  const [sobre, setSobre] = useState()
  const [cartas, setCartas] = useState([])

  const [loadingCarta, setLoadingCarta] = useState(false)
  const [loadingSobre, setLoadingSobre] = useState(false)

  useEffect(() => {
    setLoadingSobre(true)
    getSobre(sobreId).then(async Sobre => {

      const sobreConImagen = await getImagenSobre(Sobre.imagen).then(imgUrl => {
        Sobre.imagen = imgUrl
        return Sobre
      })
      
      setSobre(sobreConImagen)
    })
    .finally(() => setLoadingSobre(false))

    setLoadingCarta(true)
    getCartas().then(async data => {
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
    .finally(() => setLoadingCarta(false))

  }, [sobreId])

  return (
    <div>
      <h1 className='title'>Detalles de Sobre</h1>
      <div className="escaparate">
        {loadingSobre && <Loader />}
        {sobre&&<Producto key={sobreId} sobre={sobre}></Producto>}
        {console.log("Details",sobre)}
      </div>
      <h2 className='contain'>
        Cartas que contiene
      </h2>
      <div className="escaparate">
        {loadingCarta && <Loader />}
        {cartas
        .filter(carta => carta.idSobre == sobreId)
        .map(carta => {
            // console.log("Carta.sobreId",carta.sobreId)
            // console.log("sobreId",sobreId)
          return (
            // <Carta id={carta.idCarta} nombreImagen={carta.imagen}/>
            <>
              <Link to={`/cartas/${carta.idCarta}`}> <img src={carta.imagen} alt="" className='imagenCarta'/> </Link>
            </>
          )
        })}
      </div>
    </div>
  )
}