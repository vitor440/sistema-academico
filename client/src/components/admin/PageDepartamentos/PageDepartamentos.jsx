import React from 'react'
import "./PageDepartamentos.css"
import { DataGrid, gridClasses, renderActionsCell, GridActionsCell, GridActionsCellItem } from '@mui/x-data-grid';
import { MdDeleteOutline } from "react-icons/md";
import { CiEdit } from "react-icons/ci";
import { useState, useEffect } from 'react';
import { DepartamentoHook } from '../../hooks/DepartamentoHook';
import CustomTable from '../../CustomTable';
import Modal from '@mui/material/Modal';
import DepartamentoForm from '../../DepartamentoForm';
import Box from "@mui/material/Box"
import Typography from '@mui/material/Typography';
import Grid from '@mui/material/Grid';
import Paper from '@mui/material/Paper';
import TextField from '@mui/material/TextField';
import Button from '@mui/material/Button';
import CustomModal from '../../CustomModal';
import ClearIcon from '@mui/icons-material/Clear';
import IconButton from '@mui/material/IconButton';
import api from '../../../services/api';
import { useNavigate } from 'react-router-dom';
import CustomAlert from '../../CustomAlert';
import DeleteOptions from '../../DeleteOptions';
import FormDepartamento from './FormDepartamento';
import { toast } from 'react-toastify';

const PageDepartamentos = () => {
    const navigate = useNavigate()

    const [paginationModel, setPaginationModel] = useState({
        page: 0,
        pageSize: 20
      })
        
    const [departamentos, setDepartamentos] = useState([])
    const {listar} = DepartamentoHook()
    const [loading, setloading] = useState(false)
    const [nome, setNome] = useState("")
    const [open, setOpen] = useState(false)
    const [atualizar, setAtualizar] = useState(false)
    const [deleteOptions, setDeleteOptions] = useState(false)
    const [departamento, setDepartamento] = useState("")
    const [total, setTotal] = useState(0)
    
    const columns = [
        { field: 'nome', headerName: 'nome', flex: 1 },
        { field: 'bloco', headerName: 'Bloco', flex: 1 },
        { field: 'sigla', headerName: 'Sigla', flex: 1 },
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

    async function getDepartamentos() {
      
        try {
          setloading(true)
          // const data = await listar(paginationModel.page, paginationModel.pageSize, nome)
          const response = await api.get("/departamentos", {
            params:{
              pagina: paginationModel.page,
              tamanho: paginationModel.pageSize,
              nome: nome
            }
          })
          setTotal(response.data.totalElements)
          const data = response.data.content
          setDepartamentos(data)

        } catch (error) {
        if(error.response.status === 403) {
          alert("Acesso negado!")
          navigate("/login")
        }

        else if(error.response.status === 401){
          alert("Tokens inválidos!")
          navigate("/login")
        }
        }
        setloading(false)
    }
      
    const handleEditClick = (params) => {
      const departamento = departamentos.find(d => d.id === params.id)

      setAtualizar(true)
      setDepartamento(departamento)
      handleOpen()

    }
      
    const handleDeleteClick = (params) => {
      const departamento = departamentos.find(d => d.id === params.id)
      setDepartamento(departamento)
      setDeleteOptions(true)
   }
    async function deletarDepartamento() {
      
      try {
        
        await api.delete(`/departamentos/${departamento.id}`)
        setDeleteOptions(false)
        getDepartamentos()
        toast.success("Item Deletado!")
      } catch (error) {
      }
      
      }
      
      
      
      const handleOpen = () => setOpen(true)
      const handleClose = () => {
        setOpen(false)
        setAtualizar(false)
      }


      useEffect(() => {

      getDepartamentos()
      }, [paginationModel, nome])

  return (

    <Box>
      <Typography variant='h5' sx={{p:1}}>Departamentos</Typography>
      <Grid container direction='column' spacing={3}>
          <Grid container direction='row'>
              <Grid size={12}>
                  <Paper sx={{p:2, display:'flex', justifyContent:'space-between'}} variant='outlined'>
                      <TextField label='nome do departamento' size='small' value={nome} onChange={(e) => setNome(e.target.value)}/>
                      <Button variant='contained' size='medium' onClick={() => setOpen(true)}>Add Departamento</Button>
                  </Paper>
              </Grid>
          </Grid>
      </Grid>
      <Paper sx={{p:2, mt:3}} variant='outlined'>
          <CustomTable columns={columns} paginationModel={paginationModel} setPaginationModel={setPaginationModel} rows={departamentos} loading={loading} total={total}/>
        </Paper>

        <FormDepartamento open={open} handleClose={handleClose} departamento={departamento} atualizar={atualizar} obterDepartamentos={getDepartamentos}/>

        <DeleteOptions 
        open={deleteOptions} 
        handleClose={() => setDeleteOptions(false)} 
        deletar={deletarDepartamento} mensagem={"Departamento deletado com sucesso!"} mensagemErro={"Erro ao deletar departamento!"}/>
    </Box>
  )
}

export default PageDepartamentos