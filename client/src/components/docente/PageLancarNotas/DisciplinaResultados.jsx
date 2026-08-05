import Box from '@mui/material/Box'
import Grid from '@mui/material/Grid'
import Typography from '@mui/material/Typography'
import React, { useEffect, useState } from 'react'
import api from '../../../services/api'
import CircularProgress from '@mui/material/CircularProgress'
import DisciplinasCard from '../DisciplinasCard'
import { useNavigate } from 'react-router-dom'

const DisciplinaResultados = () => {

    const [disciplinas, setDisciplinas] = useState([])
    const [loading, setLoading] = useState(false)
    const [docente, setDocente] = useState("")
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
            <Typography variant='h5' sx={{ mb: 1 }}>Lançar Notas</Typography>
            <Grid container direction="column" spacing={2}>
                <Grid container direction="row" spacing={2}>

                    {disciplinas.length ? disciplinas?.map(disciplina => (
                        <DisciplinasCard disciplina={disciplina} buttonText={"Lançar Notas"} funcao={() => navigate(`/docentes/turmasNotas/${disciplina.id}/exames`)} />
                    )) : <Typography variant='body1'>Nenhum disciplina encontrada</Typography>}

                </Grid>
            </Grid>
        </Box>
    )
}

export default DisciplinaResultados