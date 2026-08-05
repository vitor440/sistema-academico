import React from 'react'
import { DataGrid, gridClasses, renderActionsCell, GridActionsCell, GridActionsCellItem } from '@mui/x-data-grid';
import { useState, useEffect } from 'react';
import { DocenteHook } from '../../hooks/DocenteHook';
import UserCards from '../UserCards';
import { FaUserTie } from "react-icons/fa";
import { FaU } from 'react-icons/fa6';
import "./PageDocentes.css"
import Info from '../../Info';
import { Box, CircularProgress, Grid, Typography } from '@mui/material';
import { useNavigate } from 'react-router-dom';

const PageDocentes = () => {
  const [docentes, setDocentes] = useState([])
  const { listar } = DocenteHook()
  const navigate = useNavigate()
  const [loading, setLoading] = useState(false)

  async function obterDocentes() {
    setLoading(true)
    try {
      const data = await listar(0, 10)
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