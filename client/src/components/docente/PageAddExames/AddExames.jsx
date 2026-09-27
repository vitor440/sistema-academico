import Box from '@mui/material/Box'
import Button from '@mui/material/Button'
import CircularProgress from '@mui/material/CircularProgress'
import Grid from '@mui/material/Grid'
import Typography from '@mui/material/Typography'
import { useContext, useEffect, useState } from 'react'
import api from '../../../services/api'
import ExameCard from '../ExameCard'

import dayjs from 'dayjs'
import { useNavigate } from 'react-router-dom'
import { GlobalContext } from '../../../context/GlobalContext'
import FormExames from './FormExames'

const AddExames = () => {

  const [exames, setExames] = useState([])
  const [loading, setLoading] = useState(false)
  const [open, setOpen] = useState(false)
  const [disciplinas, setDisciplinas] = useState([])
  const navigate = useNavigate()

  const [nome, setNome] = useState("")
  const [tipo, setTipo] = useState("")
  const [peso, setPeso] = useState("")
  const [data, setData] = useState(dayjs(""))
  const [hora, setHora] = useState("")
  const [disciplinaId, setDisciplinaId] = useState("")
  const [pagina, setPagina] = useState(0)
  const [tamanho, setTamanho] = useState(100)

  const [atualizar, setAtualizar] = useState(false)
  const { openDrawer, ano, setAno, periodo, setPeriodo } = useContext(GlobalContext)

  const handleClose = () => {
    setOpen(false)
  }

  async function getDisciplinas() {
    try {


      const response = await api.get("/disciplinas", {
        params: {
          docenteId: localStorage.getItem("docenteId"),
          pagina: pagina,
          tamanho: tamanho
        }
      })

      const data = response.data
      setDisciplinas(data.content)
    } catch (error) {

    }
  }
  async function getExames() {
    setLoading(true)
    try {


      const response = await api.get("/exames", {
        params: {
          pagina: pagina,
          tamanho: tamanho,
          semestre: periodo,
          ano: ano
        }
      })

      const data = response.data
      setExames(data.content)
    } catch (error) {

    }
    setLoading(false)
  }


  useEffect(() => {
    getExames()
    getDisciplinas()
  }, [])

  if (loading) {
    return <CircularProgress />
  }


  return (
    <Box>
      <Box sx={{ display: "flex", justifyContent: "space-between", alignItems: "center" }}>
        <Typography variant='h5' sx={{ mb: 2 }}>Exames</Typography>
        <Button variant='outlined' sx={{ mb: 2 }} onClick={() => setOpen(true)}>Add Exame</Button>
      </Box>
      <Grid container direction="column" spacing={2}>
        <Grid container direction="row" spacing={2}>

          {exames.length ? exames.map(exame => (
            <ExameCard exame={exame} obterExames={getExames} />
          )) : <Typography variant='body1'>Nenhum exame agendado</Typography>}

        </Grid>
      </Grid>



      <FormExames open={open} handleClose={() => setOpen(false)} atualizar={false} exame={''} obterExames={getExames} />

    </Box>
  )
}

export default AddExames