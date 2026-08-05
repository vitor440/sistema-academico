import { Avatar, Box, Button, Card, CardContent, CircularProgress, Grid, Typography } from '@mui/material'
import React, { useContext, useEffect, useState } from 'react'
import api from '../../../services/api'
import { GlobalContext } from '../../../context/GlobalContext'
import CustomAlert from '../../CustomAlert'
import CustomBackDrop from '../../CustomBackDrop'
import { toast } from 'react-toastify'

const SolicitacaoMatricula = () => {

  const [matriculas, setMatriculas] = useState([])
  const [disciplinas, setDisciplinas] = useState([])
  const { ano, setAno, periodo, setPeriodo } = useContext(GlobalContext)
  const [open, setOpen] = useState(false)
  const [loading, setLoading] = useState(false)

  function verificaSeEstaMatriculado(disciplina) {
    for (let matricula of matriculas) {
      if (matricula.disciplinaId === disciplina.id) {
        return true
      }
    }

    return false
  }



  async function getMatriculas() {
    try {
      const response = await api.get("/matriculas", {
        params: {
          tamanho: 100,
          semestre: periodo,
          ano: ano
        }
      })
      const data = response.data.content
      setMatriculas(data)
    } catch (error) {

    }
  }

  async function getDisciplinas() {
    setLoading(true)
    try {
      const response = await api.get("/disciplinas", {
        params: {
          tamanho: 100
        }
      })
      const data = response.data.content
      setDisciplinas(data)
    } catch (error) {

    }
    setLoading(false)
  }

  async function solicitarMatricula(disciplinaId) {
    setOpen(true)
    try {
      const body = { alunoId: localStorage.getItem("alunoId"), disciplinaId: disciplinaId }
      await api.post("/matriculas", body)
      toast.success("Matricula solicitada!")
      getDisciplinas()
      getMatriculas()
      setOpen(false)
    } catch (error) {

    }
  }

  async function cancelarMatricula(disciplinaId) {
    setOpen(true)
    try {
      const matricula = matriculas.find(m => m.disciplinaId === disciplinaId)
      await api.delete(`/matriculas/${matricula.id}`)
      toast.success("Matricula cancelada!")
      getDisciplinas()
      getMatriculas()
      setOpen(false)
    } catch (error) {

    }
    setOpen(false)
  }


  function avatarName(nameList) {
    return nameList.length > 1 ? nameList[0][0] + nameList[nameList.length - 1][0] : nameList[0][0]
  }

  useEffect(() => {
    getDisciplinas()
    getMatriculas()
  }, [])

  if (loading) {
      return (
        <Box sx={{ display: 'flex', justifyContent: 'center', alignItems: "center" }}>
          <CircularProgress />
        </Box>
      )
    }

  return (
    <Box>
      <Typography variant='h5' sx={{ mb: 2 }}>Solicitação de matrícula</Typography>

      <Grid container direction='column' spacing={2}>
        <Grid container direction='row'>
          {disciplinas?.map(d => {
            return <Grid size={3}>
              <Card sx={{
                height: "230px", justifyContent: "center",
                "&:hover": {
                  borderColor: "#3fb566",
                  cursor: "pointer"
                }
              }} elevation={3} variant='outlined' >
                <CardContent>
                  <Box sx={{ display: "flex", flexDirection: "column", justifyContent: "center", alignItems: "center" }}>
                    <Avatar sx={{ height: "100px", width: "100px", mb: 2, backgroundColor: "#3fb566", fontSize: "40px" }}>
                      {avatarName(d.nome.split(' '))}
                    </Avatar>
                    <Typography variant='h5'> {d.nome}</Typography>
                    <Button
                      variant='contained'
                      fullWidth
                      sx={{ mt: 2 }}
                      color={verificaSeEstaMatriculado(d) ? 'error' : 'primary'}
                      onClick={() => verificaSeEstaMatriculado(d) ? cancelarMatricula(d.id) : solicitarMatricula(d.id)}>{verificaSeEstaMatriculado(d) ? 'Cancelar Matricula' : 'Solicitar matricula'}
                    </Button>
                  </Box>
                </CardContent>
              </Card>
            </Grid>
          })}
        </Grid>
      </Grid>

      <CustomBackDrop open={open} handleClose={() => setOpen(false)} />

    </Box>
  )
}

export default SolicitacaoMatricula