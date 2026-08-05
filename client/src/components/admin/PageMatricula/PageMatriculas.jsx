import React from 'react'
import CustomTable from '../../CustomTable';
import { MatriculaHooks } from '../../hooks/MatriculaHooks';
import { useState, useEffect } from 'react';
import { GridActionsCellItem } from '@mui/x-data-grid';
import { MdDeleteOutline } from "react-icons/md";
import { CiEdit } from "react-icons/ci";
import Box from '@mui/material/Box';
import Typography from '@mui/material/Typography';
import Grid from '@mui/material/Grid';
import Paper from '@mui/material/Paper';
import TextField from '@mui/material/TextField';
import MenuItem from '@mui/material/MenuItem';
import Button from '@mui/material/Button';
import api from '../../../services/api';
import { useNavigate } from 'react-router-dom';

const PageMatriculas = () => {

  const [efetivado, setEfetivado] = useState("")
  const [statusSolicitacao, setStatusSolicitacao] = useState("")
  const [statusDisciplina, setStatusDisciplina] = useState("")
  const [total, setTotal] = useState(0)
  const navigate = useNavigate()

  const [loading, setloading] = useState(false)

  const [matriculas, setMatriculas] = useState([])
  const { listarMatriculas } = MatriculaHooks()

  const [paginationModel, setPaginationModel] = useState({
                          page: 0,
                          pageSize: 20
                        })


  async function obterMatriculas() {
    setloading(true)
    try {

      // const data = await listarMatriculas(paginationModel.page, paginationModel.pageSize, statusSolicitacao, statusDisciplina, efetivado)
      const response = await api.get("/matriculas", {
        params: {
          pagina: paginationModel.page,
          tamanho: paginationModel.pageSize,
          efetivado: efetivado,
          statusDisciplina: statusDisciplina,
          statusSolicitacao: statusSolicitacao
        }
      })
      setTotal(response.data.totalElements)
      const data = response.data.content
      setMatriculas(data)

    } catch (error) {
    }
    setloading(false)
  }



  const columns = [
    { field: 'id', headerName: 'id', flex: 1 },
    { field: 'nomeAluno', headerName: 'aluno', flex: 1 },
    { field: 'disciplina', headerName: 'disciplina', flex: 1 },
    { field: 'faltas', headerName: 'faltas', flex: 1 },
    { field: 'media', headerName: 'media', flex: 1 },
    { field: 'status', headerName: 'status', flex: 1 },
    { field: 'notaFinal', headerName: 'notaFinal', flex: 1 },
    { field: 'mediaFinal', headerName: 'mediaFinal', flex: 1 },
    { field: 'statusSolicitacao', headerName: 'statusSolicitacao', flex: 1 },
    { field: 'efetivado', headerName: 'efetivado', flex: 1 }
  ];

  const handleEditClick = (id) => {
    console.log(id);
  }

  const handleDeleteClick = (id) => {
    console.log(id);
  }

  useEffect(() => {


    obterMatriculas()
  }, [paginationModel, statusSolicitacao, statusDisciplina, efetivado])

  return (

    <Box>
      <Typography variant='h5'>Matrículas</Typography>
      <Grid container direction='column' spacing={3}>
        <Grid container direction='row'>
          <Grid size={12}>
            <Paper sx={{ p: 2, display: 'flex', justifyContent: 'space-between' }} variant='outlined'>

              <TextField label='Status-Disciplina' size='small' select sx={{ width: 280 }} value={statusDisciplina} onChange={(e) => setStatusDisciplina(e.target.value)}>
                <MenuItem key={1} value=''>Todos</MenuItem>
                <MenuItem key={2} value='CURSANDO'>CURSANDO</MenuItem>
                <MenuItem key={3} value='TRANCADO'>TRANCADO</MenuItem>
                <MenuItem key={4} value='REPROVADO_POR_NOTA'>REPROVADO_POR_NOTA</MenuItem>
                <MenuItem key={5} value='REPROVADO_POR_FALTA'>REPROVADO_POR_FALTA</MenuItem>
                <MenuItem key={6} value='APROVADO'>APROVADO</MenuItem>
              </TextField>

              <TextField label='Status-Solicitação' size='small' select sx={{ width: 280 }} value={statusSolicitacao} onChange={(e) => setStatusSolicitacao(e.target.value)}>
                <MenuItem key={1} value=''>Todos</MenuItem>
                <MenuItem key={2} value='PENDENTE'>PENDENTE</MenuItem>
                <MenuItem key={3} value='EFETIVADA'>EFETIVADA</MenuItem>
                <MenuItem key={4} value='INDEFERIDA'>INDEFERIDA</MenuItem>
              </TextField>
              <TextField label='Efetivado' size='small' select sx={{ width: 180 }} value={efetivado} onChange={(e) => setEfetivado(e.target.value)}>
                <MenuItem key={1} value=''>Todos</MenuItem>
                <MenuItem key={2} value='true'>true</MenuItem>
                <MenuItem key={3} value='false'>false</MenuItem>
              </TextField>
            </Paper>
          </Grid>
        </Grid>
      </Grid>

      <Paper sx={{ p: 2, mt: 3 }} variant='outlined'>
        <CustomTable columns={columns} paginationModel={paginationModel} setPaginationModel={setPaginationModel} rows={matriculas} loading={loading} total={total}/>
      </Paper>
    </Box>

  )

}

export default PageMatriculas