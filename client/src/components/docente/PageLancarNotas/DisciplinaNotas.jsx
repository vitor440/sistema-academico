import React, { useContext, useEffect, useState } from 'react'
import api from '../../../services/api'
import Box from '@mui/material/Box'
import Typography from '@mui/material/Typography'
import Grid from '@mui/material/Grid'
import DisciplinasCard from '../DisciplinasCard'
import CircularProgress from '@mui/material/CircularProgress'
import ExameCard from '../ExameCard'
import { useNavigate, useParams } from 'react-router-dom'
import ArrowBackIcon from '@mui/icons-material/ArrowBack';
import IconButton from '@mui/material/IconButton'
import MenuItem from '@mui/material/MenuItem'
import TextField from '@mui/material/TextField'
import { GlobalContext } from '../../../context/GlobalContext'
import Button from '@mui/material/Button'

const DisciplinaNotas = () => {

    const [exames, setExames] = useState([])
    const { disciplinaId } = useParams()
    const [loading, setLoading] = useState(false)
    const navigate = useNavigate()
    const { ano, setAno, periodo, setPeriodo } = useContext(GlobalContext)

    async function getExames() {
        setLoading(true)
        try {
            const response = await api.get("/exames", {
                params: {
                    disciplinaId: disciplinaId,
                    status: "PENDENTE",
                    semestre: periodo,
                    ano: ano
                }
            })

            const data = response.data
            setExames(data.content)
        } catch (error) {

        }
        setLoading(false)
    }

    function handleClick() {
        getExames()
    }

    useEffect(() => {
        getExames()
    }, [])

    const handleClose = (event, reason) => {
        if (reason === 'clickaway') {
            return;
        }

        setOpen(false);
    };

    if (loading) {
        return <CircularProgress />
    }


    return (
        <Box>
            <IconButton sx={{ mb: 1 }} onClick={() => navigate("/docentes/turmasNotas")}>
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
                    <Button size='medium' sx={{ ml: 1 }} variant='outlined' onClick={handleClick}>Filtrar</Button>
                </Box>
            </Box>
            <Grid container direction="column" spacing={2}>
                <Grid container direction="row" spacing={2}>

                    {exames.length ? exames.map(exame => (
                        <ExameCard exame={exame} link={`/docentes/turmasNotas/${exame.disciplinaId}/exames/${exame.id}`} />
                    )) : <Typography variant='body1'>Nenhum exame pendente</Typography>}

                </Grid>
            </Grid>

        </Box>
    )
}

export default DisciplinaNotas