import Box from '@mui/material/Box'
import { useContext } from 'react'
import { Outlet } from 'react-router-dom'
import { GlobalContext } from '../context/GlobalContext'
import AdminSideBar from './AdminSideBar'
import SideBar from './SideBar'
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