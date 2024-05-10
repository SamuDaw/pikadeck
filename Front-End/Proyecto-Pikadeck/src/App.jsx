import './App.css'
import Landing from './pages/Landing.jsx'
import Pruebas from './pages/Pruebas.jsx'
import { BrowserRouter, Route, Routes, Link } from 'react-router-dom'
import { Login } from './Components/Auth/Login.jsx'
import { Register } from './Components/Auth/Register.jsx'



function App() {
  // En router se renderiza la aplicación
  return (
    <BrowserRouter>
      <Routes >
        <Route path='/' element={<Landing />} errorElement={() => <h1>Error</h1>}/>
        <Route path='/pruebas' element={<Pruebas />} />
        <Route path='/login' element={<Login />} />
        <Route path='/register' element={<Register />} />
        <Route path='/pruebas/:id' element={<Pruebas />} />
      </Routes>
      <Link to='/'>Volver a la home</Link>
    </BrowserRouter>
  )
}

export default App
