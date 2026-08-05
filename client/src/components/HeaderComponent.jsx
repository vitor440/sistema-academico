import React from 'react'
import './HeaderComponent.css'
import { Link } from 'react-router-dom'

const HeaderComponent = () => {
  return (
    <div>
        <div className="header">
            <h2>Campus Digital</h2>
            {/* <a href="#">Logout</a> */}
            <Link to={"/error"}>Logout</Link>
        </div>
    </div>
  )
}

export default HeaderComponent