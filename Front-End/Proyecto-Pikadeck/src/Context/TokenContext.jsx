import { createContext, useState } from "react";
import { getToken, setTokenJS } from "../Services/Token";


const TokenContext = createContext();

const TokenProvider = ({children}) => {
  const [token, setToken] = useState(getToken())

  const inicioSesion = (token) => {
    setToken(token)
    setTokenJS(token)
  }

  return (
    <TokenContext.Provider value={{token, inicioSesion}}>
      {children}
    </TokenContext.Provider>
  )
}
export {TokenContext, TokenProvider}