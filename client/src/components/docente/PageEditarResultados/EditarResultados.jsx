import Box from '@mui/material/Box'
import Grid from '@mui/material/Grid'
import Typography from '@mui/material/Typography'
import React, { useEffect, useState } from 'react'
import DisciplinasCard from '../DisciplinasCard'
import CircularProgress from '@mui/material/CircularProgress'
import api from '../../../services/api'
import { useNavigate } from 'react-router-dom'

const EditarResultados = () => {

  const navigate = useNavigate()
  const [disciplinas, setDisciplinas] = useState([])

  async function getDisciplinas() {
    try {
      const response = await api.get("/disciplinas", {
        params: {
          docenteId: localStorage.getItem("docenteId")
        }
      })

      const data = response.data
      setDisciplinas(data.content)
    } catch (error) {

    }
  }

  

  useEffect(() => {
    getDisciplinas()
  }, [])

  return (
    <Box>
      <Typography variant='h5' sx={{ mb: 2 }}>Editar notas de exames</Typography>
      <Grid container direction="column" spacing={2}>
        <Grid container direction="row" spacing={2}>

          {disciplinas.length ? disciplinas.map(disciplina => (
            <DisciplinasCard disciplina={disciplina} buttonText={"Editar Resultados"} funcao={() => navigate(`/docentes/editarResultados/${disciplina.id}/exames`)} />
          )) : <CircularProgress />}

        </Grid>
      </Grid>
    </Box>
  )
}

export default EditarResultados