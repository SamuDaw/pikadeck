import { API_URL } from "../utils/constants.js"

// Se podria pasar por parametro si fuera login o register para usar la misma función para login y register
export const authUser = (json, type) => {
  return fetch(`${API_URL}/auth/${type}`, {method: "POST", headers:{'Content-Type': 'application/json'},body: json})
  .then(res => res.json())
  .then(data => {
    return data
  })
}

