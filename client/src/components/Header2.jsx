import AccountCircleIcon from '@mui/icons-material/AccountCircle';
import MenuIcon from '@mui/icons-material/Menu';
import AppBar from '@mui/material/AppBar';
import IconButton from '@mui/material/IconButton';
import Menu from '@mui/material/Menu';
import MenuItem from '@mui/material/MenuItem';
import Toolbar from '@mui/material/Toolbar';
import Typography from '@mui/material/Typography';
import { useContext, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { GlobalContext } from '../context/GlobalContext';

const Header2 = () => {

    const {openDrawer, setOpenDrawer} = useContext(GlobalContext)
    const [anchorEl, setAnchorEl] = useState(null)
    const open = Boolean(anchorEl)
    const navigate = useNavigate()

    const handleClick = (e) => {
        setAnchorEl(e.currentTarget)
    }

    const handleClose = () => {
        setAnchorEl(null)
    }

    function detalhesPerfil() {
        const role = localStorage.getItem("roles")
        if(role.includes("DOCENTE")) {
            return navigate("/docentes/perfil")
        }
        else if(role.includes("ALUNO")) {
            return navigate("/alunos/perfil")
        }
        else {
            return navigate("/admin/perfil")
        }
    }

  return (
    <>
    <AppBar   elevation={1} enableColorOnDark>
        <Toolbar >
            <IconButton sx={{color: "#161515"}} onClick={() => setOpenDrawer(openDrawer ? false : true)}>
                <MenuIcon/>
            </IconButton>
            <Typography variant='h5' sx={{flex:1, color:"#161515"}}>Campus Digital</Typography>
            <IconButton size='large' disableFocusRipple sx={{color: "#161515"}} onClick={handleClick}>
                <AccountCircleIcon sx={{height:"36px", width:"36px"}}/>
            </IconButton>
        </Toolbar>
    </AppBar>

    <Toolbar/>

    <Menu open={open} onClose={handleClose} anchorEl={anchorEl}>
        {/* <MenuItem onClick={() => detalhesPerfil}>Perfil</MenuItem> */}
        <MenuItem onClick={() => navigate("/logout")}>Sair</MenuItem>
    </Menu>
    </>
  )
}

export default Header2