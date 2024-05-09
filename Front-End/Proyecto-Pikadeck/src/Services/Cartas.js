import { getToken } from './Token.js'

export const getCards = () => {
  const token = getToken()
  return fetch("http://141.147.52.85:8080/api/cartas/",{ 
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
