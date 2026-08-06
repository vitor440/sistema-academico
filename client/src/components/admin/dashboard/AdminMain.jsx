import React from 'react'
import Info from '../../Info'
import { FaBook } from "react-icons/fa";
import { LuNotebookText } from "react-icons/lu";
import { FaPencil } from "react-icons/fa6";
import { MdDeleteOutline } from "react-icons/md";
import { CiEdit } from "react-icons/ci";
import { BarChart, axisClasses } from '@mui/x-charts';
import { useState, useEffect } from 'react';
import "./AdminMain.css"
import { CursoHooks } from '../../hooks/CursoHooks';
import { DepartamentoHook } from '../../hooks/DepartamentoHook';
import { DocenteHook } from '../../hooks/DocenteHook';
import CustomTable from '../../CustomTable';
import CustomBarChart from '../../CustomBarChart';
import CustomPieChart from '../../CustomPieChart';
import { GridActionsCellItem } from '@mui/x-data-grid';
import Box from '@mui/material/Box';
import Typography from '@mui/material/Typography';
import Grid from '@mui/material/Grid';
import Card from '../../Card';
import Paper from '@mui/material/Paper';
import CircularProgress from '@mui/material/CircularProgress';
import { useNavigate } from 'react-router-dom';
import api from '../../../services/api'
import { FaRegBuilding } from "react-icons/fa";
import AccountBalanceOutlinedIcon from '@mui/icons-material/AccountBalanceOutlined';
import { HiMiniBuildingLibrary } from "react-icons/hi2";
import { ImUserTie } from "react-icons/im";

const AdminMain = () => {

  const navigate = useNavigate()
  const {listar, cursosPorAlunos, areasPorCurso, countCursos} = CursoHooks()
  const {countDepartamentos}  = DepartamentoHook()
  const {countDocentes} = DocenteHook()
  const [loading, setLoading] = useState(false)
  const [loadingTable, setLoadingTable] = useState(false)

  
  const [cursos, setCursos] = useState([])
  const [barchartData, setBarchartData] = useState([])
  const [pieData, setPieData] = useState([])
  const [totalCursos, setTotalCursos] = useState(0)
  const [totalDepartamentos, setTotalDepartamentos] = useState(0)
  const [totalDocentes, setTotalDocentes] = useState(0)
  const [paginationModel, setPaginationModel] = useState({
                              page: 0,
                              pageSize: 20
                            })


  async function getBarChartData() {
      setLoading(true)
      // const data = await cursosPorAlunos()
      try {
        const response = await api.get("/cursos/alunos-curso")
        const data = response.data
        setBarchartData(data)
        
      } catch (error) {
      }
      setLoading(false)
    }

    async function getPieData() {
      setLoading(true)
      // const data = await areasPorCurso()
      try {
        const response = await api.get("/cursos/areas-count")
        const data = response.data
        setPieData(data.map(d => {
          return {label: d[0], value: d[1]}
        }))
      } catch (error) {
      }

      
      setLoading(false)
    }

    async function getCursos() {
      setLoading(true)
      try {
        const response = await api.get("/cursos/count")
        const data = response.data
        setTotalCursos(data)
        
      } catch (error) {
        
      }
        setLoading(false)
      }

    async function getDepartamentos() {
      setLoading(true)
      try {
      const response = await api.get("/departamentos/count")
      const data = response.data
      setTotalDepartamentos(data)
      } catch (error) {
        
      }
      setLoading(false)
    }

    async function getDocentes() {
      setLoading(true)
      try {
        const response = await api.get("/docentes/count")
        const data = response.data
        setTotalDocentes(data)
        
      } catch (error) {
        
      }
      setLoading(false)
    }

    async function obterCursos() {
        setLoadingTable(true)
        try {
          const response = await api.get("/cursos", {
            params:{
              pagina: paginationModel.page,
              tamanho: paginationModel.pageSize
            }
          }) 
          const data = response.data.content
          setCursos(data)
          
        } catch (error) {
          
        }
        setLoadingTable(false)
      }

  



  

  const columns = [
    { field: 'nome', headerName: 'nome', flex: 1  },
    { field: 'area', headerName: 'Área', flex: 1 },
    { field: 'quantidadeAlunos', headerName: 'quantidade_alunos', flex: 1 },
    { field: 'periodo', headerName: 'Turno', flex: 1 },
    { field: 'quantidadePeriodos', headerName: 'Periodos', flex: 1 }
  ];

  const handleEditClick = (id) => {
    //console.log(id);
  }

   const handleDeleteClick = (id) => {
    //console.log(id);
  }

  useEffect(() => {

      
      obterCursos() 
  }, [paginationModel])

  useEffect(() => {
    
    getBarChartData() // carrega dados para o gráfico de barras
    getPieData() // carrega dados para o gráfico de pizza
    getCursos() 
    getDepartamentos()
    getDocentes()

    
  }, [])

  if(loading) {
    return(
      <Box sx={{display:'flex', justifyContent:'center', alignItems:"center"}}>
        <CircularProgress/>
      </Box>
    )
  }

  return (
    <Box>
      <Typography variant='h5' sx={{mb:2}}>Bem vindo {localStorage.getItem("username")}</Typography>
      <Grid container direction="column" spacing={3}>
        <Grid container direction="row"spacing={3}>
          <Grid size={4}>
            <Card Icone={FaRegBuilding} titulo={"Departamentos"} content={totalDepartamentos} cor={"#01460a"}/>
          </Grid>
          <Grid size={4}>
            <Card Icone={HiMiniBuildingLibrary} titulo={"Cursos"} content={totalCursos} cor={"#914202"}/>
          </Grid>
          <Grid size={4}>
            <Card Icone={ImUserTie} titulo={"Docentes"} content={totalDocentes} cor={"#021791"}/>
          </Grid>
        </Grid>
        <Grid container direction="row"spacing={3}>
          <Grid size={6}>
            <Paper sx={{p:1, height:400}} variant='outlined'>
                <Typography variant='h5' sx={{mb:3}}>Cursos x Alunos Matriculados</Typography>
              <CustomBarChart data={barchartData}   loading={loading}/>

            </Paper>
          </Grid>
          <Grid size={6}>
            <Paper sx={{p:1, height:400}} variant='outlined'>
                <Typography variant='h5' sx={{mb:3}}>Proporção de Cursos por Área do Conhecimento</Typography>
              <CustomPieChart data={pieData}  loading={loading}/>

            </Paper>
          </Grid>
        </Grid>
      </Grid>
      <Paper sx={{p:2, mt:3}} variant='outlined' >
        <Typography variant='h5' sx={{mb:2}}>Lista de cursos</Typography>
        <CustomTable columns={columns} rows={cursos} paginationModel={paginationModel} setPaginationModel={setPaginationModel} loading={loadingTable} total={totalCursos}/>
      </Paper>
    </Box>
  )
}

export default AdminMain