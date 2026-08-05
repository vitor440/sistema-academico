import React, { useEffect, useState } from 'react'
import CustomModal from '../../CustomModal'
import Box from '@mui/material/Box'
import IconButton from '@mui/material/IconButton'
import ClearIcon from '@mui/icons-material/Clear';
import Typography from '@mui/material/Typography';
import TextField from '@mui/material/TextField';
import Button from '@mui/material/Button';
import api from '../../../services/api';
import { useNavigate } from 'react-router-dom';
import Grid from '@mui/material/Grid';
import MenuItem from '@mui/material/MenuItem';
import CircularProgress from '@mui/material/CircularProgress';
import { toast } from 'react-toastify';

const FormCurso = ({open, handleClose, curso, atualizar, obterCursos}) => {

  const [loading, setloading] = useState(false)
  const [mensagem, setMensagem] = useState("")
  const [formNome, setFormNome] = useState("")
  const [formArea, setFormArea] = useState("")
  const [formPeriodo, setFormPeriodo] = useState("")
  const [formQuantidadePeriodos, setFormQuantidadePeriodos] = useState("")
  const [formDepartamentoId, setFormDepartamentoId] = useState("")
  const navigate = useNavigate()

  const [departamentos, setDepartamentos] = useState([])

  function limparCampos() {
    setFormNome("")
    setFormArea("")
    setFormPeriodo("")
    setFormQuantidadePeriodos("")
    setFormDepartamentoId("")
  }

  async function getDepartamentos() {
    setloading(true)
    try {
      const response = await api.get("/departamentos", {
        params: {
          tamanho: 100
        }
      })
      const data = response.data.content
      setDepartamentos(data)
    } catch (error) {
    }
    setloading(false)
  }


  async function salvarCurso() {
    setloading(true)
    try {
      const body = {nome: formNome, area: formArea, periodo: formPeriodo, quantidadePeriodos: formQuantidadePeriodos, departamentoId: formDepartamentoId}
      await api.post("/cursos", body)
      toast.success("Curso salvo com sucesso!")
      obterCursos()
      limparCampos()
      handleClose()
    } catch (error) {
    }
    setloading(false)
  }

  async function atualizarCurso() {
    setloading(true)
    try {
      const body = {nome: formNome, area: formArea, periodo: formPeriodo, quantidadePeriodos: formQuantidadePeriodos, departamentoId: formDepartamentoId}
      await api.put(`/cursos/${curso.id}`, body)
      toast.success("Curso atualizado com sucesso!")
      obterCursos()
      limparCampos()
      handleClose()
    } catch (error) {
      
    }
    setloading(false)
  }

  useEffect(() => {
    getDepartamentos()
    
    if(atualizar) {
      setFormNome(curso.nome)
      setFormArea(curso.area)
      setFormPeriodo(curso.periodo)
      setFormQuantidadePeriodos(curso.quantidadePeriodos)
      setFormDepartamentoId(curso.departamentoId)
    }

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
    <CustomModal open={open} handleClose={() =>{
        handleClose()
        limparCampos()
    }}>
            <Box sx={{display:"flex", justifyContent:"end"}}>
                        
                <IconButton onClick={() => {
                    handleClose()
                    limparCampos()
                }}>
                  <ClearIcon/>
                </IconButton>
            </Box>
            <Box sx={{display:"flex", justifyContent:"center", mb:3}}>
            <Typography variant='h6'>{atualizar ? 'atualizar curso' : 'Cadastro de curso'}</Typography>
          </Box>

          <Grid container direction='column' spacing={3}>
              <Grid container direction='row' spacing={2}>
                  <Grid size={12}>
                    <TextField label='nome' fullWidth value={formNome} onChange={(e) => setFormNome(e.target.value)}/>
                  </Grid>
              </Grid>
              <Grid container direction='row'>
                  <Grid size={6}>
                    <TextField label='Área' fullWidth value={formArea} onChange={(e) => setFormArea(e.target.value)} select>
                      <MenuItem key={1} value='EXATAS'>EXATAS</MenuItem>
                      <MenuItem key={1} value='HUMANAS'>HUMANAS</MenuItem>
                      <MenuItem key={1} value='ENGENHARIAS'>ENGENHARIAS</MenuItem>
                      <MenuItem key={1} value='LETRAS'>LETRAS</MenuItem>
                      <MenuItem key={1} value='CIENCIAS_SOCIAIS'>CIENCIAS_SOCIAIS</MenuItem>
                      <MenuItem key={1} value='CIENCIAS_BIOLOGICAS'>CIENCIAS_BIOLOGICAS</MenuItem>
                      <MenuItem key={1} value='CIENCIAS_AGRARIAS'>CIENCIAS_AGRARIAS</MenuItem>
                    </TextField>
                  </Grid>
                  <Grid size={6}>
                    <TextField label='Periodo' fullWidth value={formPeriodo} onChange={(e) => setFormPeriodo(e.target.value)} select>
                      <MenuItem key={1} value='INTEGRAL'>INTEGRAL</MenuItem>
                      <MenuItem key={2} value='MATUTINO'>MATUTINO</MenuItem>
                      <MenuItem key={3} value='VESPERTINO'>VESPERTINO</MenuItem>
                    </TextField>
                  </Grid>
              </Grid>
              <Grid container direction='row'>
                  <Grid size={6}>
                    <TextField label='Quantidade periodos' fullWidth value={formQuantidadePeriodos} onChange={(e) => setFormQuantidadePeriodos(e.target.value)} select>
                      <MenuItem key={1} value={6}>6</MenuItem>
                      <MenuItem key={2} value={8}>8</MenuItem>
                      <MenuItem key={3} value={10}>10</MenuItem>
                    </TextField>
                  </Grid>
                  <Grid size={6}>
                    <TextField label='departamento' fullWidth value={formDepartamentoId} onChange={(e) => setFormDepartamentoId(e.target.value)} select> 
                      {departamentos?.map(departamento => {
                        return <MenuItem key={departamento.key} value={departamento.id}>{departamento.nome}</MenuItem>
                      })}
                    </TextField>
                  </Grid>
              </Grid>
          </Grid>

          <Box sx={{display:"flex", justifyContent:"end", mt:2}}>
            <Button variant='contained' onClick={atualizar ? atualizarCurso : salvarCurso}>{atualizar ? 'Atualizar' : 'Salvar'}</Button>
          </Box>

          
    </CustomModal>
    </>
  )
}

export default FormCurso