import { Box, Button, CircularProgress, Divider, Grid, IconButton, MenuItem, TextField, Typography } from '@mui/material'
import React, { useEffect, useState } from 'react'
import ClearIcon from '@mui/icons-material/Clear';
import CustomAlert from '../../CustomAlert';
import CustomModal from '../../CustomModal';
import api from '../../../services/api';
import { toast } from 'react-toastify';

const FormDisciplina = ({ open, handleClose, disciplina, atualizar, obterDisciplinas }) => {

  const [loading, setLoading] = useState(false)
  const [nome, setNome] = useState("")
  const [localizacao, setLocalizacao] = useState("")
  const [vagas, setVagas] = useState("")
  const [docenteId, setDocenteId] = useState("")
  const [departamentoId, setDepartamentoId] = useState("")
  const [listaHorarios, setListaHorarios] = useState([])

  const [diaSemana, setDiaSemana] = useState("")
  const [horario, setHorario] = useState("")
  const [periodo, setPeriodo] = useState("")
  const [docentes, setDocentes] = useState([])
  const [departamentos, setDepartamentos] = useState([])


  async function getDocentes(params) {
    setLoading(true)
    try {
      const response = await api.get("/docentes", {
        params: {
          tamanho: 500
        }
      })
      const data = response.data.content
      setDocentes(data)
    } catch (error) {

    }
    setLoading(false)
  }

  async function getDepartamentos(params) {
    setLoading(true)
    try {
      const response = await api.get("/departamentos", {
        params: {
          tamanho: 500
        }
      })
      const data = response.data.content
      setDepartamentos(data)
    } catch (error) {

    }
    setLoading(false)
  }

  async function salvarDisciplina() {
    setLoading(true)
    try {
      const body = { nome: nome, localizacao: localizacao, vagas: vagas, departamentoId: departamentoId, docenteId: docenteId, horarios: listaHorarios }
      await api.post("/disciplinas", body)
      toast.success("disciplina salva com sucesso!")
      limparCampos()
      handleClose()
      obterDisciplinas()
    } catch (error) {

    }
    setLoading(false)
  }

  async function atualizarDisciplina() {
    setLoading(true)
    try {
      const body = { nome: nome, localizacao: localizacao, vagas: vagas, departamentoId: departamentoId, docenteId: docenteId, horarios: listaHorarios }
      await api.put(`/disciplinas/${disciplina.id}`, body)
      toast.success("disciplina atualizada com sucesso!")
      limparCampos()
      handleClose()
      obterDisciplinas()

    } catch (error) {

    }
    setLoading(false)
  }

  function limparCampos() {
    setNome("")
    setLocalizacao("")
    setVagas("")
    setDepartamentoId("")
    setDocenteId("")
    setListaHorarios([])
  }

  useEffect(() => {
    if (atualizar) {

      setNome(disciplina.nome)
      setLocalizacao(disciplina.localizacao)
      setVagas(disciplina.vagas)
      setDepartamentoId(disciplina.departamentoId)
      setDocenteId(disciplina.docenteId)
      setListaHorarios(disciplina.horarios)
    } else {
      limparCampos()
    }

    
    getDepartamentos()
    getDocentes()
  }, [open])

  const [openHorarioForm, setOpenHorarioForm] = useState(false)
  if (loading) {
    return <CustomModal open={open} handleClose={() => {
      handleClose()
      limparCampos()
    }}>
      <Box sx={{ display: 'flex', justifyContent: 'center', alignItems: 'center' }}>
        <CircularProgress />
      </Box>
    </CustomModal>
  }

  return (
    <>
      <CustomModal open={open} handleClose={() => {
        handleClose()
        limparCampos()
      }}>
        <Box sx={{ display: "flex", justifyContent: "end" }}>

          <IconButton onClick={() => {
            handleClose()
            limparCampos()
          }}>
            <ClearIcon />
          </IconButton>
        </Box>
        <Box sx={{ display: "flex", justifyContent: "center", mb: 3 }}>
          <Typography variant='h5'>{atualizar ? 'Atualizar Disciplina' : 'Cadastrar nova disciplina'}</Typography>
        </Box>

        <Grid container direction='column' spacing={3}>
          <Grid container direction='row' spacing={2}>
            <Grid size={12}>
              <TextField label='Nome' fullWidth value={nome} onChange={(e) => setNome(e.target.value)} />
            </Grid>
          </Grid>
          <Grid container direction='row'>
            <Grid size={12}>
              <TextField label='Localização' fullWidth value={localizacao} onChange={(e) => setLocalizacao(e.target.value)} />
            </Grid>
          </Grid>
          <Grid container direction='row'>
            <Grid size={12}>
              <TextField label='Vagas' fullWidth value={vagas} onChange={(e) => setVagas(e.target.value)} hidden />
            </Grid>
          </Grid>
          <Grid container direction='row'>
            <Grid size={12}>
              <TextField label='Docente' fullWidth value={docenteId} onChange={(e) => setDocenteId(e.target.value)} hidden select>
                {docentes.map(docente => {
                  return <MenuItem key={docente.id} value={docente.id}>{docente.nome}</MenuItem>
                })}
              </TextField>
            </Grid>
          </Grid>
          <Grid container direction='row'>
            <Grid size={12}>
              <TextField label='Departamento' fullWidth value={departamentoId} onChange={(e) => setDepartamentoId(e.target.value)} hidden select>
                {departamentos.map(departamento => {
                  return <MenuItem key={departamento.id} value={departamento.id}>{departamento.nome}</MenuItem>
                })}
              </TextField>
            </Grid>
          </Grid>

        </Grid>

        <Box >
          <Typography variant='h6' sx={{ mb: 2, mt: 2 }}>Horários</Typography>
          {listaHorarios?.map((h, index) => {
            return <Box>
              <Box sx={{ display: 'flex', justifyContent: 'space-around', alignItems: 'center' }}>
                <Typography variant='body1'>{h.diaSemana}</Typography>
                <Typography variant='body1'>{h.horario}</Typography>
                <IconButton onClick={() => setListaHorarios(listaHorarios.filter((_, i) => i !== index))}><ClearIcon /></IconButton>
              </Box>
              <Divider sx={{ mb: 2 }} />
            </Box>
          })}
          <Box sx={{ display: 'flex', justifyContent: 'center' }}>
            <Button variant='outlined' onClick={() => setOpenHorarioForm(true)}>Add Horário</Button>
          </Box>
        </Box>

        <Box sx={{ display: "flex", justifyContent: "end", mt: 2 }}>
          <Button variant='contained' onClick={atualizar ? atualizarDisciplina : salvarDisciplina}>{atualizar ? 'Atualizar' : 'Salvar'}</Button>
        </Box>

        <CustomModal open={openHorarioForm} handleClose={() => setOpenHorarioForm(false)}>
          <Box sx={{ display: 'flex', justifyContent: 'center' }}>
            <Typography variant='h6' sx={{ flex: 1 }}>Add horário</Typography>
            <IconButton onClick={() => setOpenHorarioForm(false)}>
              <ClearIcon />
            </IconButton>
          </Box>
          <TextField select label='Dia da semana' value={diaSemana} onChange={(e) => setDiaSemana(e.target.value)} fullWidth sx={{ mt: 2 }}>
            <MenuItem key={1} value='SEGUNDA'>SEGUNDA</MenuItem>
            <MenuItem key={2} value='TERCA'>TERCA</MenuItem>
            <MenuItem key={3} value='QUARTA'>QUARTA</MenuItem>
            <MenuItem key={4} value='QUINTA'>QUINTA</MenuItem>
            <MenuItem key={5} value='SEXTA'>SEXTA</MenuItem>
          </TextField>
          <TextField select label='horário' value={horario} onChange={(e) => setHorario(e.target.value)} fullWidth sx={{ mt: 2 }}>
            <MenuItem key={1} value={'08:00'}>08:00</MenuItem>
            <MenuItem key={2} value={'10:00'}>10:00</MenuItem>
            <MenuItem key={3} value={'14:00'}>14:00</MenuItem>
            <MenuItem key={4} value={'16:00'}>16:00</MenuItem>
            <MenuItem key={5} value={'18:00'}>18:00</MenuItem>
            <MenuItem key={6} value={'20:00'}>20:00</MenuItem>
          </TextField>
          <TextField select label='Periodo' value={periodo} onChange={(e) => setPeriodo(e.target.value)} fullWidth sx={{ mt: 2 }}>
            <MenuItem key={1} value={'MATUTINO'}>MATUTINO</MenuItem>
            <MenuItem key={2} value={'VESPERTINO'}>VESPERTINO</MenuItem>
          </TextField>
          <Box sx={{ display: 'flex', justifyContent: 'end', mt: 2 }}>
            <Button variant='contained' onClick={() => setListaHorarios(prevHorarios => [...prevHorarios, { diaSemana: diaSemana, horario: horario, periodo: periodo }])}>Adicionar</Button>
          </Box>
        </CustomModal>
      </CustomModal>
    </>
  )
}

export default FormDisciplina