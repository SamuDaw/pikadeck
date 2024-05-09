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

    var token
    // await setTimeout(async () => {token = await loginUser(JSON.stringify(formData))}, 20000)
    await new Promise(resolve => setTimeout(resolve, 2000)) // Simulamos una petición que tarda 2 segundos

    setLoading(false)
    setToken(token);

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