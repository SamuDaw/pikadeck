/* eslint-disable react/jsx-key */
/* eslint-disable react/prop-types */
import { Sobre } from "./Sobre";
import { Carta } from "../Cartas/Carta";
import "./AperturaSobre.css"
import { useEffect, useState } from "react";

export function AperturaSobre ({sobre, cartas, abriendoSobre, ImagenSobre, setAbriendoSobre}) {
  const [classNameCarta, setClassNameCarta] = useState("none")
  
  useEffect(() => {
    if (abriendoSobre)
    {
      setTimeout(() => {
        setClassNameCarta("cartaSobre")
      }, 5100)
    }
  }, [abriendoSobre])
  
  const handleClick = (e) => {
    e.target.parentNode.className = "cartaVista"
  }
  const volverAlInventario = () => {
    setAbriendoSobre(false)
  }

  return(
    <>
      <div className="escenarioSobre">
        <div>
          <Sobre ImagenSobre={ImagenSobre} className={abriendoSobre ? "abriendoSobre" : "inventarioSobre"} nombreImagen={sobre.imagen}/>
          {cartas.map(carta => {
            return (
              <Carta className={classNameCarta} onClick={(e) => handleClick(e)} nombreImagen={carta.imagen}/>
            )
          })}
          <button onClick={volverAlInventario} className="volverInventario">Volver al inventario</button>
        </div>
      </div>
    </>
  )
}