import { Link } from 'react-router-dom'
import { jwtDecode } from "jwt-decode"
import { getToken } from '../Services/Token'
import { useContext, useEffect, useState } from 'react'
import { Header } from '../Components/commons/Header.jsx'
import './Landing.css'
import Sparticles from 'sparticles'
import { TokenContext } from '../Context/TokenContext.jsx'

export default function Landing() {

  const [tokenDecoded, setTokenDecoded] = useState()

  const {token} = useContext(TokenContext)

  useEffect(() => {
    const tokenDecoded = token ? jwtDecode(token) : null
    setTokenDecoded(tokenDecoded)
  }, [token])
  
  let s = new Sparticles(document.getElementById('landingContent'),{
    minSize: 20,
    maxSize: 50,
    count: 200,
    minAlpha: 1,
    maxAlpha: 1,
    drift: 0,
    direction: 140,
    rotation: 2,
    xVariance: 10,
    parallax: 2,
    speed: 1,
    shape: ["image", "circle"],
    imageUrl: [
      "https://img.pokemondb.net/sprites/items/poke-ball.png",
      "https://img.pokemondb.net/sprites/items/poke-ball.png",
      "https://img.pokemondb.net/sprites/items/poke-ball.png",
      "https://img.pokemondb.net/sprites/items/poke-ball.png",
      "https://img.pokemondb.net/sprites/items/poke-ball.png",
      "https://img.pokemondb.net/sprites/items/poke-ball.png",
      "https://img.pokemondb.net/sprites/items/poke-ball.png",
      "https://img.pokemondb.net/sprites/items/poke-ball.png",
      "https://img.pokemondb.net/sprites/items/poke-ball.png",
      "https://img.pokemondb.net/sprites/items/poke-ball.png",
      "https://img.pokemondb.net/sprites/items/great-ball.png",
      "https://img.pokemondb.net/sprites/items/ultra-ball.png",
      "https://img.pokemondb.net/sprites/items/master-ball.png",
      "https://img.pokemondb.net/sprites/items/friend-ball.png",
      "https://img.pokemondb.net/sprites/items/fast-ball.png",
      "https://img.pokemondb.net/sprites/items/dream-ball.png",
      "https://img.pokemondb.net/sprites/items/dive-ball.png",
      "https://img.pokemondb.net/sprites/items/level-ball.png",
      "https://img.pokemondb.net/sprites/items/lure-ball.png",
      "https://img.pokemondb.net/sprites/items/love-ball.png",
      "https://img.pokemondb.net/sprites/items/moon-ball.png",
      "https://img.pokemondb.net/sprites/items/nest-ball.png",
      "https://img.pokemondb.net/sprites/items/net-ball.png",
      "https://img.pokemondb.net/sprites/items/park-ball.png",
      "https://img.pokemondb.net/sprites/items/premier-ball.png",
      "https://img.pokemondb.net/sprites/items/quick-ball.png",
      "https://img.pokemondb.net/sprites/items/safari-ball.png",
      "https://img.pokemondb.net/sprites/items/sport-ball.png",
      "https://img.pokemondb.net/sprites/items/timer-ball.png",
    ],
    color: ["transparent"]
  });
  
  return(
    <>
      <div className='landingContent' id='landingContent'>
        <div className='presentation'>
          <div className='presentation-title'>
            <h2>¿Estás preparado?</h2>
            <h2>Hazte con todos</h2>
            <div>
              {tokenDecoded &&
                <Link to='/shop' className='enlace boton-landing'>¡Consigue tus cartas!</Link>
              }
            </div>
          </div>
          <div className='presentation-images'>
            <img src="https://m.media-amazon.com/images/I/51TxlvrsoBL._AC_.jpg" alt="" className='squirtleMalo'/>
            <img src="https://images.wikidexcdn.net/mwuploads/wikidex/9/94/latest/20230212115022/Parte_trasera_carta_de_Pok%C3%A9mon.png" alt="" className='reversoCarta'/>
            <img src="https://www.mypokecard.com/my/galery/deBNQbp8H6wt.jpg" alt="" className='pikachuSorprendido'/>
          </div>
        </div>
      </div>

    </>
  ) 
}