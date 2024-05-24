export function getToken() {
  return localStorage.getItem('token')
}
export function setTokenJS(token) {
  localStorage.setItem('token', token)
}