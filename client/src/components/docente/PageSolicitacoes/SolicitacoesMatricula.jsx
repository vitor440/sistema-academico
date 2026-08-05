import { Box, Button, CircularProgress, Grid, MenuItem, Paper, TextField, Typography } from '@mui/material'
import React, { useContext, useEffect, useState } from 'react'
import SchoolIcon from '@mui/icons-material/School';
import api from '../../../services/api';
import { GlobalContext } from '../../../context/GlobalContext';
import CustomBackDrop from '../../CustomBackDrop';
import { toast } from 'react-toastify';

const SolicitacoesMatricula = () => {
  
  const [matriculasPendentes, setMatriculasPendentes] = useState([])
  const {ano, setAno, periodo, setPeriodo} = useContext(GlobalContext)
  const [loading, setLoading] = useState(false)
  const [open, setOpen] = useState(false)

  async function getMatriculasPendentes() {
    setLoading(true)
    try {
        const response = await api.get("/matriculas", {
            params:{
                statusSolicitacao: 'PENDENTE',
                tamanho: 100,
                semestre: periodo,
                ano: ano
            }
        })
        const data = response.data.content
        setMatriculasPendentes(data)
    } catch (error) {
        
    }
    setLoading(false)
  }

  async function aceitarMatricula(id) {
    setOpen(true)
    try {
        await api.patch(`/matriculas/${id}/statusSolicitacao`, null, {
            params:{
                statusSolicitacao: 'EFETIVADA'
            }
        })
        toast.success("Matricula Aceita!")
        getMatriculasPendentes()
    } catch (error) {
        
    }
    setOpen(false)
  }

  async function rejeitarMatricula(id) {
    setOpen(true)
    try {
        await api.delete(`/matriculas/${id}`)
        toast.success("Matricula cancelada!")
        getMatriculasPendentes()
    } catch (error) {
        
    }
    setOpen(false)
  }

  function handleClick() {
    getMatriculasPendentes()
  }

  useEffect(() => {
    getMatriculasPendentes()
  }, [])

  if(loading) {
    <CircularProgress/>
  }
  return (
    <Box>
        <Box sx={{ display: 'flex', justifyContent: 'space-between', mb: 2 }}>
                <Typography variant='h5' gutterBottom={true}>Solicitações de matrícula</Typography>
                <Box>
                  <TextField label="periodo" select defaultValue={periodo} size='small' sx={{ width: 80 }} onChange={(e) => setPeriodo(e.target.value)}>
                    <MenuItem key={1} value={1}>1</MenuItem>
                    <MenuItem key={2} value={2}>2</MenuItem>
                  </TextField>
                  <TextField label='ano' defaultValue={ano} onChange={(e) => setAno(e.target.value)} size='small' sx={{ ml: 1 }} />
                  <Button size='medium' sx={{ ml: 1 }} variant='outlined' onClick={handleClick}>Filtrar</Button>
                </Box>
              </Box>
        <Grid container direction='column'>
            <Grid container direction='row'>
            {matriculasPendentes.map(matricula => {
                return <Grid size={3}>
                            <Paper sx={{p:2, display: 'flex', flexDirection:'column', alignItems:'center', justifyContent:'center'}} variant='outlined'>
                                <SchoolIcon sx={{height:60, width:60, mb:2, color:"#3fb566"}}/>
                                <Typography sx={{mb:2}}>{matricula.nomeAluno}</Typography>
                                <Typography sx={{mb:2}}>{matricula.disciplina}</Typography>
                                <Button variant='contained' fullWidth sx={{mb:2}} onClick={() => aceitarMatricula(matricula.id)}>Aceitar</Button>

                                <Button variant='contained' fullWidth color='error' sx={{mb:2}} onClick={() => rejeitarMatricula(matricula.id)}>Rejeitar</Button>
                            </Paper>
                        </Grid>})}
            </Grid>
        </Grid>
        <CustomBackDrop open={open} handleClose={() => setOpen(false)}/>
    </Box>
  )
}

export default SolicitacoesMatricula