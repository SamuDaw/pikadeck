import { Link } from '../Components/Router/Link.jsx'




export default function Landing() {
  return(
    <>
      <h1>Bienvenido a Pikadeck</h1>
      <Link to='/pruebas'>Pruebas</Link>
      <Link to='/sesion'>Gestion de la sesión</Link>
    </>
  )
  
}