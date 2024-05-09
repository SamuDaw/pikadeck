import './App.css'
import Landing from './pages/Landing.jsx'
import Pruebas from './pages/Pruebas.jsx'
import { Router } from './Components/Router/Router.jsx'
import Route from './Components/Router/Route.jsx'
import { Page404 } from './Components/Errors/Page404.jsx'




function App() {
  // En router se renderiza la aplicación
  return (
    <main>
      <Router defaultComponent={Page404}> 
        <Route path='/' Component={Landing}/>
        <Route path='/pruebas' Component={Pruebas}/>
        <Route path='/pruebas/:id' Component={Pruebas}/>
      </Router>
    </main>
  )
}

export default App
