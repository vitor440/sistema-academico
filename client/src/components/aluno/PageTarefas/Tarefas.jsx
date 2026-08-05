import Box from '@mui/material/Box'
import Button from '@mui/material/Button'
import MenuItem from '@mui/material/MenuItem'
import Paper from '@mui/material/Paper'
import TextField from '@mui/material/TextField'
import Typography from '@mui/material/Typography'
import React, { useContext, useEffect, useState } from 'react'
import CustomTable from '../../CustomTable'
import api from '../../../services/api'
import { data } from 'react-router-dom'
import dayjs from 'dayjs'
import { GlobalContext } from '../../../context/GlobalContext'

const Tarefas = () => {


  const { ano, setAno, periodo, setPeriodo } = useContext(GlobalContext)

  const [tarefas, setTarefas] = useState([])
  const [totalTarefas, setTotalTarefas] = useState(0)
  const [disciplinas, setDisciplinas] = useState([])
  const [disciplinaId, setDisciplinaId] = useState("")
  const [direction, setDirection] = useState("ASC")
  const columns = [
    { field: 'nome', headerName: 'Tarefa', flex: 1 },
    { field: 'disciplina', headerName: 'Disciplina', flex: 1 },
    { field: 'data', headerName: 'Data', flex: 1 },
    { field: 'hora', headerName: 'Hora', flex: 1 },
    { field: 'peso', headerName: 'Peso', flex: 1 },
  ];


  const [loading, setloading] = useState(false)
  const [paginationModel, setPaginationModel] = useState({
                      page: 0,
                      pageSize: 20
                    })


  async function getDisciplinas() {
    setloading(true)
    try {
      const response = await api.get("/matriculas",
        {
          params: {
            semestre: periodo,
            ano: ano
          }
        })

      const dados = response.data.content
      setDisciplinas(dados)
    } catch (error) {

    }
    setloading(false)
  }

  async function getTarefas() {

    setloading(true)
    try {
      const response = await api.get("/exames", {
        params: {
          tipo: 'TRABALHO',
          pagina: paginationModel.page,
          tamanho: paginationModel.pageSize,
          sortDirection: direction,
          disciplinaId: disciplinaId,
          data: dayjs(new Date()).format("YYYY-MM-DD"),
          semestre: periodo,
          ano: ano
        }
      })
      setTotalTarefas(response.data.totalElements)
      const dados = response.data.content
      setTarefas(dados)
    } catch (error) {

    }
    setloading(false)
  }

  async function handleClick() {
    setloading(true)
    getTarefas()
    getDisciplinas()
    setloading(false)
  }

  useEffect(() => {
    getTarefas()
    getDisciplinas()
  }, [direction, paginationModel, disciplinaId])

  return (
    <Box>
      <Box sx={{ display: 'flex', justifyContent: 'space-between' }}>
        <Typography variant='h4' gutterBottom={true}>Trabalhos Pendentes</Typography>
        <Box>
          <TextField label="periodo" select defaultValue={periodo} size='small' sx={{ width: 80 }} onChange={(e) => setPeriodo(e.target.value)}>
            <MenuItem key={1} value={1}>1</MenuItem>
            <MenuItem key={2} value={2}>2</MenuItem>
          </TextField>
          <TextField label='ano' defaultValue={ano} onChange={(e) => setAno(e.target.value)} size='small' sx={{ ml: 1 }} />
          <Button size='medium' sx={{ ml: 1 }} variant='outlined' onClick={handleClick}>Filtrar</Button>
        </Box>
      </Box>
      <Paper elevation={0} sx={{ display: "flex", mb: 3, p: 2 }}>
        <TextField label="Disciplina" select sx={{ width: "25%", mr: 2 }} size='small' value={disciplinaId} onChange={(e) => setDisciplinaId(e.target.value)}>
          {disciplinas?.map(disciplina => {
            return <MenuItem key={disciplina.disciplinaID} value={disciplina.disciplinaID}>{disciplina.disciplina}</MenuItem>
          })}
        </TextField>

        <TextField label="Data" select sx={{ width: "25%" }} size='small' onChange={(e) => setDirection(e.target.value)}>
          <MenuItem key={1} value={'DESC'}>Mais Recentes</MenuItem>
          <MenuItem key={2} value={'ASC'}>Mais Antigos</MenuItem>
        </TextField>
      </Paper>

      <Paper>
        <CustomTable columns={columns} rows={tarefas} paginationModel={paginationModel} etPaginationModel={setPaginationModel} loading={loading} total={totalTarefas}/>
      </Paper>
    </Box>
  )
}

export default Tarefas