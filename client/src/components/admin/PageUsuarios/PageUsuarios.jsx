import { Box, CircularProgress, Grid, Typography } from '@mui/material';
import { useEffect, useState } from 'react';
import { FaUserTie } from "react-icons/fa";
import { useNavigate } from 'react-router-dom';
import api from '../../../services/api';
import { UsuarioHook } from '../../hooks/UsuarioHook';
import UserCards from '../UserCards';

const PageUsuarios = () => {
  const [usuarios, setUsuarios] = useState([])
  const [loading, setLoading] = useState(false)
  const { listarUsuarios } = UsuarioHook()
  const navigate = useNavigate()


  async function obterUsuarios() {
    setLoading(true)
    try {
      const response = await api.get("/usuarios", {
        params: {
          role: 'ADMIN'
        }
      })

      const data = response.data.content
      setUsuarios(data)
    } catch (error) {
    }
    setLoading(false)
  }

  useEffect(() => {

    obterUsuarios()
  }, [])


  if (loading) {
    return (
      <Box sx={{ display: 'flex', justifyContent: 'center', alignItems: "center" }}>
        <CircularProgress />
      </Box>
    )
  }

  if (!loading && usuarios.length === 0) {
    return (
      <Box>
        <Typography>Nenhum usuário encontrado.</Typography>
      </Box>

    )

  }

  return (

    <Box>
      <Typography variant='h5' sx={{ mb: 2 }}>Admin Cadastrados</Typography>
      <Grid container direction='column' spacing={2}>
        <Grid container direction='row'>
          {usuarios ? usuarios.map(d => {
            return <UserCards Icone={FaUserTie} data={d} role={"USUARIO"} cor={"#017aeb"} obterDados={obterUsuarios}/>
          }) : <p>Carregando....</p>}
        </Grid>
      </Grid>
    </Box>
  )
}

export default PageUsuarios