import { Box, CircularProgress, Grid, Typography } from '@mui/material';
import { useEffect, useState } from 'react';
import { FaUserTie } from "react-icons/fa";
import { useNavigate } from 'react-router-dom';
import api from '../../../services/api';
import UserCards from '../UserCards';

const PageDocentes = () => {
  const [docentes, setDocentes] = useState([])
  const navigate = useNavigate()
  const [loading, setLoading] = useState(false)

  async function obterDocentes() {
    setLoading(true)
    try {
      // const data = await listar(0, 10)
      const data = await api.get("/docentes")
      setDocentes(data.content)
    } catch (error) {
    }
    setLoading(false)
  }

  useEffect(() => {
    obterDocentes()
  }, [])


  if (loading) {
    return (
      <Box sx={{ display: 'flex', justifyContent: 'center', alignItems: "center" }}>
        <CircularProgress />
      </Box>
    )
  }

  if(!loading && docentes.length === 0) {
    return (
      <Box>
        <Typography>Nenhum docente encontrado.</Typography>
      </Box>
    )
      
    }

  return (
    <Box>
      <Typography variant='h5' sx={{ mb: 2 }}>Docentes</Typography>
      <Grid container direction='column' spacing={2}>
        <Grid container direction='row'>
          {docentes ? docentes.map(d => {
            return <UserCards Icone={FaUserTie} data={d} role={"DOCENTE"} cor={"#017aeb"} obterDados={obterDocentes} />
          }) : <p>Carregando....</p>}
        </Grid>
      </Grid>
    </Box>
  )
}

export default PageDocentes