import { Link } from '../Router/Link.jsx'

export function Page404() {
  return (
    <>
      <h1>404</h1>
      <p>Se supone que no deberías estar aquí...</p>
      <img src="https://media1.tenor.com/m/-kZOB16tELEAAAAC/this-is-fine-fire.gif" alt="" />
      <br />      
      <br />
      <Link to='/'>Volver a la pagina principal</Link>
    </>
  )
}