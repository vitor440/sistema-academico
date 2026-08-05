import React, {  useEffect, useState } from 'react'
import "./ResultadoComponent.css"
import { CiCalendarDate } from "react-icons/ci";
import { GoShieldCheck } from "react-icons/go";
import { FaRegCircleCheck } from "react-icons/fa6";
import Paper from '@mui/material/Paper';
import Stack from '@mui/material/Stack';
import Typography from '@mui/material/Typography';
import Box from '@mui/material/Box';
import Grid from '@mui/material/Grid';
import api from '../../../services/api';

const ResultadoComponent = ({resultado}) => {
  const [exame, setExame] = useState("")
  const [disciplinaNome, setDisciplinaNome] = useState("")
  console.log(resultado)
    
    async function getExame() {
        try {
            const response = await api.get(`exames/${resultado.exameId}`)
            const data = response.data
            setExame(data)

        } catch (error) {
            
        }
    }

    async function getDisiplina() {
        try {
            const response = await api.get(`matriculas/${resultado.matriculaId}`)
            const data = response.data
            setDisciplinaNome(data.disciplina)

        } catch (error) {
            
        }
    }

    
    useEffect(() => {
        getDisiplina()
        getExame()
    }, [resultado])

  return (

        

            <Grid size={3}>
                <Paper sx={{height:"190px", p:3}} variant='outlined'>
                    <Typography variant='h5' gutterBottom={true}>{disciplinaNome}</Typography>
                    <Typography variant='body1' sx={{mb:2}}>{exame.nome}</Typography>
                    <Box sx={{display:"flex"}}>
                        <Box sx={{flex:1}}>
                            <Box sx={{display:"flex", mb:3}}>
                                <CiCalendarDate size={"24px"} color='#fff'/>
                                <Typography variant='body1'>Data:</Typography>
                                <Typography variant='body1'>{exame.data}</Typography>
                            </Box>
                            <Box sx={{display:"flex", alignItems:"center"}}>
                                <GoShieldCheck size={"24px"} color='#fff'/>
                                <Typography variant='body1'>Nota:</Typography>
                                <Typography variant='body1'>{resultado.nota}</Typography>
                            </Box>
                        </Box>
                        <Box>
                            <FaRegCircleCheck color='#038d21' size={"60px"}/>
                        </Box>
                    </Box>
                </Paper>

                {/* <Grid size={3}>
                <Paper sx={{height:"190px", p:3}} variant='outlined'>
                    <Typography variant='h5' gutterBottom={true}>Engenharia de software</Typography>
                    <Typography variant='body1' sx={{mb:2}}>Prova 1 - Testes Automatizados</Typography>
                    <Box sx={{display:"flex"}}>
                        <Box sx={{flex:1}}>
                            <Box sx={{display:"flex", mb:3}}>
                                <CiCalendarDate size={"24px"} color='#fff'/>
                                <Typography variant='body1'>Data:</Typography>
                                <Typography variant='body1'>03/10/2026</Typography>
                            </Box>
                            <Box sx={{display:"flex", alignItems:"center"}}>
                                <GoShieldCheck size={"24px"} color='#fff'/>
                                <Typography variant='body1'>Nota:</Typography>
                                <Typography variant='body1'>6.7</Typography>
                            </Box>
                        </Box>
                        <Box>
                            <FaRegCircleCheck color='#038d21' size={"60px"}/>
                        </Box>
                    </Box>
                </Paper>
                </Grid> */}
            </Grid>
            
            
             

            
    
  )
}

export default ResultadoComponent