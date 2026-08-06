import Box from '@mui/material/Box'
import Typography from '@mui/material/Typography'
import React, { useContext, useEffect, useState } from 'react'
import { useNavigate, useParams } from 'react-router-dom'
import api from '../../../services/api'
import CircularProgress from '@mui/material/CircularProgress'
import Paper from '@mui/material/Paper'
import { DataGrid } from '@mui/x-data-grid'
import TextField from '@mui/material/TextField'
import Grid from '@mui/material/Grid'
import ArrowBackIcon from '@mui/icons-material/ArrowBack';
import IconButton from '@mui/material/IconButton'
import { GlobalContext } from '../../../context/GlobalContext'
import MenuItem from '@mui/material/MenuItem'
import Button from '@mui/material/Button'

const DetalheTurma = () => {

    const [alunos, setAlunos] = useState([])
    const { disciplinaId } = useParams()
    const [disciplina, setDisciplina] = useState("")
    const [loading, setLoading] = useState(false)
    const [alunoInput, setAlunoInput] = useState("")
    const [matriculaInput, setMatriculaInput] = useState("")
    const [loadingTable, setLoadingTable] = useState(false)
    const navigate = useNavigate()
    const { ano, setAno, periodo, setPeriodo } = useContext(GlobalContext)


    async function detalhesDisciplina() {
        setLoading(true)
        try {
        const response = await api.get(`/disciplinas/${disciplinaId}`)
        const data = response.data
        setDisciplina(data)
            
        } catch (error) {
            
        }
        setLoading(false)
    }



    async function getAlunos() {

        setLoadingTable(true)
        try {
            const response = await api.get("/matriculas", {
                params: {
                    pagina: paginationModel.page,
                    tamanho: paginationModel.pageSize,
                    nomeAluno: alunoInput,
                    disciplinaId: disciplinaId,
                    semestre: periodo,
                    statusSolicitacao: 'EFETIVADA',
                    ano: ano
                }
            })

            const data = response.data
            setAlunos(data.content)
        } catch (error) {

        }
        setLoadingTable(false)
    }

    function handleClick() {
        setLoading(true)
        getAlunos()
        setLoading(false)
    }



    const columns = [
        { field: 'nomeAluno', headerName: 'Nome', flex: 1 },
        { field: 'matricula', headerName: 'Matrícula', flex: 1 }
    ];


    const [paginationModel, setPaginationModel] = useState({
        page: 0,
        pageSize: 10
    })

    useEffect(() => {
        detalhesDisciplina()
        getAlunos()
    }, [])

    useEffect(() => {
        getAlunos()
    }, [alunoInput, matriculaInput, paginationModel])

    if (loading) {
        return <CircularProgress />
    }

    return (
        <Box>
            <IconButton sx={{ mb: 2 }} onClick={() => navigate("/docentes/turmas")}>
                <ArrowBackIcon />
            </IconButton>
            <Box sx={{ display: 'flex', justifyContent: 'space-between' }}>
                <Typography variant='h4' gutterBottom={true}>Informações gerais </Typography>
                <Box>
                    <TextField label="periodo" select defaultValue={periodo} size='small' sx={{ width: 80 }} onChange={(e) => setPeriodo(e.target.value)}>
                        <MenuItem key={1} value={1}>1</MenuItem>
                        <MenuItem key={2} value={2}>2</MenuItem>
                    </TextField>
                    <TextField label='ano' defaultValue={ano} onChange={(e) => setAno(e.target.value)} size='small' sx={{ ml: 1 }} />
                    <Button size='medium' sx={{ ml: 1 }} variant='outlined' onClick={handleClick}>Filtrar</Button>
                </Box>
            </Box>
            <Paper variant='outlined' sx={{ p: 2, mb: 2 }}>
                <Grid container direction="column" spacing={2}>
                    <Grid container direction="row" spacing={2}>
                        <Grid size={6}>
                            <Typography variant='body1'>Nome: {disciplina.nome}</Typography>
                        </Grid>
                        <Grid size={6}>
                            <Typography variant='body1'>Localização: {disciplina.localizacao}</Typography>
                        </Grid>
                    </Grid>
                    <Grid container direction="row" spacing={2}>
                        <Grid size={6}>
                            <Typography variant='body1'>alunos matriculados: {disciplina.alunosMatriculados}</Typography>
                        </Grid>
                        <Grid size={6}>
                            <Typography variant='body1'>vagas: {disciplina.vagas}</Typography>
                        </Grid>
                    </Grid>
                    <Grid container direction="row" spacing={2}>
                        <Grid size={6}>
                            <Typography variant='body1'>departamento id: {disciplina.departamentoId}</Typography>
                        </Grid>
                        <Grid size={6}>
                            <Typography variant='body1'>docente id: {disciplina.docenteId}</Typography>
                        </Grid>
                    </Grid>
                </Grid>
            </Paper>

            <Typography variant='h5' sx={{ mb: 2 }}>Alunos</Typography>
            <Paper variant='outlined' sx={{ mb: 2, p: 3 }}>
                <Grid container direction="row" spacing={3} sx={{ alignItems: "center" }}>
                    <Grid>
                        <TextField label="nome" size='small' onChange={(e) => setAlunoInput(e.target.value)} />
                    </Grid>
                </Grid>
            </Paper>

            <Paper sx={{ p: 2 }} variant='outlined'>
                <DataGrid
                    rows={alunos}
                    columns={columns}
                    style={{ color: "#fff", margin: "0 auto" }} // 1360px 
                    autoHeight={true}
                    disableRowSelectionOnClick
                    loading={loadingTable}
                    pagination
                    pageSizeOptions={[10, 15, 20]}
                    paginationModel={paginationModel}
                    onPaginationModelChange={setPaginationModel}
                    density='compact'
                    sx={{
                        '& .MuiDataGrid-cell:focus-within': {
                            outline: "none"
                        }
                    }}
                />

            </Paper>
        </Box>
    )
}

export default DetalheTurma