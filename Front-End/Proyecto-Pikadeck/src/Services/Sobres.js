import { getToken } from './Token.js'
import { API_URL } from '../utils/constants.js'

export const getSobres = () => {
  const token = getToken()
  return fetch(`${API_URL}/api/sobres/`,{ 
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

export const getImagenSobre = (nombreImagen) => {
  const token = getToken()
  return fetch(`${API_URL}/api/sobres/imagen/${nombreImagen}`, {
    headers:
    {
        'Authorization': 'Bearer ' + token
    }
  })
  .then(res => res.blob())
  .then(data => {
    const imgUrl = URL.createObjectURL(data);
    return imgUrl
  })
}

export const getInventarioSobres = () =>{
  const token = getToken()
  return fetch(`${API_URL}/api/sobres/inventarioSobres`, {
    headers: {
      'Authorization': 'Bearer ' + token
    }
  })
  .then(res => res.json())
  .then(data => {
    return data
  })
}

export const getSobre = (sobreId) => {
  const token = getToken()
  return fetch(`${API_URL}/api/sobres/${sobreId}`, {
    headers: {
      'Authorization': 'Bearer ' + token 
    }
  })
  .then(res => res.json())
  .then(data => {
    return data
  })
}

export const abrirSobre = (sobreId) => {
  const token = getToken()
  return fetch(`${API_URL}/api/sobres/abrir/${sobreId}`, {
    headers: {
      'Authorization': 'Bearer ' + token
    }
  })
  .then(res => res.json())
  .then(data => {
    return data
  })
}

export const crearSobre = (sobre) => { 
  const token = getToken()
  return fetch(`${API_URL}/api/sobres/`, {
    method: 'POST',
    headers: {
      'Authorization': 'Bearer ' + token
    },
    body: sobre
  })
  .then(res => {return res.status})

}