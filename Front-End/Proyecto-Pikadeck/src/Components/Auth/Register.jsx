import { useContext, useState } from "react"
import { authUser } from "../../Services/Auth"
import { setTokenJS } from "../../Services/Token"
import { TokenContext } from "../../Context/TokenContext"
import { Link } from "react-router-dom"

export function Register () {
  const [loading, setLoading] = useState(false)

  const { inicioSesion } =useContext(TokenContext)

  const handleSubmit = async (event) => {
    event.preventDefault()
    setLoading(true)
    const formData = new FormData(event.target)

    const infoUser = {
      username: formData.get('username'),
      password: formData.get('password'),
      useremail: formData.get('email'),
      nombreEntrenador: formData.get('trainerName')
    }

    const json = JSON.stringify(infoUser)

    let token = null
    try {
      token = await authUser(json, 'register')
    }
    catch(e){
      console.log(e)
    }
    setLoading(false)
    setTokenJS(token.token)
    inicioSesion(token.token)
  }
  return (
    <div className="formLogin-content">
      <div className="formLogin-container">
        <h1>Registrarse</h1>
        <form onSubmit={handleSubmit} className="formLogin">
          <label htmlFor="">Correo electrónico</label>
          <input type="email" name="email" id="" placeholder="Introduce tu correo electrónico" disabled={loading} className="formInput"/>
          <label htmlFor='username'>Nombre de usuario</label>
          <input id='username' type="text" name="username" placeholder="Introduce tu nombre de usuario..." disabled={loading} className="formInput"/>
          <label htmlFor="">Nombre de entrenador</label>
          <input type="text" name="trainerName" id="" placeholder="Introduce tu nombre de entrenador..." disabled={loading} className="formInput"/>
          <label htmlFor='password'>Contraseña</label>
          <input id='password' type="password" name="password" placeholder="Introduce tu contraseña..." disabled={loading} className="formInput"/>
          <button type="submit" disabled={loading} className="formSubmit">Registrarse</button>
        </form>
        <div>
          <p>¿Ya tienes una cuenta creada?</p>
          <Link to="/login" className="formLogin-enlace-register">Iniciar sesión</Link>
        </div>
        {loading && <p>Loading...</p>}
      </div>
    </div>
  )
}