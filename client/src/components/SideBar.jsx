import React, { useContext } from 'react'
import { FaCalendarAlt, FaRegClock, FaRegCheckCircle, FaTasks } from "react-icons/fa";
import { LuNotebookPen } from "react-icons/lu";
import { MdOutlineEventNote } from "react-icons/md";
import { FaFileCircleCheck } from "react-icons/fa6";
import { IoMdHome } from "react-icons/io";
import './SideBar.css'
import { Link } from 'react-router-dom';
import Drawer from '@mui/material/Drawer';
import List from '@mui/material/List';
import ListItem from '@mui/material/ListItem';
import ListItemButton from '@mui/material/ListItemButton';
import ListItemIcon from '@mui/material/ListItemIcon';
import ListItemText from '@mui/material/ListItemText';
import HomeIcon from '@mui/icons-material/Home';
import { GlobalContext } from '../context/GlobalContext';
import { useNavigate } from 'react-router-dom';

const SideBar = () => {

  const {openDrawer, setOpenDrawer} = useContext(GlobalContext)
  const navigate = useNavigate()

  return (

    <Drawer variant='persistent' open={openDrawer}  sx={{"& .MuiDrawer-paper": {
      width:"240px",
      top:"64px"
    }}}>
        <List>
          <ListItem>
            <ListItemButton onClick={() => navigate("/alunos")}>
              <ListItemIcon><HomeIcon/></ListItemIcon>
              <ListItemText primary="Home"/>
            </ListItemButton>
          </ListItem>
          <ListItem>
            <ListItemButton  onClick={() => navigate("/alunos/tarefas")}>
              <ListItemIcon><FaTasks color='#fff'/></ListItemIcon>
              <ListItemText primary="Tarefas"/>
            </ListItemButton>
          </ListItem>
          <ListItem>
            <ListItemButton onClick={() => navigate("/alunos/exames")}>
              <ListItemIcon><FaCalendarAlt color='#fff'/></ListItemIcon>
              <ListItemText primary="Provas Marcadas"/>
            </ListItemButton>
          </ListItem>
          <ListItem>
            <ListItemButton onClick={() => navigate("/alunos/notasEFrequencia")}>
              <ListItemIcon><LuNotebookPen color='#fff'/></ListItemIcon>
              <ListItemText primary="Notas e Frequências"/>
            </ListItemButton>
          </ListItem>
          <ListItem>
            <ListItemButton onClick={() => navigate("/alunos/horarios")}>
              <ListItemIcon><FaRegClock color='#fff'/></ListItemIcon>
              <ListItemText primary="Horários"/>
            </ListItemButton>
          </ListItem>
          <ListItem>
            <ListItemButton onClick={() => navigate("/alunos/resultados")}>
              <ListItemIcon><FaFileCircleCheck color='#fff'/></ListItemIcon>
              <ListItemText primary="Resultados"/>
            </ListItemButton>
          </ListItem>
          <ListItem>
            <ListItemButton onClick={() => navigate("/alunos/matriculas")}>
              <ListItemIcon><MdOutlineEventNote color='#fff'/></ListItemIcon>
              <ListItemText primary="Solicitação de matrícula"/>
            </ListItemButton>
          </ListItem>
          <ListItem>
            <ListItemButton onClick={() => navigate("/alunos/perfil")}>
              <ListItemIcon><MdOutlineEventNote color='#fff'/></ListItemIcon>
              <ListItemText primary="Perfil"/>
            </ListItemButton>
          </ListItem>
        </List>
    </Drawer>
  )
}

export default SideBar