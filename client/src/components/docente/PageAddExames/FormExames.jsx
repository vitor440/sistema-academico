import React, { useEffect, useState } from 'react'
import CustomModal from '../../CustomModal'
import TextField from '@mui/material/TextField'
import MenuItem from '@mui/material/MenuItem'
import ClearIcon from '@mui/icons-material/Clear';
import IconButton from '@mui/material/IconButton'
import { LocalizationProvider } from '@mui/x-date-pickers/LocalizationProvider';
import { AdapterDayjs } from '@mui/x-date-pickers/AdapterDayjs';
import { DateField } from '@mui/x-date-pickers/DateField';
import dayjs from 'dayjs';
import CustomAlert from '../../CustomAlert'
import { Box, Button, CircularProgress, Grid, Typography } from '@mui/material';
import api from '../../../services/api';
import { toast } from 'react-toastify';

const FormExames = ({ open, handleClose, atualizar, exame, obterExames }) => {

  const [nome, setNome] = useState("")
  const [tipo, setTipo] = useState("")
  const [peso, setPeso] = useState("")
  const [data, setData] = useState(dayjs(""))
  const [hora, setHora] = useState("")
  const [disciplinaId, setDisciplinaId] = useState("")
  const [disciplinas, setDisciplinas] = useState([])
  const [docente, setDocente] = useState("")
  const [loading, setLoading] = useState(false)

  async function limparCampos() {
    setNome("")
    setDisciplinaId("")
    setData(dayjs(""))
    setHora("")
    setPeso("")
    setTipo("")
  }

  async function getDisciplinas() {
    try {
      
    
    const response = await api.get("/disciplinas", {
      params: {
        docenteId: localStorage.getItem("docenteId"),
        pagina: 0,
        tamanho: 500
      }
    })

    const data = response.data
    setDisciplinas(data.content)

    } catch (error) {
      
    }
  }

  async function postExame() {

    setLoading(true)
    try {

      const formatDate = data.format("YYYY-MM-DD")
      const body = { nome: nome, disciplinaId: disciplinaId, data: formatDate, tipo: tipo, hora: hora, peso: peso }
      await api.post("/exames", body)
      limparCampos()
      toast.success("Exame salvo com sucesso!")
      handleClose()
      obterExames()
    } catch (error) {

    }
    setLoading(false)

  }


  async function atualizarExame() {

    setLoading(true)
    try {
      
      const formatDate = data.format("YYYY-MM-DD")
      const body = { nome: nome, disciplinaId: disciplinaId, data: formatDate, tipo: tipo, hora: hora, peso: peso }
      await api.put(`/exames/${exame.id}`, body)
      limparCampos()
      toast.success("Exame atualizado com sucesso!")
      handleClose()
      obterExames()
    } catch (error) {

    }
    setLoading(false)
  }

  async function getDocente() {
    try {
      const response = await api.get("/docentes/me")
      const data = response.data
      setDocente(data)
    } catch (error) {

    }
  }

  useEffect(() => {
    getDocente()
    if (atualizar) {

      setNome(exame.nome)
      setDisciplinaId(exame.disciplinaId)
      setData(dayjs(exame.data))
      setHora(exame.hora)
      setPeso(exame.peso)
      setTipo(exame.tipo)
    }
    getDisciplinas()
  }, [open])

  if(loading) {
    return(
      <CustomModal open={open} handleClose={() => {
        handleClose()
        limparCampos()
      }}>
        <Box sx={{display:'flex', justifyContent:'center', alignItems:'center'}}>

          <CircularProgress/>
        </Box>
      </CustomModal>
    )
  }

  return (
    <>
      <CustomModal open={open} handleClose={handleClose}>
        <Box sx={{ display: "flex", justifyContent: "end" }}>

          <IconButton onClick={handleClose}>
            <ClearIcon />
          </IconButton>
        </Box>
        <Box sx={{ display: "flex", justifyContent: "center", mb: 2 }}>
          <Typography variant='h6'>{atualizar ? 'Atualizar exame' : 'Marcar exame'}</Typography>
        </Box>
        <Grid container direction="column" spacing={3}>
          <Grid container direction="row" spacing={2}>
            <Grid size={12}>
              <TextField label="titulo" fullWidth defaultValue={nome} onChange={(e) => setNome(e.target.value)} />
            </Grid>

          </Grid>
          <Grid container direction="row">
            <Grid size={12}>
              {/* <TextField label="tipo" fullWidth defaultValue={tipo} onChange={(e) => setTipo(e.target.value)}/> */}
              <TextField label="tipo" fullWidth defaultValue={tipo} onChange={(e) => setTipo(e.target.value)} select>
                <MenuItem key={1} value="PROVA">PROVA</MenuItem>
                <MenuItem key={2} value="TRABALHO">TRABALHO</MenuItem>
              </TextField>
            </Grid>
            <Grid size={12}>
              <TextField label="peso" fullWidth defaultValue={peso} onChange={(e) => setPeso(e.target.value)} />
            </Grid>
          </Grid>
          <Grid container direction="row">
            <Grid size={12}>
              {/* <TextField label="data" fullWidth defaultValue={data} onChange={(e) => setData(e.target.value)}/> */}
              <LocalizationProvider dateAdapter={AdapterDayjs}>
                <DateField label="data" fullWidth value={data} onChange={(e) => setData(e)} format='DD/MM/YYYY' />
              </LocalizationProvider>

            </Grid>
            <Grid size={12}>
              <TextField label="hora" fullWidth defaultValue={hora} onChange={(e) => setHora(e.target.value)} />
            </Grid>
          </Grid>
          <Grid size={12}>
            <TextField label="disciplina" fullWidth select defaultValue={disciplinaId} onChange={(e) => setDisciplinaId(e.target.value)}>
              {disciplinas?.map(d => {
                return <MenuItem key={d.id} value={d.id}>{d.nome}</MenuItem>
              })}
            </TextField>
          </Grid>
        </Grid>
        <Box sx={{ display: "flex", justifyContent: "end", mt: 4 }}>
          <Button variant='contained' onClick={atualizar ? atualizarExame : postExame}>{atualizar ? 'Atualizar' : 'Salvar'}</Button>
        </Box>



      </CustomModal>
      
    </>
  )
}

export default FormExames