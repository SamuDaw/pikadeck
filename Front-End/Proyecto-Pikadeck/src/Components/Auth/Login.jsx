import { useContext, useId } from "react"
import { authUser } from "../../Services/Auth"
import { setTokenJS } from "../../Services/Token"
import { useState } from "react"
import { TokenContext } from "../../Context/TokenContext"
import './Login.css'
import { Loader } from "../commons/Loader"
import { Link } from "react-router-dom"

export function Login() {
  const [loading, setLoading] = useState(false) // Añadimos un estado para el loading

  const userInputId = useId()
  const passwordInputId = useId()
  const { inicioSesion } = useContext(TokenContext)

  const handleSubmit = async (event) => {
    event.preventDefault()
    setLoading(true)

    const formData = new FormData(event.target)

    const infoUser = {
      username: formData.get('username'),
      password: formData.get('password')
    }
    const json = JSON.stringify(infoUser)

    let token = null
    try {
      token = await authUser(json, 'login')
    }
    catch(e){
      console.log(e)
    }

    setLoading(false)
    setTokenJS(token.token)
    inicioSesion(token.token)
  }
  console.log("Renderizado")
  return (
    <div className="formLogin-content">
      <div className="formLogin-container">
        <h1>Login</h1>
        <form onSubmit={handleSubmit}  className="formLogin">
          <label htmlFor={userInputId}>Nombre de usuario</label>
          <input id={userInputId} type="text" name="username" placeholder="Pon tu nombre de usuario..." disabled={loading} className="formInput"/>
          <label htmlFor={passwordInputId}>Contraseña</label>
          <input id={passwordInputId} type="password" name="password" placeholder="Pon tu contraseña..." disabled={loading} className="formInput"/>
          <button type="submit" disabled={loading} className="formSubmit">Login</button>
        </form>
        <div>
          <p>¿No tienes una cuenta creada?</p>
          <Link to="/register" className="formLogin-enlace-register">Registrarse</Link>
        </div>
      </div>
      {loading && <Loader />}
    </div>
  )
}