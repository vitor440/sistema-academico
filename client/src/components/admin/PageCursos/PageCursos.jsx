import React from 'react'
import { DataGrid, gridClasses, renderActionsCell, GridActionsCell, GridActionsCellItem } from '@mui/x-data-grid';
import { MdDeleteOutline } from "react-icons/md";
import { CiEdit } from "react-icons/ci";
import { useState, useEffect } from 'react';
import { CursoHooks } from '../../hooks/CursoHooks';
import CustomTable from '../../CustomTable';
import Box from '@mui/material/Box';
import Typography from '@mui/material/Typography';
import TextField from '@mui/material/TextField';
import MenuItem from '@mui/material/MenuItem';
import Grid from '@mui/material/Grid';
import Paper from '@mui/material/Paper';
import Button from '@mui/material/Button';
import api from '../../../services/api';
import DeleteOptions from '../../DeleteOptions';
import FormCurso from './FormCurso';
import { useNavigate } from 'react-router-dom';
import { toast } from 'react-toastify';

const PageCursos = () => {

    const [area, setArea] = useState("")
    const [turno, setTurno] = useState("")
    const [periodos, setPeriodos] = useState("")
    const [nome, setNome] = useState("")
    const [open, setOpen] = useState(false)
    const [atualizar, setAtualizar] = useState(false)
    const [deleteOptions, setDeleteOptions] = useState(false)
    const [curso, setCurso] = useState("")
    const navigate = useNavigate()
    const [total, setTotal] = useState(0)

    const [loading, setloading] = useState(false)
    const [paginationModel, setPaginationModel] = useState({
                                page: 0,
                                pageSize: 20
                              })
            
    const [cursos, setCursos] = useState([])
    const {listar} = CursoHooks()
    const columns = [
            { field: 'nome', headerName: 'nome', flex: 1 },
            { field: 'area', headerName: 'Área', flex: 1 },
            { field: 'quantidadeAlunos', headerName: 'quantidade_alunos', flex: 1 },
            { field: 'periodo', headerName: 'Turno', flex: 1 },
            { field: 'quantidadePeriodos', headerName: 'Periodos', flex: 1 },
            {field: "actions", headerName:"Actions", type: "actions", flex: 1, getActions: (params) => {
              return [
                <GridActionsCellItem 
                  icon={<CiEdit/>}
                  label="edit"
                  color='inherit'
                  onClick={() => handleEditClick(params)}
                />
                ,<GridActionsCellItem 
                  icon={<MdDeleteOutline/>}
                  label="delete"
                  color='inherit'
                  onClick={() => handleDeleteClick(params)}
                />
        
              ]
            }}
          ];

    const handleEditClick = (params) => {
          const curso = cursos.find(d => d.id === params.id)

          setAtualizar(true)
          setCurso(curso)
          setOpen(true)
        }
      
    const handleDeleteClick = (params) => {
      const curso = cursos.find(d => d.id === params.id)
      setCurso(curso)
      setDeleteOptions(true)
    }

    async function getCursos() {
        
        try {
          setloading(true)
          // const data = await listar(paginationModel.page, paginationModel.pageSize, nome, area, turno, periodos)
          const response = await api.get("/cursos", {
            params:{
              pagina: paginationModel.page,
              tamanho: paginationModel.pageSize,
              nome: nome,
              area: area,
              periodo: turno,
              quantidadePeriodos: periodos
            }
          })

          setTotal(response.data.totalElements)
          const data = response.data.content
          setCursos(data)

        } catch (error) {
        }

        setloading(false)
      }
    
    async function  deletarCurso() {
      try {
        await api.delete(`/cursos/${curso.id}`)

        setDeleteOptions(false)
        getCursos()
        toast.success("Item Deletado!")
      } catch (error) {
        
      }
    
    }

    const handleClose = () => {
      setOpen(false)
      setAtualizar(false)
    }

    useEffect(() => {
      getCursos()
      }, [paginationModel, area, turno, periodos, nome])

  return (

    <Box>
      <Typography variant='h5'>Cursos</Typography>
      <Grid container direction='column' spacing={3}>
          <Grid container direction='row'>
              <Grid size={12}>
                  <Paper sx={{p:2, display:'flex', justifyContent:'space-between'}} variant='outlined'>
                      <TextField label='nome do curso' size='small' value={nome} onChange={(e) => setNome(e.target.value)}/>

                      <TextField label='Área' size='small' select sx={{width:180}} value={area} onChange={(e) => setArea(e.target.value)}>
                        <MenuItem key={1} value=''>Todos</MenuItem>
                        <MenuItem key={2} value='EXATAS'>EXATAS</MenuItem>
                        <MenuItem key={3} value='HUMANAS'>HUMANAS</MenuItem>
                        <MenuItem key={4} value='LETRAS'>LETRAS</MenuItem>
                        <MenuItem key={5} value='CIENCIAS BIOLOGICAS'>CIẼNCIAS BIOLOGICAS</MenuItem>
                        <MenuItem key={6} value='ENGENHARIAS'>ENGENHARIAS</MenuItem>
                        <MenuItem key={7} value='CIENCIAS AGRÁRIAS'>CIÊNCIAS AGRÁRIAS</MenuItem>
                      </TextField>

                      <TextField label='Turno' size='small' select sx={{width:180}} value={turno} onChange={(e) => setTurno(e.target.value)}>
                        <MenuItem key={1} value=''>Todos</MenuItem>
                        <MenuItem key={2} value='INTEGRAL'>INTEGRAL</MenuItem>
                        <MenuItem key={3} value='MATUTINO'>MATUTINO</MenuItem>
                        <MenuItem key={4} value='VESPERTINO'>VESPERTINO</MenuItem>
                      </TextField>
                      <TextField label='Periodos' size='small' select sx={{width:180}} value={periodos} onChange={(e) => setPeriodos(e.target.value)}>
                        <MenuItem key={1} value=''>Todos</MenuItem>
                        <MenuItem key={2} value='6'>6</MenuItem>
                        <MenuItem key={3} value='8'>8</MenuItem>
                        <MenuItem key={4} value='10'>10</MenuItem>
                      </TextField>
                      <Button variant='contained' size='medium' onClick={() => setOpen(true)}>Add Curso</Button>
                  </Paper>
              </Grid>
          </Grid>
      </Grid>

      <Paper sx={{p:2, mt:3}} variant='outlined'>
          <CustomTable columns={columns} rows={cursos} paginationModel={paginationModel} setPaginationModel={setPaginationModel} loading={loading} total={total}/>
      </Paper>

      <FormCurso open={open} handleClose={handleClose} curso={curso} atualizar={atualizar} obterCursos={getCursos}/>

        <DeleteOptions 
        open={deleteOptions} 
        handleClose={() => setDeleteOptions(false)} 
        deletar={deletarCurso} mensagem={"Curso deletado com sucesso!"} mensagemErro={"Erro ao deletar curso!"}/>
    </Box>
  )
}

export default PageCursos