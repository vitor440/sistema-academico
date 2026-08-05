import Box from '@mui/material/Box'
import Grid from '@mui/material/Grid'
import Paper from '@mui/material/Paper'
import TextField from '@mui/material/TextField'
import Typography from '@mui/material/Typography'
import Button from '@mui/material/Button'
import React, { useContext, useEffect, useState } from 'react'
import CustomTable from '../../CustomTable'
import { DataGrid, GridActionsCellItem, renderActionsCell, useGridApiContext, useGridApiRef } from '@mui/x-data-grid'
import api from '../../../services/api'
import Alert from '@mui/material/Alert'
import Snackbar from '@mui/material/Snackbar';
import { useParams } from 'react-router-dom'
import { useNavigate } from 'react-router-dom'
import { GlobalContext } from '../../../context/GlobalContext'
import MenuItem from '@mui/material/MenuItem'
import CustomBackDrop from '../../CustomBackDrop'
import { toast } from 'react-toastify'

const LancarFaltas = () => {

    const [alunos, setAlunos] = useState([])
    const [alunoInput, setAlunoInput] = useState("")
    const [disciplinaInput, setDisciplinaInput] = useState("")
    const [openBackdrop, setOpenBackdrop] = useState(false)
    const { ano, setAno, periodo, setPeriodo } = useContext(GlobalContext)

    const navigate = useNavigate()
    const { disciplinaId } = useParams()

    const columns = [
        { field: 'id', headerName: 'Id', flex: 1 },
        { field: 'matricula', headerName: 'Matrícula', flex: 1 },
        { field: 'nomeAluno', headerName: 'Nome', flex: 1 },
        { field: 'disciplina', headerName: 'Disciplina', flex: 1 },
        {
            field: "faltas", headerName: "Faltas", flex: 1, renderCell: (params) =>
                <TextField defaultValue={params.row.faltas} size='small' sx={{ width: "50px", height: "40px" }}
                    onChange={(e) => {
                        setAlunos((rows) => {
                            return rows.map((row) => row.id === params.row.id ? { ...row, faltas: e.target.value } : row)
                        })
                    }}
                />
        }
    ];


    const [loading, setloading] = useState(false)
    const [paginationModel, setPaginationModel] = useState({
        page: 0,
        pageSize: 10
    })

    const handleClose = (event, reason) => {
        if (reason === 'clickaway') {
            return;
        }

        

    };

    async function getAlunos() {
        setloading(true)
        try {
            const response = await api.get("/matriculas", {
                params: {
                    pagina: 0,
                    tamanho: 100,
                    nomeAluno: alunoInput ? alunoInput : "",
                    disciplinaId: disciplinaId,
                    statusSolicitacao: "EFETIVADA",
                    semestre: periodo,
                    ano: ano
                }
            })

            const data = response.data
            setAlunos(data.content)

        } catch (error) {

        }
        setloading(false)
    }


    async function atualizarFaltas() {
        setOpenBackdrop(true)
        try {
            await Promise.all(alunos.map(aluno => {
                return api.patch(`/matriculas/${aluno.id}/faltas`, null, {
                    params: {
                        faltas: aluno.faltas
                    }
                })
            }))
            toast.success("Frequências salvas com sucesso!")

            getAlunos()
        } catch (error) {

        }
        setOpenBackdrop(false)
    }

    function handleClick() {
        setloading(true)
        getAlunos()
        setloading(false)
    }

    useEffect(() => {
        getAlunos()
    }, [alunoInput, disciplinaInput])

    return (
        <Box>
            <Box sx={{ display: 'flex', justifyContent: 'space-between', mb: 2 }}>
                <Typography variant='h5' gutterBottom={true}>Exames</Typography>
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
                        <TextField label="aluno" size='small' onChange={(e) => setAlunoInput(e.target.value)} />
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
                    loading={loading}
                    pagination
                    pageSizeOptions={[10, 15, 20]}
                    onPaginationModelChange={setPaginationModel}
                    density='standard'
                    sx={{
                        '& .MuiDataGrid-cell:focus-within': {
                            outline: "none"
                        }
                    }}
                />
                <Box sx={{ p: 2, display: "flex", justifyContent: "end" }}>
                    <Box>
                        <Button variant='contained' onClick={() => navigate("/docentes/turmasFrequencia")} color='secondary'>Voltar</Button>
                        <Button variant='contained' onClick={atualizarFaltas} sx={{ ml: 2 }}>Salvar</Button>
                    </Box>
                </Box>

            </Paper>
            <CustomBackDrop open={openBackdrop} handleClose={() => setOpenBackdrop(false)} />
        </Box>
    )
}

export default LancarFaltas