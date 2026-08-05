import Box from '@mui/material/Box'
import Grid from '@mui/material/Grid'
import Typography from '@mui/material/Typography'
import React, { useEffect, useState } from 'react'
import DisciplinasCard from '../DisciplinasCard'
import api from '../../../services/api'
import { useNavigate } from 'react-router-dom'
import { CircularProgress } from '@mui/material'

const Turmas = () => {

    const [disciplinas, setDisciplinas] = useState([])
    const [loading, setLoading] = useState(false)
    const navigate = useNavigate()

    async function getDisciplinas() {
        setLoading(true)
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
        setLoading(false)
    }

    useEffect(() => {
        getDisciplinas()
    }, [])

    if (loading) {
        return <CircularProgress />
    }

    return (
        <Box>
            <Typography variant='h5' sx={{ mb: 2 }}>Turmas</Typography>
            <Grid container direction="column" spacing={2}>
                <Grid container direction="row" spacing={2}>

                    {disciplinas.length ? disciplinas.map(disciplina => (
                        <DisciplinasCard disciplina={disciplina} buttonText={"Ver Detalhes"} funcao={() => navigate(`/docentes/turmas/${disciplina.id}`)} />
                    )) : <Typography variant='body1'>Nenhuma turma encontrada</Typography>}

                </Grid>
            </Grid>
        </Box>
    )
}

export default Turmas