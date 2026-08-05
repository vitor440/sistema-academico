import React, { useContext } from 'react'
import './MainContent.css'
import Info from '../../Info'
import ListaAvaliacoes from '../PageExames/ListaAvaliacoes';
import ListaTarefas from '../PageTarefas/ListaTarefas';
import { FaBook } from "react-icons/fa";
import { LuNotebookText } from "react-icons/lu";
import { FaPencil } from "react-icons/fa6";
import CustomTable from '../../CustomTable';
import { DataGrid, gridClasses, renderActionsCell, GridActionsCell, GridActionsCellItem } from '@mui/x-data-grid';
import { MdDeleteOutline } from "react-icons/md";
import { CiEdit } from "react-icons/ci";
import { useState, useEffect } from 'react';
import Box from '@mui/material/Box';
import Typography from '@mui/material/Typography';
import Grid from '@mui/material/Grid';
import Card from '../../Card';
import Paper from '@mui/material/Paper';
import { GlobalContext } from '../../../context/GlobalContext';
import api from '../../../services/api'
import TextField from '@mui/material/TextField';
import MenuItem from '@mui/material/MenuItem'
import Button from '@mui/material/Button';

const MainContent = () => {

  const { openDrawer, ano, setAno, periodo, setPeriodo } = useContext(GlobalContext)
  const [disciplinas, setDisciplinas] = useState(0)
  const [exames, setExames] = useState([])
  const [quantidadeExames, setQuantidadeExames] = useState(0)
  const [tarefas, setTarefas] = useState([])
  const [quantidadeTarefas, setQuantidadeTarefas] = useState(0)



  const columnsExames = [
    { field: 'nome', headerName: 'nome', flex: 1 },
    { field: 'disciplina', headerName: 'Disciplina', flex: 1 },
    { field: 'data', headerName: 'Data', flex: 1 },
    { field: 'hora', headerName: 'Hora', flex: 1 },
    { field: 'tipo', headerName: 'Tipo', flex: 1 },
    { field: 'peso', headerName: 'Peso', flex: 1 },
    { field: 'status', headerName: 'Status', flex: 1 }
  ];

  const columnsTarefas = [
    { field: 'nome', headerName: 'nome', flex: 1 },
    { field: 'disciplina', headerName: 'Disciplina', flex: 1 },
    { field: 'data', headerName: 'Data', flex: 1 },
    { field: 'hora', headerName: 'Hora', flex: 1 }
  ];

  const [loading, setloading] = useState(false)
  const [paginationModel, setPaginationModel] = useState({
    page: 0,
    pageSize: 20
  })

  async function getExames() {

    const response = await api.get("/exames", {
      params: {
        pagina: paginationModel.page,
        tamanho: paginationModel.pageSize,
        semestre: periodo,
        ano: ano,
        tipo: "PROVA"
      }
    })

    const content = response.data.content

    setExames(content)
    setQuantidadeExames(response.data.totalElements)
  }

  async function getDisciplinas() {
    try {
      const response = await api.get("/matriculas", {
        params: {
          pagina: paginationModel.page,
          tamanho: paginationModel.pageSize,
          statusSolicitacao: 'EFETIVADA',
          semestre: periodo,
          ano: ano
        }
      })


      setDisciplinas(response.data.totalElements)
    } catch (error) {
    }
  }

  async function getTrabalhos() {
    const response = await api.get("/exames", {
      params: {
        tipo: "TRABALHO",
        pagina: paginationModel.page,
        tamanho: paginationModel.pageSize,
        semestre: periodo,
        ano: ano
      }
    })

    const content = response.data.content
    setQuantidadeTarefas(response.data.totalElements)
    setTarefas(content)
  }

  async function handleClick() {
    getExames()
    getDisciplinas()
    getTrabalhos()
  }

  useEffect(() => {

    getExames()
    getDisciplinas()
    getTrabalhos()
  }, [paginationModel])

  return (
    <Box >
      <Box sx={{ display: 'flex', justifyContent: 'space-between' }}>
        <Typography variant='h4' gutterBottom={true}>Bem-Vindo {localStorage.getItem("username")}</Typography>
        <Box>
          <TextField label="periodo" select defaultValue={periodo} size='small' sx={{ width: 80 }} onChange={(e) => setPeriodo(e.target.value)}>
            <MenuItem key={1} value={1}>1</MenuItem>
            <MenuItem key={2} value={2}>2</MenuItem>
          </TextField>
          <TextField label='ano' defaultValue={ano} onChange={(e) => setAno(e.target.value)} size='small' sx={{ ml: 1 }} />
          <Button size='medium' sx={{ ml: 1 }} variant='outlined' onClick={handleClick}>Filtrar</Button>
        </Box>
      </Box>
      <Grid container direction="column" spacing={3}>
        <Grid container direction="row" spacing={3}>
          <Grid size={4}>
            <Card Icone={FaBook} titulo={"Disciplinas"} content={disciplinas} cor={"#01460a"} />
          </Grid>
          <Grid size={4}>
            <Card Icone={LuNotebookText} titulo={"Trabalhos"} content={quantidadeTarefas} cor={"#914202"} />
          </Grid>
          <Grid size={4}>
            <Card Icone={FaPencil} titulo={"Exames"} content={quantidadeExames} cor={"#021791"} />
          </Grid>
        </Grid>
        <Grid container direction="row">
          <Grid size={8}>
            <Paper sx={{ p: 2 }} elevation={0} variant='outlined'>
              <Typography variant='h5' gutterBottom={true}>Exames Futuros</Typography>
              <CustomTable columns={columnsExames} rows={exames} paginationModel={paginationModel} setPaginationModel={setPaginationModel} loading={loading} total={quantidadeExames} />
            </Paper>
          </Grid>
          <Grid size={4}>
            <Paper sx={{ p: 2 }} elevation={0} variant='outlined'>
              <Typography variant='h5' gutterBottom={true}>Trabalhos Futuros</Typography>
              <CustomTable columns={columnsTarefas} rows={tarefas} paginationModel={paginationModel} setPaginationModel={setPaginationModel} loading={loading} total={quantidadeTarefas} />
            </Paper>
          </Grid>
        </Grid>
      </Grid>
    </Box>
  )
}

export default MainContent