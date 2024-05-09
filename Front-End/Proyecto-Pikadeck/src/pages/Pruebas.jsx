/* eslint-disable react/prop-types */
import { Link } from '../Components/Router/Link.jsx'

export default function Pruebas({routeParams}) {
  return (
    <>
      <h1>Pruebas {routeParams.id}</h1>
      <Link className='MeGustaLaFruta' to='/'>Ir a la Landing</Link>
    </>
  )
}