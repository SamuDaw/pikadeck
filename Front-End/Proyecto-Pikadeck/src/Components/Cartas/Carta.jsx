/* eslint-disable react-hooks/exhaustive-deps */
/* eslint-disable react/prop-types */
import { useState, useEffect } from "react"
import { getImagenCarta } from "../../Services/Cartas"

export function Carta({id, nombreImagen, className, onClick}) {
  const [imagenCarta, setImagenCarta] = useState([])
  const [loading, setLoading] = useState(true)

  useEffect(() => {
    getImagenCarta(nombreImagen).then(ImagenCarta => {
      setImagenCarta(ImagenCarta)
      setLoading(false)
    })
  }, [])

  return (
    <div className={className} onClick={onClick}>
      {/* <Link to={`/cartas/${id}`}>{loading === true ? <p>loading</p> : <img src={imagenCarta} alt="" />}</Link> */}
      {loading === true ? <p>loading</p> : <img src={imagenCarta} alt="" className="imagenCarta"/>}
    </div>
  )
}