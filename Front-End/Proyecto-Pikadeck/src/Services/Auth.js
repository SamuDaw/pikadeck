import { API_URL } from "../utils/constants.js"

// Se podria pasar por parametro si fuera login o register para usar la misma función para login y register
export const loginUser = (formData) => {
  return fetch(`${API_URL}/auth/login`, {method: "POST", body: formData})
  .then(res => res.json())
  .then(data => {
    return data
  })
}