import './Footer.css'
export function Footer() {
    return (
        <footer>
            <div className='footer'>
                    <div className='footer-logo'>
                        <img src="src\assets\pokemon-logo.svg" alt="" className='logo-img'/>
                    </div>
                    <div className='footer-info'>
                        <p>Desarrollado por:</p>
                        <p>Abraham Lorenzo Guerra</p>
                        <p>Samuel Alvarado Yanes</p>
                    </div>
            </div>
            <div className="reservado">
                <p>© 2024 Pokémon. TM, ® Nintendo. Derechos reservados a NINTENDO</p>
            </div>
        </footer>
    )
}