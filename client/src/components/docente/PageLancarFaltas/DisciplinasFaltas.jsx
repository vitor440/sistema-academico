import Box from '@mui/material/Box'
import Card from '@mui/material/Card'
import CardActions from '@mui/material/CardActions'
import CardContent from '@mui/material/CardContent'
import Grid from '@mui/material/Grid'
import Typography from '@mui/material/Typography'
import Button from '@mui/material/Button'
import React, { useEffect, useState } from 'react'
import Avatar from '@mui/material/Avatar'
import api from '../../../services/api'
import LinearProgress from '@mui/material/LinearProgress'
import CircularProgress from '@mui/material/CircularProgress'
import { useNavigate } from 'react-router-dom'
import DisciplinasCard from '../DisciplinasCard'

const DisciplinasFaltas = () => {

    const [disciplinas, setDisciplinas] = useState([])
    const [docente, setDocente] = useState("")
    const navigate = useNavigate()

    async function getDisciplinas() {
        const response = await api.get("/disciplinas", {
            params: {
                docenteId: localStorage.getItem("docenteId")
            }
        })

        const data = response.data
        setDisciplinas(data.content)
    }

    async function getDocente() {
        try {
            const response = await api.get("/docentes/me")
            const data = response.data
            setDocente(data)
        } catch (error) {

        }
    }

    useEffect(() => {
        getDocente()
        getDisciplinas()
    }, [])




    return (
        <Box>
            <Typography variant='h5' sx={{ mb: 2 }}>Gerenciar Faltas</Typography>
            <Grid container direction="column" spacing={2}>
                <Grid container direction="row" spacing={2}>

                    {disciplinas.length ? disciplinas.map(disciplina => (
                        <DisciplinasCard disciplina={disciplina} buttonText={"Editar Frequências"} funcao={() => navigate(`/docentes/turmasFrequencia/${disciplina.id}/faltas`)} />
                    )) : <CircularProgress />}

                </Grid>
            </Grid>
        </Box>
    )
}



export default DisciplinasFaltas