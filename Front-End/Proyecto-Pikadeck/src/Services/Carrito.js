import { API_URL } from "../utils/constants";
import { getToken } from "../Services/Token";

export const getCarrito = () => {
  const token = getToken()

  return fetch(`${API_URL}/api/carrito/`, {
    headers: {
      'Authorization': `Bearer ${token}`
    }
  })
  .then(res => res.json())
  .then(data => {return data})
}

export const addSobreToCarrito = (id) => {
  const token = getToken()

  return fetch(`${API_URL}/api/carrito/${id}`, {
    method: 'POST',
    headers: {
      Authorization: `Bearer ${token}`
    }
  })
  .then(res => {return res.status})
}
export const eliminarSobreCarrito = async (id) => {
  const token = getToken()

  return fetch(`${API_URL}/api/carrito/${id}`, {
    method: 'DELETE',
    headers: {
      Authorization: `Bearer ${token}`
  }})
  .then(res => res.status)
}