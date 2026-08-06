import React, { useContext } from 'react'
import "./AdminSideBar.css"
import { FaCalendarAlt, FaRegClock, FaRegCheckCircle, FaTasks } from "react-icons/fa";
import { LuNotebookPen } from "react-icons/lu";
import { MdOutlineEventNote } from "react-icons/md";
import { FaFileCircleCheck } from "react-icons/fa6";
import { IoMdHome } from "react-icons/io";
import { Link, useNavigate } from 'react-router-dom';
import Drawer from '@mui/material/Drawer';
import List from '@mui/material/List';
import ListItem from '@mui/material/ListItem';
import ListItemButton from '@mui/material/ListItemButton';
import ListItemIcon from '@mui/material/ListItemIcon';
import ListItemText from '@mui/material/ListItemText';
import HomeIcon from '@mui/icons-material/Home';
import AccountCircleOutlinedIcon from '@mui/icons-material/AccountCircleOutlined';
import { GlobalContext } from '../context/GlobalContext';
import BookOutlinedIcon from '@mui/icons-material/BookOutlined';
import { FaRegBuilding } from "react-icons/fa";
import AccountBalanceOutlinedIcon from '@mui/icons-material/AccountBalanceOutlined';
import { ImUserTie } from "react-icons/im";
import { PiStudentBold } from "react-icons/pi";
import PermIdentityOutlinedIcon from '@mui/icons-material/PermIdentityOutlined';
import PersonAddOutlinedIcon from '@mui/icons-material/PersonAddOutlined';

const AdminSideBar = () => {


  const {openDrawer, setOpenDrawer} = useContext(GlobalContext)
  const navigate = useNavigate()

  return (

    <Drawer variant='persistent' open={openDrawer}  sx={{"& .MuiDrawer-paper": {
          width:"240px",
          top:"64px"
        }}}>
            <List>
              <ListItem>
                <ListItemButton onClick={() => navigate("/admin")}>
                  <ListItemIcon><HomeIcon/></ListItemIcon>
                  <ListItemText primary="Home"/>
                </ListItemButton>
              </ListItem>
              <ListItem>
                <ListItemButton  onClick={() => navigate("/admin/departamentos")}>
                  <ListItemIcon><FaRegBuilding color='#fff'/></ListItemIcon>
                  <ListItemText primary="Departamentos"/>
                </ListItemButton>
              </ListItem>
              <ListItem>
                <ListItemButton onClick={() => navigate("/admin/cursos")}>
                  <ListItemIcon><AccountBalanceOutlinedIcon color='#fff'/></ListItemIcon>
                  <ListItemText primary="Cursos"/>
                </ListItemButton>
              </ListItem>
              <ListItem>
                <ListItemButton onClick={() => navigate("/admin/docentes")}>
                  <ListItemIcon><ImUserTie color='#fff'/></ListItemIcon>
                  <ListItemText primary="Docentes"/>
                </ListItemButton>
              </ListItem>
              <ListItem>
                <ListItemButton onClick={() => navigate("/admin/alunos")}>
                  <ListItemIcon><PiStudentBold color='#fff'/></ListItemIcon>
                  <ListItemText primary="Alunos"/>
                </ListItemButton>
              </ListItem>
              <ListItem>
                <ListItemButton onClick={() => navigate("/admin/usuarios")}>
                  <ListItemIcon><PermIdentityOutlinedIcon color='#fff'/></ListItemIcon>
                  <ListItemText primary="Usuários"/>
                </ListItemButton>
              </ListItem>
              <ListItem>
                <ListItemButton onClick={() => navigate("/admin/matriculas")}>
                  <ListItemIcon><MdOutlineEventNote color='#fff'/></ListItemIcon>
                  <ListItemText primary="Matrículas"/>
                </ListItemButton>
              </ListItem>
              <ListItem>
                <ListItemButton onClick={() => navigate("/admin/disciplinas")}>
                  <ListItemIcon><BookOutlinedIcon color='#fff'/></ListItemIcon>
                  <ListItemText primary="Disciplinas"/>
                </ListItemButton>
              </ListItem>
              <ListItem>
                <ListItemButton onClick={() => navigate("/admin/criarUsuario")}>
                  <ListItemIcon><PersonAddOutlinedIcon color='#fff'/></ListItemIcon>
                  <ListItemText primary="Criar usuário"/>
                </ListItemButton>
              </ListItem>
              <ListItem>
                <ListItemButton onClick={() => navigate("/admin/perfil")}>
                  <ListItemIcon><AccountCircleOutlinedIcon color='#fff'/></ListItemIcon>
                  <ListItemText primary="Perfil"/>
                </ListItemButton>
              </ListItem>
            </List>
        </Drawer>
  )
}

export default AdminSideBar