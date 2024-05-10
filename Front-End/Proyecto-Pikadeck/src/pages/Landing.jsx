import { Link } from 'react-router-dom'



export default function Landing() {
  return(
    <>
      <h1>Bienvenido a Pikadeck</h1>
      <Link to='/pruebas'>Pruebas</Link>
      <Link to='/login'>Iniciar sesión</Link>
      <Link to='/register'>Registrarse</Link>
    </>
  )
  
}