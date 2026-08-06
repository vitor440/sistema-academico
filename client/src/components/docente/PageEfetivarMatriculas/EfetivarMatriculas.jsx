import Button from '@mui/material/Button';
import React, { useContext, useEffect, useState } from 'react'
import api from '../../../services/api';
import { GlobalContext } from '../../../context/GlobalContext';
import Box from '@mui/material/Box';
import Typography from '@mui/material/Typography';
import Paper from '@mui/material/Paper';
import { DataGrid } from '@mui/x-data-grid';
import CustomAlert from '../../CustomAlert';
import CustomBackDrop from '../../CustomBackDrop';
import { MenuItem, TextField } from '@mui/material';
import { toast } from 'react-toastify';

const EfetivarMatriculas = () => {

  const [matriculas, setMatriculas] = useState([])
  const [disciplinas, setDisciplinas] = useState([])
  const { ano, setAno, periodo, setPeriodo } = useContext(GlobalContext)
  const [open, setOpen] = useState(false)
  const [disciplinaId, setDisciplinaId] = useState("")
  const [docente, setDocente] = useState("")

  const [loading, setloading] = useState(false)
  const [paginationModel, setPaginationModel] = useState({
    page: 0,
    pageSize: 10
  })

  const columns = [
    { field: 'nomeAluno', headerName: 'Nome', flex: 1 },
    { field: 'disciplina', headerName: 'Disciplinas', flex: 1 },
    {
      field: "efetivado", headerName: "Actions", flex: 1, renderCell: (params) =>
        <Button variant='outlined' color={params.row.efetivado ? 'error' : 'primary'} onClick={() => efetivarMatricula(params.row.id)}>{params.row.efetivado ? 'Cancelar efetivação' : 'Efetivar'}</Button>
    }
  ];


  async function getMatriculas() {
    setloading(true)
    try {
      const response = await api.get("/matriculas", {
        params: {
          tamanho: 100,
          semestre: periodo,
          ano: ano,
          disciplinaId: disciplinaId,
          statusSolicitacao: 'EFETIVADA'
        }
      })
      const data = response.data.content
      setMatriculas(data)
    } catch (error) {

    }
    setloading(false)
  }

  async function getDisciplinas() {
    setloading(true)
    try {
      const response = await api.get("/disciplinas", {
        params: {
          tamanho: 100,
          docenteId: localStorage.getItem("docenteId")
        }
      })
      const data = response.data.content
      setDisciplinas(data)
    } catch (error) {

    }

    setloading(false)
  }

  async function efetivarMatricula(id) {
    setOpen(true)
    try {
      await api.patch(`/matriculas/${id}/efetivarHistorico`)
      toast.success('Matrícula efetivada!')
      getDocente()
      getMatriculas()
      getDisciplinas()
    } catch (error) {

    }
    setOpen(false)
  }


  async function getDocente() {

    setloading(true)
    try {
      const response = await api.get("/docentes/me")
      const data = response.data
      setDocente(data)
    } catch (error) {

    }

    setloading(false)
  }

  function handleClick() {
    getDocente()
    getMatriculas()
    getDisciplinas()
  }

  useEffect(() => {
    getDocente()
    getMatriculas()
    getDisciplinas()
  }, [disciplinaId])

  return (
    <Box>
      <Box sx={{ display: 'flex', justifyContent: 'space-between', mb: 2 }}>
        <Typography variant='h5' gutterBottom={true}>Efetivar Histórico de Matriculas</Typography>
        <Box>
          <TextField label="periodo" select defaultValue={periodo} size='small' sx={{ width: 80 }} onChange={(e) => setPeriodo(e.target.value)}>
            <MenuItem key={1} value={1}>1</MenuItem>
            <MenuItem key={2} value={2}>2</MenuItem>
          </TextField>
          <TextField label='ano' defaultValue={ano} onChange={(e) => setAno(e.target.value)} size='small' sx={{ ml: 1 }} />
          <Button size='medium' sx={{ ml: 1 }} variant='outlined' onClick={handleClick}>Filtrar</Button>
        </Box>
      </Box>
      <Paper variant='outlined' sx={{ p: 2, mb: 2 }}>
        <TextField select label='disciplinas' size='small' value={disciplinaId} onChange={(e) => setDisciplinaId(e.target.value)} sx={{ width: 280 }}>
          <MenuItem key={0} value=''>Todos</MenuItem>
          {disciplinas?.map(d => {
            return <MenuItem key={d.id} value={d.id}>{d.nome}</MenuItem>
          })}
        </TextField>
      </Paper>
      <Paper sx={{ p: 2 }} variant='outlined'>
        <DataGrid
          rows={matriculas}
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

      </Paper>
      <CustomBackDrop open={open} handleClose={() => setOpen(false)} />
    </Box>
  )
}

export default EfetivarMatriculas