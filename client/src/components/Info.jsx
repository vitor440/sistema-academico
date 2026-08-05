import React from 'react'
import "./Info.css"
import { FaBook } from "react-icons/fa";

const Info = ({Icone, titulo, content, cor}) => {
  return (
    <div className='info'>
        <div className="icon">
            <Icone fontSize="40px" color={cor}/>
        </div>
        <div className="text-content">
            <h3>{titulo}</h3>
            <h2 id='number'>{content}</h2>
        </div>
        
    </div>
  )
}

export default Info