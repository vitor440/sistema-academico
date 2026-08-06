import Box from '@mui/material/Box'
import Grid from '@mui/material/Grid'
import Typography from '@mui/material/Typography'
import React, { useContext, useEffect, useState } from 'react'
import Card from '../../Card'
import { FaBook } from "react-icons/fa";
import { LuNotebookText } from "react-icons/lu";
import { FaPencil } from "react-icons/fa6";
import { LineChart } from '@mui/x-charts/LineChart';
import Paper from '@mui/material/Paper'
import { BarChart } from '@mui/x-charts'
import CustomTable from '../../CustomTable'
import { GlobalContext } from '../../../context/GlobalContext'
import api from '../../../services/api'
import { GridActionsCellItem } from '@mui/x-data-grid'
import DeleteIcon from '@mui/icons-material/Delete';
import EditIcon from '@mui/icons-material/Edit';
import TextField from '@mui/material/TextField'
import MenuItem from '@mui/material/MenuItem'
import Button from '@mui/material/Button'
import { CircularProgress } from '@mui/material'

import { PiStudentBold } from "react-icons/pi";


const DocenteDashboard = () => {


  const [quantidadeTurmas, setQuantidadeTurmas] = useState(0)
  const [quantidadeAlunos, setQuantidadeAlunos] = useState(0)
  const [quantidadeExames, setQuantidadeExames] = useState(0)
  const [mediaNotas, setMediaNotas] = useState([])
  const [disciplinaAlunos, setDisciplinaAlunos] = useState([])
  const [exames, setExames] = useState([])
  const { openDrawer, ano, setAno, periodo, setPeriodo } = useContext(GlobalContext)
  const [docente, setDocente] = useState("")
  const [loading, setLoading] = useState(false)

  const columns = [
    { field: 'id', headerName: 'ID', flex: 1 },
    { field: 'nome', headerName: 'nome', flex: 1 },
    { field: 'disciplinaId', headerName: 'disciplinaId', flex: 1 },
    { field: 'data', headerName: 'Data', flex: 1 },
    { field: 'hora', headerName: 'Horário', flex: 1 },
    { field: 'tipo', headerName: 'Tipo', flex: 1 },
    { field: 'peso', headerName: 'Peso', flex: 1 },
  ];


  const [paginationModel, setPaginationModel] = useState({
    page: 0,
    pageSize: 10
  })


  async function getQuantidadeTurmas() {
    setLoading(true)
    try {


      const response = await api.get("/disciplinas", {
        params: {
          docenteId: localStorage.getItem("docenteId"),
          tamanho: 10
        }
      })

      const data = response.data
      setQuantidadeTurmas(data.content.length)

      let totalAlunos = 0

      data.content.forEach(content => totalAlunos += content.alunosMatriculados)

      setQuantidadeAlunos(totalAlunos)

    } catch (error) {

    }

    setLoading(false)
  }

  async function getExames() {

    setLoading(true)
    try {


      const response = await api.get("/exames", {
        params: {
          direction: "DESC",
          pagina: paginationModel.page,
          tamanho: paginationModel.pageSize,
          semestre: periodo,
          ano: ano
        }
      })
      const data = response.data
      setExames(data.content)
      setQuantidadeExames(response.data.totalElements)
    } catch (error) {

    }
    setLoading(false)
  }

  async function getMediaNotas() {

    setLoading(true)
    const response = await api.get("/resultados/mediaNotasMeses")
    setMediaNotas(response.data)
  }

  async function getDisciplinas(params) {
    // const response = await api.get("/disciplinas/topDisciplinas")
    const response = await api.get("/disciplinas", {
      params: {
        pagina: 0,
        tamanho: 5,
        sortDirection: "DESC",
        docenteId: localStorage.getItem("docenteId")
      }
    })
    setDisciplinaAlunos(response.data.content)

    setLoading(false)
  }


  useEffect(() => {

    getQuantidadeTurmas()
    getExames()
    getMediaNotas()
    getDisciplinas()
  }, [paginationModel])

  if (loading) {
    return (
      <Box sx={{ display: 'flex', justifyContent: 'center', alignItems: "center" }}>
        <CircularProgress />
      </Box>
    )
  }

  return (
    <Box >
      <Typography variant='h4' gutterBottom={true}>Bem-Vindo {localStorage.getItem("username")}</Typography>
      <Grid container direction="column" spacing={3} sx={{ minWidth: 0 }}>
        <Grid container direction="row" spacing={3}>
          <Grid size={4}>
            <Card Icone={FaBook} titulo={"Disciplinas"} content={quantidadeTurmas} cor={"#01460a"} />
          </Grid>
          <Grid size={4}>
            <Card Icone={PiStudentBold} titulo={"Total ALunos"} content={quantidadeAlunos} cor={"#914202"} />
          </Grid>
          <Grid size={4}>
            <Card Icone={FaPencil} titulo={"Exames Agendados"} content={quantidadeExames} cor={"#021791"} />
          </Grid>
        </Grid>
        <Grid container direction="row">
          <Grid size={6}>
            <Paper variant='outlined' sx={{ p: 2 }}>
              <Typography variant='h6' sx={{mb:2}}>Média de notas nos últimos 6 meses</Typography>
              <LineChart
                dataset={mediaNotas}
                series={[{ dataKey: "media", label: "média de notas", curve: "natural", showMark: true, shape: "circle" }]}
                xAxis={[{ dataKey: "mes", scaleType: "point" }]}
                yAxis={[{ max: "10" }]}
                grid={{ vertical: true, horizontal: true }}
                height={300}
              />
            </Paper>
          </Grid>
          <Grid size={6}>
            <Paper variant='outlined' sx={{ p: 2 }}>
              <Typography variant='h6' sx={{mb:2}}>Disciplinas x Alunos Matriculados</Typography>
              <BarChart
                dataset={disciplinaAlunos}
                series={[{ dataKey: "alunosMatriculados", label: "Alunos Matriculados" }]}
                xAxis={[{ dataKey: "nome" }]}
                height={300}
              />
            </Paper>
          </Grid>
        </Grid>


      </Grid>

      <Paper sx={{ p: 2, mt: 3 }} variant='outlined'>
          <Typography variant='h6' sx={{mb:2}}>Exames Marcados</Typography>
        <CustomTable columns={columns} rows={exames} paginationModel={paginationModel} setPaginationModel={setPaginationModel} loading={loading} total={quantidadeExames}/>
      </Paper>
    </Box>
  )
}

export default DocenteDashboard