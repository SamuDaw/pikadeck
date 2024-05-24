import { API_URL } from '../utils/constants'
import { getToken } from './Token.js'

export const createPedido = () => {
  const token = getToken()

  return fetch(`${API_URL}/api/pedidos/crear`, { 
    method: 'POST', 
    headers: {
      'Authorization': `Bearer ${token}`
    }
  })
  .then(res => {return res.status})
}