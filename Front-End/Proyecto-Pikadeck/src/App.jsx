import './App.css'
import Landing from './pages/Landing.jsx'
import Pruebas from './pages/Pruebas.jsx'
import Inventario from './pages/Inventario.jsx'
import { BrowserRouter, Route, Routes, Link } from 'react-router-dom'
import { Login } from './Components/Auth/Login.jsx'
import { Register } from './Components/Auth/Register.jsx'
import { Shop } from './pages/Shop.jsx'
import { Carrito } from './pages/Carrito.jsx'
import { Details as DetailsSobre } from './Components/Sobres/Details.jsx'
import { Details as DetailsCarta } from './Components/Cartas/Details.jsx'
import { Admin } from './pages/Admin.jsx'
import { jwtDecode } from 'jwt-decode'
import { NoPermisos } from './pages/NoPermisos.jsx'
import { Header } from './Components/commons/Header.jsx'
import { Footer } from './Components/commons/Footer.jsx'
import { useContext } from 'react'
import { TokenContext } from './Context/TokenContext.jsx'



function App() {
  // En router se renderiza la aplicación
  const { token } = useContext(TokenContext)
  const tokenDecoded = token ? jwtDecode(token) : null
  return (
    <div className='app-container'>
      <BrowserRouter>
        <Header/>
        <main>
          <Routes >
            <Route path='/' element={<Landing />} errorElement={() => <h1>Error</h1>}/>
            <Route path='/pruebas' element={<Pruebas />} />
            <Route path='/login' element={<Login />} />
            <Route path='/register' element={<Register />} />
            <Route path='/pruebas/:id' element={<Pruebas />} />
            <Route path='/shop' element={<Shop />} />
            <Route path='/carrito' element={tokenDecoded && tokenDecoded.roles.find(token => token === 'ROLE_ADMIN') === 'ROLE_ADMIN' ? <NoPermisos/> : <Carrito />}/>
            <Route path='/inventario' element={tokenDecoded && tokenDecoded.roles.find(token => token === 'ROLE_ADMIN') === 'ROLE_ADMIN' ? <NoPermisos/> : <Inventario />}/>
            <Route path ='/shop/:sobreId' element={<DetailsSobre />} />
            <Route path ='/cartas/:cartaId' element={<DetailsCarta />} />
            <Route path='/admin' element={tokenDecoded && tokenDecoded.roles.find(token => token === 'ROLE_ADMIN') === 'ROLE_ADMIN' ? <Admin/> : <NoPermisos />}/>
            <Route path='/logout' element={<h1>Sesión cerrada</h1>}></Route>
          </Routes>
        </main>
        <Footer/>
      </BrowserRouter>
    </div>

  )
}

export default App
