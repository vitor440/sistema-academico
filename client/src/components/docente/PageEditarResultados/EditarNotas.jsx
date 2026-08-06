import { Backdrop, Box, Button, CircularProgress, Paper, TextField, Typography } from '@mui/material';
import { DataGrid } from '@mui/x-data-grid';
import React, { useEffect, useState } from 'react'
import CustomAlert from '../../CustomAlert';
import api from '../../../services/api';
import { useParams } from 'react-router-dom';
import { useNavigate } from 'react-router-dom';
import CustomBackDrop from '../../CustomBackDrop';
import { toast } from 'react-toastify';

const EditarNotas = () => {
    const [openBackDrop, setOpenBackDrop] = useState(false)
    const navigate = useNavigate()
    const [deleteOptions, setDeleteOptions] = useState(false)
    const { exameId, disciplinaId } = useParams()
    const [resultados, setResultados] = useState([])
    const [loading, setLoading] = useState(false)
    const columns = [
        { field: 'aluno', headerName: 'Nome', flex: 1 },
        { field: 'matricula', headerName: 'Matrícula', flex: 1 },
        {
            field: "nota", headerName: "Nota", flex: 1, renderCell: (params) =>
                <TextField
                    size='small'
                    sx={{ width: "50px", height: "40px" }}
                    defaultValue={params.row.nota}
                    onChange={(e) => {
                        for (let resultado of resultados) {
                            if (resultado.id === params.row.id) {
                                resultado.nota = e.target.value
                                break
                            }
                        }
                    }}
                />
        }
    ];
    const [backdrop, setBackdrop] = useState(false)
    const [paginationModel, setPaginationModel] = useState({
        page: 0,
        pageSize: 100
    })


    async function getResultados() {
        setLoading(true)
        try {
            const response = await api.get("/resultados", {
                params: {
                    tamanho: paginationModel.pageSize,
                    exameId: exameId
                }
            })
            const data = response.data.content
            setResultados(data)
        } catch (error) {

        }
        setLoading(false)
    }

    async function atualizarResultados() {
        setBackdrop(true)
        try {
            await Promise.all(resultados.map(resultado => {
                return api.put(`/resultados/${resultado.id}`, null, {
                    params: {
                        nota: resultado.nota
                    }
                })
            }))
            toast.success("Notas atualizadas com sucesso!")
        } catch (error) {
        }
        setBackdrop(false)
    }

    useEffect(() => {
        getResultados()
    }, [])

    return (
        <Box>
            <Typography variant='h5' gutterBottom={true}>Editar Notas </Typography>
            <Paper sx={{ p: 2 }} variant='outlined'>
                <DataGrid
                    rows={resultados}
                    columns={columns}
                    style={{ color: "#fff", margin: "0 auto" }}
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
                        <Button variant='contained' color='secondary' onClick={() => navigate(`/docentes/editarResultados/${disciplinaId}/exames`)}>Voltar</Button>
                        <Button variant='contained'
                            sx={{ ml: 2 }} onClick={atualizarResultados} >Salvar</Button>
                    </Box>
                </Box>

            </Paper>
            <Backdrop open={openBackDrop}>
                <CircularProgress />
            </Backdrop>

            <CustomBackDrop open={backdrop} handleClose={() => setBackdrop(false)} />
        </Box>
    )
}

export default EditarNotas