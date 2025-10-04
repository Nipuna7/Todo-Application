import { useState } from 'react'
import Todo from './components/Home.jsx'
import './App.css'
import Home from './components/Home.jsx'

function App() {
  const [count, setCount] = useState(0)

  return (
    <>
      <Home />
      
    </>
  )
}

export default App
