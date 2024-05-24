import { jwtDecode } from 'jwt-decode'
import { Link } from 'react-router-dom'
import './Header.css'
import { useContext } from 'react'
import { TokenContext } from '../../Context/TokenContext.jsx'


export function Header() {
  // En router se renderiza la aplicación
  const {token, inicioSesion} = useContext(TokenContext)
  const tokenDecoded = token ? jwtDecode(token) : null
  console.log(tokenDecoded)
  return (
    <div className='header'>
        <div className='logo'>
            <Link to="/"><img src="src\assets\pokemon-logo.svg" alt="" className='logo-img'/></Link>
        </div>

        {tokenDecoded && <div className='opciones'>
            <Link to='/shop' className='enlace'>Tienda</Link>
            <Link to='/carrito' className='enlace'>Carrito</Link>
            <Link to='/Inventario' className='enlace'>Inventario</Link>
            {tokenDecoded && tokenDecoded.roles.find(token => token === 'ROLE_ADMIN') && <Link to='/admin' className='enlace'>Admin</Link>}
        </div>}

        <div className='login'>
            {!token && 
              <>
                <Link to='/login' className='enlace'>Iniciar sesión</Link>
                <Link to='/register' className='enlace'>Registrarse</Link>
              </>
            }
            {token && 
            <>
              <span>Usuario: {tokenDecoded.sub}</span>
              <Link to='/logout' className='enlace' onClick={() => {inicioSesion('')}}>Cerrar sesión</Link>
            </>
            }
        </div>
    </div>

  )
}