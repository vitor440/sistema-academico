import Drawer from '@mui/material/Drawer'
import List from '@mui/material/List'
import ListItem from '@mui/material/ListItem'
import ListItemButton from '@mui/material/ListItemButton'
import ListItemIcon from '@mui/material/ListItemIcon'
import ListItemText from '@mui/material/ListItemText'
import React, { useContext } from 'react'
import HomeIcon from '@mui/icons-material/Home';
import { FaCalendarAlt, FaRegClock, FaTasks } from 'react-icons/fa'
import { LuNotebookPen } from 'react-icons/lu'
import { FaFileCircleCheck } from 'react-icons/fa6'
import { MdOutlineEventNote } from 'react-icons/md'
import { useNavigate } from 'react-router-dom'
import { GlobalContext } from '../../context/GlobalContext'

const DocenteSideBar = () => {


    
    const {openDrawer, setOpenDrawer} = useContext(GlobalContext)
    const navigate = useNavigate()

  return (
    <Drawer variant='persistent' open={openDrawer}  sx={{"& .MuiDrawer-paper": {
      width:"240px",
      top:"64px"
    }}}>
        <List>
          <ListItem>
            <ListItemButton onClick={() => navigate("/docentes")}>
              <ListItemIcon><HomeIcon/></ListItemIcon>
              <ListItemText primary="Home"/>
            </ListItemButton>
          </ListItem>
          <ListItem>
            <ListItemButton  onClick={() => navigate("/docentes/turmas")}>
              <ListItemIcon><FaTasks color='#fff'/></ListItemIcon>
              <ListItemText primary="Turmas"/>
            </ListItemButton>
          </ListItem>
          <ListItem>
            <ListItemButton onClick={() => navigate("/docentes/addExame")}>
              <ListItemIcon><FaCalendarAlt color='#fff'/></ListItemIcon>
              <ListItemText primary="Marca Exames"/>
            </ListItemButton>
          </ListItem>
          <ListItem>
            <ListItemButton onClick={() => navigate("/docentes/turmasNotas")}>
              <ListItemIcon><LuNotebookPen color='#fff'/></ListItemIcon>
              <ListItemText primary="Lançar Notas"/>
            </ListItemButton>
          </ListItem>
          <ListItem>
            <ListItemButton onClick={() => navigate("/docentes/editarResultados")}>
              <ListItemIcon><LuNotebookPen color='#fff'/></ListItemIcon>
              <ListItemText primary="Editar Notas"/>
            </ListItemButton>
          </ListItem>
          <ListItem>
            <ListItemButton onClick={() => navigate("/docentes/turmasFrequencia")}>
              <ListItemIcon><FaRegClock color='#fff'/></ListItemIcon>
              <ListItemText primary="Gerenciar Faltas"/>
            </ListItemButton>
          </ListItem>
          <ListItem>
            <ListItemButton onClick={() => navigate("/docentes/provasFinais")}>
              <ListItemIcon><FaRegClock color='#fff'/></ListItemIcon>
              <ListItemText primary="Gerenciar Provas Finais"/>
            </ListItemButton>
          </ListItem>
          <ListItem>
            <ListItemButton onClick={() => navigate("/docentes/efetivarMatriculas")}>
              <ListItemIcon><FaRegClock color='#fff'/></ListItemIcon>
              <ListItemText primary="Efetivar matriculas"/>
            </ListItemButton>
          </ListItem>
          <ListItem>
            <ListItemButton onClick={() => navigate("/docentes/horarios")}>
              <ListItemIcon><FaRegClock color='#fff'/></ListItemIcon>
              <ListItemText primary="Horários"/>
            </ListItemButton>
          </ListItem>
          <ListItem>
            <ListItemButton onClick={() => navigate("/docentes/alunos")}>
              <ListItemIcon><FaFileCircleCheck color='#fff'/></ListItemIcon>
              <ListItemText primary="Listar Alunos"/>
            </ListItemButton>
          </ListItem>
          <ListItem sx={{flex:1}}>
            <ListItemButton onClick={() => navigate("/docentes/solicitacoesMatriculas")}>
              <ListItemIcon><FaFileCircleCheck color='#fff'/></ListItemIcon>
              <ListItemText primary="Solicitações de Matrícula"/>
            </ListItemButton>
          </ListItem>
          <ListItem>
            <ListItemButton onClick={() => navigate("/docentes/perfil")}>
              <ListItemIcon><FaFileCircleCheck color='#fff'/></ListItemIcon>
              <ListItemText primary="Perfil"/>
            </ListItemButton>
          </ListItem>
        </List>
    </Drawer>
  )
}

export default DocenteSideBar