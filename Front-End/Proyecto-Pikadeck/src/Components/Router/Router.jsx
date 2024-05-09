/* eslint-disable react/prop-types */
import { useState, useEffect, Children } from 'react';
import { NAVIGATION_EVENTS } from '../../utils/constants.js'
import { match } from 'path-to-regexp' 


export function Router ({ children, defaultComponent: DefaultComponent = () => null }){
  const [location, setLocation] = useState(window.location.pathname)
  console.log(children)
  useEffect(() => {
    console.log('useEffect')
    const onLocationChange = () => {
      setLocation(window.location.pathname) //Esto va a ser que se vuelva a renderizar el componente, por lo que cambiara el contenido en función al estado. esto se ejecutara al cambiar la url
    }
    window.addEventListener(NAVIGATION_EVENTS.pushState, onLocationChange)
    window.addEventListener(NAVIGATION_EVENTS.popstate, onLocationChange)
    return () => {
      window.removeEventListener(NAVIGATION_EVENTS.popstate, onLocationChange)
      window.removeEventListener(NAVIGATION_EVENTS.pushState, onLocationChange)
    }
  }, [])

  let routeParams = {}
  // Agregar las rutas desde el componente Route
  const routesFromChildren = Children.toArray(children).map(child => { // Con el Children importado de React podemos iterar los children
    const { props, type } = child
    const { name } = type
    const isRoute = name === 'Route' // Comprobamos si el tipo es Route
    
    if(!isRoute) return null // Si no es un Route no hacemos nada

    return props
  })
  console.log(routesFromChildren)

  const Page = routesFromChildren.find(route => 
    {
      console.log(route)
      const { path } = route
      console.log("Comprueba que sea la misma ruta")
      if(path === location) return true
      console.log("Esto sigue")
      const matchFunction = match(path, {decode: decodeURIComponent})
      const matchResult = matchFunction(location)
      if(matchResult) {
        routeParams = matchResult.params // Muy bien, pero que es matchResult.params? El resultado de haber hecho match con la url, es decir, si la url es /pruebas/1 y el path es /pruebas/:id, el resultado de hacer match es {id: 1} y eso es lo que se guarda en routeParams. El nombre del atributo es el mismo que el que pongas en la ruta como se ve en ese ejemplo :)
        return true
      }
      return false

    })?.Component || DefaultComponent // Teniendo en cuenta que routes es un array usamos un find. En el caso de que un path guardado del array coincida con el que pasamos se renderizará el componente asociado ya que find devuelve el objeto, en caso de que sea null se mostrará la página por defecto
  console.log(Page)
  return Page 
  ? <Page routeParams={routeParams}/> 
  : <DefaultComponent /> // Si Page es true se renderiza el componente, si no se renderiza el componente por defecto. Lo que tenemos que poner en el page no es mas que este mismo router como el componente que es
}