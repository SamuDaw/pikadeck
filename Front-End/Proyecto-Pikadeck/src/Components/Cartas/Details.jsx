/* eslint-disable react-hooks/exhaustive-deps */
import { useState, useEffect } from 'react';
import { useParams } from 'react-router-dom';
import { Carta } from './Carta';
import { getImagenSobre, getSobre } from '../../Services/Sobres';
import { Link } from 'react-router-dom';
import { Producto } from '../Producto/producto';
import './Details.css';
import { getCarta } from '../../Services/Cartas';


export const Details = () => {
    const [cardInfo, setCardInfo] = useState(null);
    const [sobre, setSobre] = useState()
    const { cartaId } = useParams()

    useEffect(() => {
        getCarta(cartaId).then(data => {
            setCardInfo(data)
            getSobre(data.idSobre).then(dataSobre => {
                getImagenSobre(dataSobre.imagen).then(imgUrl => {
                    dataSobre.imagen = imgUrl
                    setSobre(dataSobre)
                })
            })
        })
    }, []);

    if (!cardInfo) {
        return <div>Loading...</div>;
    }

    return (
        <>
            {cardInfo && sobre &&<div>
                <div className='detailsCarta'>
                    <h1 className='title'></h1>
                    <div className='imagenCarta'>
                        <Carta id={cardInfo.idCarta} nombreImagen={cardInfo.imagen}/>
                    </div>
                    <div className='infoCarta'>
                        <h1>{cardInfo.nombre}</h1>
                        <p>{cardInfo.descripcion}</p>
                    </div>
                    <div className='sobreContenedor'>
                        <h2 className='contain'>Sobre en el que aparece</h2>
                        <Link to={`/shop/${sobre.sobreId}`}><Producto key={sobre.id} sobre={sobre}></Producto></Link>
                    </div>

                </div>
                <div className='back'>
                    <Link to="/cartas">Back to all cards</Link>
                    
                </div>
            </div> }

        </>
    );
};
export default Details;