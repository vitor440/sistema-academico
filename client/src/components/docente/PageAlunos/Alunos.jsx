import Box from '@mui/material/Box'
import Paper from '@mui/material/Paper'
import Typography from '@mui/material/Typography'
import React, { useContext, useEffect, useState } from 'react'
import api from '../../../services/api'
import Grid from '@mui/material/Grid'
import TextField from '@mui/material/TextField'
import { DataGrid } from '@mui/x-data-grid'
import Button from '@mui/material/Button'
import { GlobalContext } from '../../../context/GlobalContext'
import MenuItem from '@mui/material/MenuItem'
import CustomTable from '../../CustomTable'

const Alunos = () => {

    const [alunos, setAlunos] = useState([])
    const [disciplinas, setDisciplinas] = useState([])
    const [totalAlunos, setTotalAlunos] = useState(0)
    const [alunoInput, setAlunoInput] = useState("")
    const [disciplinaId, setDisciplinaId] = useState("")
    const [loading, setLoading] = useState(false)
    const { ano, setAno, periodo, setPeriodo } = useContext(GlobalContext)
    const [paginationModel, setPaginationModel] = useState({
            page: 0,
            pageSize: 20
          })
    console.log(alunos)
    async function getAlunos() {
        setLoading(true)
        try {


            const response = await api.get("/matriculas", {
                params: {
                    pagina: paginationModel.page,
                    tamanho: paginationModel.pageSize,
                    nomeAluno: alunoInput,
                    disciplinaId: disciplinaId,
                    semestre: periodo,
                    ano: ano
                }
            })
            setTotalAlunos(response.data.totalElements)
            const data = response.data.content
            setAlunos(data)
        } catch (error) {

        }
        setLoading(false)
    }

    async function getDisciplinas() {
        setLoading(true)
        try {


            const response = await api.get("/disciplinas", {
                params: {
                    docenteId: localStorage.getItem("docenteId"),
                }
            })
            const data = response.data.content
            setDisciplinas(data)
        } catch (error) {

        }
        setLoading(false)
    }



    useEffect(() => {
        getDisciplinas()
        getAlunos()
    }, [alunoInput, paginationModel, disciplinaId])

    function handleClick() {
        getDisciplinas()
        getAlunos()
    }

    const columns = [
        { field: 'matricula', headerName: 'Matrícula', flex: 1 },
        { field: 'nomeAluno', headerName: 'Nome', flex: 1 },
        { field: 'disciplina', headerName: 'Disciplina', flex: 1 }
    ];

    return (
        <Box>
            <Box sx={{ display: 'flex', justifyContent: 'space-between', mb: 2 }}>
                <Typography variant='h5' gutterBottom={true}>Alunos</Typography>
                <Box>
                    <TextField label="periodo" select defaultValue={periodo} size='small' sx={{ width: 80 }} onChange={(e) => setPeriodo(e.target.value)}>
                        <MenuItem key={1} value={1}>1</MenuItem>
                        <MenuItem key={2} value={2}>2</MenuItem>
                    </TextField>
                    <TextField label='ano' defaultValue={ano} onChange={(e) => setAno(e.target.value)} size='small' sx={{ ml: 1 }} />
                    <Button size='medium' sx={{ ml: 1 }} variant='outlined' onClick={handleClick}>Filtrar</Button>
                </Box>
            </Box>
            <Paper variant='outlined' sx={{ mb: 2, p: 3 }}>
                <Grid container direction="row" spacing={3} sx={{ alignItems: "center" }}>
                    <Grid>
                        <TextField label="nome" size='small' value={alunoInput} onChange={(e) => setAlunoInput(e.target.value)} />
                    </Grid>
                    <Grid>
                        <TextField label="disciplina" size='small' value={disciplinaId} onChange={(e) => setDisciplinaId(e.target.value)} select sx={{width:280}}>
                            <MenuItem key={0} value=''>Todos</MenuItem>
                            {disciplinas.map(disciplina => {
                                return <MenuItem key={disciplina.id} value={disciplina.id}>{disciplina.nome}</MenuItem>
                            })}
                        </TextField>
                    </Grid>
                </Grid>
            </Paper>
            <Paper sx={{ p: 2 }} variant='outlined'>
                <CustomTable columns={columns} rows={alunos} paginationModel={paginationModel} setPaginationModel={setPaginationModel} loading={loading} total={totalAlunos}/>

            </Paper>
        </Box>
    )
}

export default Alunos