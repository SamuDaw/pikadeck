/* eslint-disable react/prop-types */

import { useParams } from "react-router-dom"

export default function Pruebas() {
  const { id } = useParams()
  console.log(id)
  return (
    <>
      <h1>Pruebas</h1>
      <p>Esto es una página de pruebas</p>
      <p>El parametro que pasamos por url es: {id ? id : null}</p>
    </>
  )
}