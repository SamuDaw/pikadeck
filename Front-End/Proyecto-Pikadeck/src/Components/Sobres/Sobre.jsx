/* eslint-disable no-unused-vars */
/* eslint-disable react-hooks/exhaustive-deps */
/* eslint-disable react/prop-types */
import { useEffect, useState } from "react"
import { getImagenSobre } from "../../Services/Sobres"
import { AperturaSobre } from "../Sobres/AperturaSobre"
import { abrirSobre } from "../../Services/Sobres"
import './Sobre.css'


export function Sobre({sobre, className, ImagenSobre, setSobreAbierto, sobreAbierto}) {

  const [abriendoSobre, setAbriendoSobre] = useState(false)
  const [cartas, setCartas] = useState([])

  const abrirSobreInventario = (sobre) => {
    setAbriendoSobre(true)

    abrirSobre(sobre.sobreId)
    .then(data => {
      setCartas(data)
    })
    .finally(() => {
      setSobreAbierto(!sobreAbierto)
    })
  }

  return (
    <>
      <div className={className}>
        <img className="imagenSobre" src={ImagenSobre} alt="" />
        {className === "inventarioSobre" &&
          <>
            <button onClick={() => abrirSobreInventario(sobre)} className="abrirSobre">Abrir sobre</button>
            {abriendoSobre && <AperturaSobre ImagenSobre={ImagenSobre} abriendoSobre={abriendoSobre} sobre={sobre} cartas={cartas} setAbriendoSobre={setAbriendoSobre}/>}
          </>
        }
      </div>
    </>
  )
}


