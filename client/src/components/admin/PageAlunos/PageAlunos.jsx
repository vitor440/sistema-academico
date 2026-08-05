import React from 'react'
import { useState, useEffect } from 'react'
import { AlunoHooks } from '../../hooks/AlunoHooks'
import { PiStudentBold } from "react-icons/pi";
import UserCards from '../UserCards';
import "./PageAlunos.css"
import { Box, CircularProgress, Grid, Typography } from '@mui/material';

const PageAlunos = () => {
  const [alunos, setAlunos] = useState([])
  const {listarAlunos} = AlunoHooks()
  const [nome, setNome] = useState("")
  const [loading, setLoading] = useState(false)


  async function obterAlunos() {
      setLoading(true)
      try {
      const data = await listarAlunos(0, 10, nome)
      setAlunos(data.content)
      } catch (error) {
        
      }
      setLoading(false)
  }

  useEffect(() => {
    obterAlunos()
  }, [nome])

  if(loading) {
    return(
          <Box sx={{display:'flex', justifyContent:'center', alignItems:"center"}}>
            <CircularProgress/>
          </Box>
    )
  }

  if(!loading && alunos.length === 0) {
    return (
      <Box>
      <Typography>Nenhum aluno encontrado.</Typography>
    </Box>
    )
    
  }
  return (

    <Box>
    <Typography variant='h5' sx={{mb:2}}>Alunos</Typography>
    <Grid container direction='column' spacing={2}>
      <Grid container direction='row'>
        { alunos ? alunos.map(d => {
         return <UserCards Icone={PiStudentBold} data={d} role={"ALUNO"} cor={"#017aeb"} obterDados={obterAlunos}/>
       }): <p>Carregando....</p>}
      </Grid>
    </Grid>
  </Box>
  )
  
}

export default PageAlunos