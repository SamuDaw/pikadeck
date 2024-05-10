import { useId } from "react"
import { loginUser } from "../../Services/Auth"
import { setToken } from "../../Services/Token"
import { useState } from "react"

export function Login() {
  const [loading, setLoading] = useState(false) // Añadimos un estado para el loading
  const userInputId = useId()
  const passwordInputId = useId()
  const handleSubmit = async (event) => {
    event.preventDefault()
    setLoading(true)

    console.log(loading)

    const formData = new FormData(event.target)
    console.log(formData)
    console.log(formData.get('username'), formData.get('password')) // Con el get cogemos el dato haciendo referencia al name del input

    const infoUser = {
      username: formData.get('username'),
      password: formData.get('password')
    }
    const json = JSON.stringify(infoUser)
    console.log(json)
    let token = null
    try {
      token = await loginUser(json)
    }
    catch(e){
      console.log(e)
    }

    setLoading(false)
    setToken(token.token);

    console.log(token)
  }
  return (
    <>
      <form onSubmit={handleSubmit}>
        <label htmlFor={userInputId}>Nombre de usuario</label>
        <input id={userInputId} type="text" name="username" placeholder="Pon tu nombre de usuario..." disabled={loading}/>
        <label htmlFor={passwordInputId}>Contraseña</label>
        <input id={passwordInputId} type="password" name="password" placeholder="Pon tu contraseña..." disabled={loading} />
        <button type="submit" disabled={loading}>Login</button>
      </form>
      {loading && <p>Loading...</p>}
    </>
  )
}