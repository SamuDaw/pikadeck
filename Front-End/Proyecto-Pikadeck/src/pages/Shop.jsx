// eslint-disable react-hooks/exhaustive-deps /
// eslint-disable react/jsx-key /
import { useEffect, useState } from "react"
import { getImagenSobre, getSobres } from "../Services/Sobres"
import { Sobre } from "../Components/Sobres/Sobre"
import { getCartas } from "../Services/Cartas"
import { Carta } from "../Components/Cartas/Carta"
import { Producto } from "../Components/Producto/producto"
import './Shop.css'
import { Link } from "react-router-dom"
import { Loader } from "../Components/commons/Loader"


export function Shop() {

  const [sobres, setSobres] = useState([])
  const [cartas, setCartas] = useState([])
  const [loadingSobre, setLoadingSobre] = useState(false)

  useEffect(() => {
    try {
      setLoadingSobre(true)
      getSobres().then(async SobresSoberanos => {

        const sobresConImagenPromises = SobresSoberanos.map(sobre => {
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
      .finally(() => setLoadingSobre(false))
    }
    catch(error)
    {
      console.log('Error en getSobres')
    }

    getCartas().then(Cartas => {
      setCartas(Cartas)
    })

  }, []) // Si pones array vacio solo se ejecuta 1 vez, que es al cargar el componente
  //console.log(sobres)
  return (
    <div>
      <div className="banner">
        <h1>¡Nueva Colección Disponible!</h1>
        <p>¿Buscas el mazo perfecto para tu colección de cartas Pokémon? Si es así estás en el lugar indicado para encontrar las latas y colecciones de Pokémon Trading Card Game más actuales. Mejora tu colección comprando las cartas TCG indicadas y deja a tus oponentes K.O. en todas tus batallas.</p>
      </div>

      <div className="escaparate">
        {loadingSobre && <Loader/>}
        {sobres && sobres.map(sobre => {
            return (
                console.log(sobre),
                <Producto key={sobre.id} sobre={sobre}></Producto>
                //<Producto key={sobre.id} sobre={sobre}></Producto>
                //<Sobre nombreImagen={sobre.imagen}></Sobre>
            )
            })}
      </div>

      {/* <h3>
        Cartas
      </h3>
      <div>
        {cartas.map(carta => {
          return (
            <Carta key={carta.id} nombreImagen={carta.imagen}/>
          )
        })}
      </div> */}
      </div>
    )
  }