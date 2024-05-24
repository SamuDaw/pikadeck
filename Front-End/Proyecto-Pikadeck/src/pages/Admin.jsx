import { useState } from "react"
import { crearSobre } from "../Services/Sobres"
import { crearCarta } from "../Services/Cartas"

export function Admin () {
  const [LoadingSobre, setLoadingSobre] = useState(false)
  const [LoadingCarta, setLoadingCarta] = useState(false)
  const handleSubmitSobre = (event) => {
    setLoadingSobre(true)
    event.preventDefault()
    const form = event.target
    const formData = new FormData(form)
    formData.append('numeroCarta', '10');
    crearSobre(formData).then(() => {
      console.log("Sobre creado correctamente")
    })
    .finally(() => {
      setLoadingSobre(false)
      form.reset()
    })
  }

  const handleSubmitCarta = (event) => {
    event.preventDefault()
    setLoadingCarta(true)
    const form = event.target
    const formData = new FormData(form)
    crearCarta(formData).then(() => {
      console.log("Carta creada correctamente")
    })
    .finally(() => {
      setLoadingCarta(false)
      form.reset()
    })
  }
  return(
    <>
      <h1 className="title">Admin</h1>
      <h2 className="contain">Crear nuevo sobre</h2>
      <div className="formLogin-content">
        <div className="formLogin-container">
          <form onSubmit={(e) => handleSubmitSobre(e)} className="formLogin">
            <label htmlFor="nombre">Nombre del sobre</label>
            <input type="text" placeholder="Introduce el nombre del sobre..." name="nombre" required disabled={LoadingSobre} className="formInput"/>
            <label htmlFor="precio">Precio del sobre</label>
            <input type="text" placeholder="Introduce el precio del sobre..." name="precio" required disabled={LoadingSobre} className="formInput"/>
            <label htmlFor="stock">Stock del sobre</label>
            <input type="text" placeholder="Introduce el stock del sobre..." name="stock" required disabled={LoadingSobre} className="formInput"/>
            <label htmlFor="imagen">Imagen representativa del sobre</label>
            <input type="file" placeholder="Añade la imagen del sobre" name="imagen" required disabled={LoadingSobre} className="formInput"/>
            <button type="submit" className="formSubmit">Crear sobre</button>
          </form>
        </div>
      </div>

      <h2 className="contain">Crear nueva carta</h2>
      <div className="formLogin-content">
        <div className="formLogin-container">
          <form onSubmit={(e) => handleSubmitCarta(e)} className="formLogin">
            <label htmlFor="nombre">Nombre de la carta</label>
            <input type="text" placeholder="Introduce el nombre de la carta..." name="nombre" required disabled={LoadingCarta} className="formInput"/>

            <label htmlFor="descripción">Descripción</label>
            <input type="text" placeholder="Introduce la descripción de la carta..." name="descripción" required disabled={LoadingCarta} className="formInput"/>

            <label htmlFor="img">Imagen representativa de la carta</label>
            <input type="file" placeholder="Añade la imagen de la carta" name="img" required disabled={LoadingCarta} className="formInput"/>

            <label htmlFor="idPokemon">Numero de pokedex del pokemon</label>
            <input type="text" placeholder="Introduce el numero de la pokedex del pokemon..." name="idPokemon" required disabled={LoadingCarta} className="formInput"/>

            <label htmlFor="tipoCarta">Introduce el tipo de carta (1 siendo comun, 2 siendo infrecuente, 3 siendo rara y 4 siendo holografica)</label>
            {/* <input type="text" placeholder="Introduce el numero de la pokedex del pokemon..." name="tipoCarta" required disabled={LoadingCarta}/> */}
            <select name="tipoCarta" id="" className="formInput">
              <option value="1">1</option>
              <option value="2">2</option>
              <option value="3">3</option>
              <option value="4">4</option>
            </select>

            <label htmlFor="idSobre">Número de la generación del pokemon</label>
            <input type="text" placeholder="Introduce el numero de la generación del pokemon..." name="idSobre" required disabled={LoadingCarta} className="formInput"/>

            <button type="submit" className="formSubmit">Crear sobre</button>
          </form>
        </div>
      </div>
    </>
  )
}