import { Box, Button, MenuItem, Paper, TextField, Typography } from '@mui/material'
import React, { useContext, useEffect, useState } from 'react'
import { GlobalContext } from '../../../context/GlobalContext'
import { DataGrid } from '@mui/x-data-grid'
import CustomAlert from '../../CustomAlert'
import CustomBackDrop from '../../CustomBackDrop'
import api from '../../../services/api'
import { toast } from 'react-toastify'

const ProvaFinal = () => {

  const [matriculas, setMatriculas] = useState([])
  const [disciplinas, setDisciplinas] = useState([])
  const { ano, setAno, periodo, setPeriodo } = useContext(GlobalContext)
  const [open, setOpen] = useState(false)
  const [disciplinaId, setDisciplinaId] = useState("")
  const [docente, setDocente] = useState("")

  console.log(matriculas)

  const columns = [
    { field: 'nomeAluno', headerName: 'Nome', flex: 1 },
    { field: 'disciplina', headerName: 'Disciplina', flex: 1 },
    {
      field: "notaFinal", headerName: "nota_final", flex: 1, renderCell: (params) =>
        <TextField
          size='small' sx={{ width: "50px", height: "40px" }}
          defaultValue={params.row.notaFinal}
          onChange={(e) => {
            const matricula = matriculas.find(m => m.id === params.id)
            matricula.notaFinal = e.target.value
          }}
        />
    }

  ];

  const [loading, setloading] = useState(false)
  const [paginationModel, setPaginationModel] = useState({
    page: 0,
    pageSize: 10
  })

  async function getMatriculas() {
    setloading(true)
    try {
      const response = await api.get("/matriculas", {
        params: {
          tamanho: 100,
          semestre: periodo,
          ano: ano,
          statusSolicitacao: 'EFETIVADA',
          disciplinaId: disciplinaId
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


  async function lancarNotas() {

    setloading(true)
    setOpen(true)
    try {
      await Promise.all(matriculas.map(matricula => {
        return api.patch(`/matriculas/${matricula.id}/notaFinal`, null, {
          params: {
            notaFinal: matricula.notaFinal
          }
        })
      }))
      toast.success("Notas Lançadas!")
    } catch (error) {

    }
    setOpen(false)

    setloading(false)
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
        <Typography variant='h5' gutterBottom={true}>Lançar notas de provas finais</Typography>
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
        <Box sx={{ p: 2, display: "flex", justifyContent: "end" }}>
          <Box>
            <Button variant='contained' onClick={lancarNotas} sx={{ ml: 2 }}>Salvar</Button>
          </Box>
        </Box>

      </Paper>
      <CustomBackDrop open={open} handleClose={() => setOpen(false)} />
    </Box>
  )
}

export default ProvaFinal