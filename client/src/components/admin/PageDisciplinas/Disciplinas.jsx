import { Box, Button, MenuItem, Paper, TextField, Typography } from '@mui/material'
import React, { useEffect, useState } from 'react'
import CustomTable from '../../CustomTable';
import api from '../../../services/api';
import { GridActionsCellItem } from '@mui/x-data-grid';
import { MdDeleteOutline } from "react-icons/md";
import { CiEdit } from "react-icons/ci";
import FormDisciplina from './FormDisciplina';
import DeleteOptions from '../../DeleteOptions';

const Disciplinas = () => {

    const [open, setOpen] = useState(false)
    const [atualizar, setAtualizar] = useState(false)
    const [disciplinas, setDisciplinas] = useState([])
    const [deleteOptions, setDeleteOptions] = useState(false)
    const [docentes, setDocentes] = useState([])
    const [nome, setNome] = useState("")
    const [docenteId, setDocenteId] = useState("")
    const [disciplinaSelecionada, setDisciplinaSelecionada] = useState("")
    const [totalDisciplinas, setTotalDisciplinas] = useState(0)
    const columns = [
        { field: 'nome', headerName: 'nome', flex: 1 },
        { field: 'docente', headerName: 'Docente', flex: 1 },
        { field: 'localizacao', headerName: 'Localização', flex: 1 },
        { field: 'alunosMatriculados', headerName: 'Alunos Matriculados', flex: 1 },
        { field: 'vagas', headerName: 'Vagas', flex: 1 },
        { field: 'departamento', headerName: 'Departamento', flex: 1 },
        {
            field: "actions", headerName: "Actions", type: "actions", flex: 1, getActions: (params) => {
                return [
                    <GridActionsCellItem
                        icon={<CiEdit />}
                        label="edit"
                        color='inherit'
                        onClick={() => handleEditClick(params)}
                    />
                    , <GridActionsCellItem
                        icon={<MdDeleteOutline />}
                        label="delete"
                        color='inherit'
                        onClick={() => handleDeleteClick(params)}
                    />

                ]
            }
        }
    ];

    const [paginationModel, setPaginationModel] = useState({
                            page: 0,
                            pageSize: 20
                          })

    const handleEditClick = (params) => {
        const disciplina = disciplinas.find(d => d.id === params.id)

        setAtualizar(true)
        setDisciplinaSelecionada(disciplina)
        setOpen(true)
    }

    const handleDeleteClick = (params) => {
        const disciplina = disciplinas.find(d => d.id === params.id)
        setDisciplinaSelecionada(disciplina)
        setDeleteOptions(true)
    }

    const [loading, setloading] = useState(false)


    async function getDisciplinas() {
        setloading(true)
        try {
            const response = await api.get("/disciplinas", {
                params: {
                    pagina: paginationModel.page,
                    tamanho: paginationModel.pageSize,
                    nome: nome,
                    docenteId: docenteId
                }
            })
            setTotalDisciplinas(response.data.totalElements)
            const data = response.data.content
            setDisciplinas(data)

        } catch (error) {

        }
        setloading(false)
    }
    async function getDocentes() {
        setloading(true)
        try {
            const response = await api.get("/docentes", {
                params: {
                    tamanho: 30
                }
            })
            const data = response.data.content
            setDocentes(data)

        } catch (error) {
        }


        setloading(false)
    }

    async function deletarDisciplina() {
        try {
            await api.delete(`/disciplinas/${disciplinaSelecionada.id}`)
            getDisciplinas()
            setDeleteOptions(false)
        } catch (error) {
        }
    }

    function handleClick() {
        setAtualizar(false)
        setOpen(true)
    }

    useEffect(() => {


        getDisciplinas()
        getDocentes()
    }, [nome, docenteId, paginationModel])

    return (
        <Box>
            <Typography variant='h5' sx={{ mb: 2 }}>Disciplinas</Typography>

            <Paper variant='outlined' sx={{ display: 'flex', justifyContent: 'space-between', mb: 2, p: 2 }}>
                <Box sx={{ display: 'flex', justifyContent: 'start' }}>
                    <TextField label='Nome' size='small' value={nome} onChange={(e) => setNome(e.target.value)} />
                    <TextField label='docente' select size='small' sx={{ width: 180, ml: 2 }} value={docenteId} onChange={(e) => setDocenteId(e.target.value)}>
                        <MenuItem key={0} value=''>Todos</MenuItem>
                        {docentes.map(docente => {
                            return <MenuItem key={docente.id} value={docente.id}>{docente.nome}</MenuItem>
                        })}
                    </TextField>
                </Box>
                <Button variant='contained' onClick={handleClick}>Add disciplina</Button>
            </Paper>
            <Paper variant='outlined' sx={{ p: 2 }}>
                <CustomTable columns={columns} rows={disciplinas} paginationModel={paginationModel} setPaginationModel={setPaginationModel} loading={loading} total={totalDisciplinas}/>
            </Paper>

            <DeleteOptions
                open={deleteOptions}
                handleClose={() => setDeleteOptions(false)}
                deletar={deletarDisciplina} mensagem={"Disciplina deletada com sucesso!"} mensagemErro={"Erro ao deletar disciplina!"} />
            <FormDisciplina open={open} handleClose={() => setOpen(false)} disciplina={disciplinaSelecionada} atualizar={atualizar} obterDisciplinas={getDisciplinas} />
        </Box>
    )
}

export default Disciplinas