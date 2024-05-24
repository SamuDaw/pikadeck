import ReactDOM from 'react-dom/client'
import App from './App.jsx'
import './index.css'
import { TokenProvider } from './Context/TokenContext.jsx'



ReactDOM.createRoot(document.getElementById('root')).render(
  <TokenProvider>
    <App />
  </TokenProvider>
)
