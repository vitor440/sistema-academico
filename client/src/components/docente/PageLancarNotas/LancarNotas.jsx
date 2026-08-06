import Box from '@mui/material/Box'
import Button from '@mui/material/Button'
import Grid from '@mui/material/Grid'
import Paper from '@mui/material/Paper'
import TextField from '@mui/material/TextField'
import Typography from '@mui/material/Typography'
import { DataGrid } from '@mui/x-data-grid'
import React, { useContext, useEffect, useState } from 'react'
import { useNavigate, useParams } from 'react-router-dom'
import api from '../../../services/api'
import Backdrop from '@mui/material/Backdrop'
import CircularProgress from '@mui/material/CircularProgress'
import Alert from '@mui/material/Alert'
import CustomAlert from '../../CustomAlert'
import ArrowBackIcon from '@mui/icons-material/ArrowBack';
import IconButton from '@mui/material/IconButton'
import { GlobalContext } from '../../../context/GlobalContext'
import CustomBackDrop from '../../CustomBackDrop'
import { toast } from 'react-toastify'

const LancarNotas = () => {
    const [alunos, setAlunos] = useState([])
    const [alunoInput, setAlunoInput] = useState("")
    const [disciplinaInput, setDisciplinaInput] = useState("")
    const navigate = useNavigate()
    const [openBackDrop, setOpenBackDrop] = useState(false)
    const { ano, setAno, periodo, setPeriodo } = useContext(GlobalContext)

    const [dados, setDados] = useState([])


    function alteracaoJaExiste(id) {
        for (let i = 0; i < dados.length; i++) {
            if (dados[i].matriculaId === id) {
                return i
            }
        }

        return -1
    }

    function existeNotaVazia() {

        if (dados.length !== alunos.length) {
            console.log("dentro do primeiro if")
            return true
        }

        for (let i = 0; i < dados.length; i++) {
            if (dados[i].nota.length === 0) {
                console.log("dentro do segundo if")
                return true
            }
        }
        return false
    }

    const columns = [
        { field: 'nomeAluno', headerName: 'Nome', flex: 1 },
        { field: 'matricula', headerName: 'Matrícula', flex: 1 },
        {
            field: "nota", headerName: "Nota", flex: 1, renderCell: (params) =>
                <TextField
                    size='small' sx={{ width: "50px", height: "40px" }}
                    onChange={(e) => {
                        const i = alteracaoJaExiste(params.row.id)
                        if (i >= 0) {
                            dados[i] = { matriculaId: params.row.id, nota: e.target.value }
                            console.log(alunos)
                            console.log(dados)
                        }
                        else {
                            dados.push({ matriculaId: params.row.id, nota: e.target.value })
                            console.log(alunos)
                            console.log(dados)
                        }
                    }}
                />
        }
    ];

    const { disciplinaId, exameId } = useParams()
    const [loading, setloading] = useState(false)
    const [paginationModel, setPaginationModel] = useState({
        page: 0,
        pageSize: 10
    })

    async function getAlunos() {
        setloading(true)
        try {
            const response = await api.get("/matriculas", {
                params: {
                    pagina: 0,
                    tamanho: 100,
                    nomeAluno: alunoInput ? alunoInput : "",
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

        setloading(false)
    }


    const handleClose = (event, reason) => {
        if (reason === 'clickaway') {
            return;
        }

        setOpen(false);
    };

    async function atualizaStatusExame() {
        setOpenBackDrop(true)
        try {
            await api.patch(`/exames/${exameId}/status`, null, {
                params: {
                    status: "CONCLUIDO"
                }
            })
        } catch (error) {

        }
        setOpenBackDrop(false)
    }

    async function salvarNotas() {
        setOpenBackDrop(true)
        try {
            await Promise.all(dados.map(d => {
                return api.post(`matriculas/${d.matriculaId}/resultados`, { exameId: exameId, nota: d.nota })

            }))

            toast.success("Notas registradas com sucesso!")
            atualizaStatusExame()
            navigate(`/docentes/turmasNotas/${disciplinaId}/exames`)
        } catch (error) {
        }
        setOpenBackDrop(false)
    }

    useEffect(() => {
        getAlunos()
    }, [])


    useEffect(() => {
        getAlunos()
    }, [alunoInput])

    return (
        <Box>
            <Typography variant='h5' gutterBottom={true}>Lançar Notas </Typography>
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
                        <Button variant='contained' onClick={() => navigate(`/docentes//turmasNotas/${disciplinaId}/exames`)} color='secondary'>Voltar</Button>
                        <Button variant='contained' onClick={() => {
                            if (existeNotaVazia()) {
                                toast.error("Preencha todos os campos de nota!")
                                return
                            }
                            salvarNotas()
                        }} sx={{ ml: 2 }}>Salvar</Button>
                    </Box>
                </Box>

            </Paper>
            <CustomBackDrop open={openBackDrop} handleClose={() => setOpenBackDrop(false)} />
        </Box>
    )
}

export default LancarNotas