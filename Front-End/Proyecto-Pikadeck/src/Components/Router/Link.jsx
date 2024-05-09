/* eslint-disable react/prop-types */
import { NAVIGATION_EVENTS } from "../../utils/constants.js"

function navigate(href){
  //Evita que se recargue la pagina y logramos hacer una spa
 window.history.pushState({}, '', href)
 const navigationEvent = new Event(NAVIGATION_EVENTS.pushState)
 window.dispatchEvent(navigationEvent)
}

export function Link({target, to, ...props}){
  // Esta bien hacer un handler para el evento click, pero no es necesario
  const handleClick = (event) => {
    
    
    //Comprobaciones para saber si el usuario esta tocando alguna tecla
    const isModifiedEvent = event.metaKey || event.altKey || event.ctrlKey || event.shiftKey
    const isMainEvent = event.button === 0
    const isManageableEvent = target === '_self' || target === undefined
    if(isManageableEvent && isMainEvent && !isModifiedEvent) {
      event.preventDefault() // Evita que se recargue la pagina
      navigate(to)
    }

  }

  return (
    <a onClick={handleClick} href={to} target={target} {...props}/> // El children se pasara con el resto de props asi se renderiza el titulo del link
    //Las props podrían ser por ejemplo className
  )
}