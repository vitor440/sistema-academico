import { Box, Button, CircularProgress, Grid, IconButton, MenuItem, TextField, Typography } from '@mui/material'
import React, { useContext, useEffect, useState } from 'react'
import ArrowBackIcon from '@mui/icons-material/ArrowBack';
import ExameCard from '../ExameCard';
import api from '../../../services/api';
import { useNavigate, useParams } from 'react-router-dom';
import { GlobalContext } from '../../../context/GlobalContext';

const EditarNotasExames = () => {
    const [exames, setExames] = useState([])
    const { ano, setAno, periodo, setPeriodo } = useContext(GlobalContext)
    const [loading, setLoading] = useState(false)
    const navigate = useNavigate()
    const { disciplinaId } = useParams("disciplinaId")

    async function getExames() {
        setLoading(true)
        try {


            const response = await api.get("/exames", {
                params: {
                    disciplinaId: disciplinaId,
                    semestre: periodo,
                    ano: ano,
                    status: "CONCLUIDO",
                }
            })

            const data = response.data
            setExames(data.content)
        } catch (error) {

        }
        setLoading(false)
    }

    useEffect(() => {
        getExames()
    }, [])

    if (loading) {
        <CircularProgress />
    }

    return (
        <Box>
            <IconButton sx={{ mb: 1 }} onClick={() => navigate("/docentes/editarResultados")}>
                <ArrowBackIcon />
            </IconButton>
            <Box sx={{ display: 'flex', justifyContent: 'space-between', mb: 2 }}>
                <Typography variant='h4' gutterBottom={true}>Exames</Typography>
                <Box>
                    <TextField label="periodo" select defaultValue={periodo} size='small' sx={{ width: 80 }} onChange={(e) => setPeriodo(e.target.value)}>
                        <MenuItem key={1} value={1}>1</MenuItem>
                        <MenuItem key={2} value={2}>2</MenuItem>
                    </TextField>
                    <TextField label='ano' defaultValue={ano} onChange={(e) => setAno(e.target.value)} size='small' sx={{ ml: 1 }} />
                    <Button size='medium' sx={{ ml: 1 }} variant='outlined' >Filtrar</Button>
                </Box>
            </Box>
            <Grid container direction="column" spacing={2}>
                <Grid container direction="row" spacing={2}>

                    {exames.length ? exames?.map(exame => (
                        <ExameCard exame={exame} link={`/docentes/editarResultados/${exame.disciplinaId}/exames/${exame.id}`} />
                    )) : <Typography variant='body1'>Nenhum exame agendado</Typography>}

                </Grid>
            </Grid>

        </Box>
    )
}

export default EditarNotasExames