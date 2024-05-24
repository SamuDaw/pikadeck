import { getToken } from './Token.js'
import { API_URL } from '../utils/constants.js'

export const getCartas = () => {
  const token = getToken()
  try{
    return fetch(`${API_URL}/api/cartas/`,{ 
      headers:
      {
        'Authorization': 'Bearer ' + token
      }
    })
    .then(res => res.json())
    .then(data => {
      return data
    })
  }
  catch(error)
  {
    console.log('Error en getCartas')
    console.log(error)
  }

}

export const getImagenCarta = (nombreCarta) => {
  const token = getToken()
  return fetch(`${API_URL}/api/cartas/imagen/${nombreCarta}`, {
    headers: {
      'Authorization': 'Bearer ' + token
    }
  })
  .then(res => res.blob())
  .then(data => {
    const imgUrl = URL.createObjectURL(data)
    return imgUrl
  })
}

export const getInventarioCartas = () =>{
  const token = getToken()
  return fetch(`${API_URL}/api/cartas/inventarioCartas`, {
    headers: {
      'Authorization': 'Bearer ' + token
    }
  })
  .then(res => res.json())
  .then(data => {
    return data
  })
}

export const getCarta = (cartaId) => {
  const token = getToken()
  return fetch(`${API_URL}/api/cartas/${cartaId}`, {
    headers: {
      'Authorization': 'Bearer ' + token 
    }
  })
  .then(res => res.json())
  .then(data => {
    return data
  })
}

export const crearCarta = (carta) => {
  const token = getToken()
  return fetch(`${API_URL}/api/cartas/create`, {
    method: 'POST',
    headers: {
      'Authorization': 'Bearer ' + token
    },
    body: carta
  })
  .then(res => res.status)
}