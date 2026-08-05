import Box from '@mui/material/Box'
import MenuItem from '@mui/material/MenuItem'
import Paper from '@mui/material/Paper'
import TextField from '@mui/material/TextField'
import Typography from '@mui/material/Typography'
import React, { useContext, useEffect, useState } from 'react'
import CustomTable from '../../CustomTable'
import { GridActionsCellItem } from '@mui/x-data-grid'
import Button from '@mui/material/Button'
import api from '../../../services/api'
import { useNavigate } from 'react-router-dom'
import { GlobalContext } from '../../../context/GlobalContext'

const Exames = () => {
  const navigate = useNavigate()
  const [direction, setDirection] = useState("DESC")
  const [disciplinaId, setDisciplinaId] = useState("")
  const [matriculas, setMatriculas] = useState([])
  const { ano, setAno, periodo, setPeriodo } = useContext(GlobalContext)
  const [exames, setExames] = useState([])
  const [totalExames, setTotalExames] = useState(0)
  const columns = [
    { field: 'nome', headerName: 'Prova', flex: 1 },
    { field: 'disciplina', headerName: 'Disciplina', flex: 1 },
    { field: 'data', headerName: 'Data', flex: 1 },
    { field: 'hora', headerName: 'Hora', flex: 1 },
    { field: 'peso', headerName: 'Peso', flex: 1 }
  ];


  const [loading, setloading] = useState(false)
  const [paginationModel, setPaginationModel] = useState({
                        page: 0,
                        pageSize: 20
                      })


  async function getMatriculas() {
  setloading(true)
    try {
      const response = await api.get("/matriculas", {
        params: {
          semestre: periodo,
          ano: ano
        }
      })
      const content = response.data.content

      setMatriculas(content)

    } catch (error) {
    }
  setloading(false)
  }

  async function getExames() {
  setloading(true)
    try {
      const response = await api.get("/exames", {
        params: {
          direction: direction,
          semestre: periodo,
          ano: ano,
          tipo: "PROVA",
          disciplinaId: disciplinaId
        }
      })
      setTotalExames(response.data.totalElements)
      const content = response.data.content

      setExames(content)

    } catch (error) {
    }
  setloading(false)
  }


useEffect(() => {
  getExames()
}, [paginationModel, direction, disciplinaId])

return (
  <Box>
    <Typography variant='h4' gutterBottom={true}>Provas Marcadas</Typography>
    <Paper elevation={0} sx={{ display: "flex", mb: 3, p: 2 }}>
      <TextField label="Disciplina" select sx={{ width: "25%", mr: 2 }} size='small' value={disciplinaId} onChange={(e) => setDisciplinaId(e.target.value)}>
        <MenuItem key={0} value=''>Todos</MenuItem>
        {matriculas.map(m => {
          return <MenuItem key={m.id} value={m.disciplinaId}>{m.disciplina}</MenuItem>
        })}
      </TextField>

      <TextField label="Data" select sx={{ width: "25%", mr: 2 }} size='small' value={direction} onChange={(e) => setDirection(e.target.value)}>
        <MenuItem key={1} value='DESC'>Mais Recentes</MenuItem>
        <MenuItem key={2} value='ASC'>Mais Antigos</MenuItem>
      </TextField>
    </Paper>

    <Paper>
      <CustomTable columns={columns} rows={exames} paginationModel={paginationModel} setPaginationModel={setPaginationModel} loading={loading} total={totalExames}/>
    </Paper>
  </Box>
)
}

export default Exames