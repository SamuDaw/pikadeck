import React from 'react'
import ReactDOM from 'react-dom/client'
import App from './App.jsx'
import Root from "./routes/root";
import './index.css'



ReactDOM.createRoot(document.getElementById('root')).render(
  <React.StrictMode>
    <App />
  </React.StrictMode>,
)
