import React, { useContext } from 'react'
import SideBar from './SideBar'
import MainContent from './aluno/dashboard/MainContent'
import { Outlet } from 'react-router-dom'
import './Main.css'
import { useState, useEffect } from 'react'
import AdminSideBar from './AdminSideBar';
import Box from '@mui/material/Box'
import { GlobalContext } from '../context/GlobalContext'
import DocenteSideBar from './docente/DocenteSideBar'



const Main = () => {
  
  const roles = localStorage.getItem("roles")
  const {openDrawer} = useContext(GlobalContext)

  function getSideBar() {
    if(roles.includes("ALUNO")) {
      return <SideBar/>
    }
    else if(roles.includes("ADMIN")) {
      return <AdminSideBar/>
    }
    else {
      return <DocenteSideBar/>
    }
  }

  return (
    <div>
        <Box>
            {/* <SideBar/> */}
            {getSideBar()}
            {/* <MainContent/> */}
            <Box sx={{p:3, ml: openDrawer ? "240px" : 0, flexGrow:1}}>
              <Outlet/>
            </Box>
        </Box>
    </div>
  )
}

export default Main