import React, { useEffect, useState } from 'react'
import CustomModal from '../../CustomModal'
import Box from '@mui/material/Box'
import Typography from '@mui/material/Typography'
import TextField from '@mui/material/TextField'
import Button from '@mui/material/Button'
import IconButton from '@mui/material/IconButton';
import ClearIcon from '@mui/icons-material/Clear';
import api from '../../../services/api'
import CustomAlert from '../../CustomAlert'
import CircularProgress from '@mui/material/CircularProgress'
import { toast } from 'react-toastify'

const FormDepartamento = ({open, handleClose, departamento, atualizar, obterDepartamentos}) => {

    const [loading, setloading] = useState(false)
    const [alert, setAlert] = useState(false)
    const [formNome, setFormNome] = useState("")
    const [formBloco, setFormBloco] = useState("")
    const [formSigla, setFormSigla] = useState("")
    const [mensagem, setMensagem] = useState("")

    function limparCampos() {
        setFormNome("")
        setFormBloco("")
        setFormSigla("")
    }

    async function salvar() {
        setloading(true)
        try {
          
          const body = {nome: formNome, bloco: formBloco, sigla: formSigla}
          await api.post("/departamentos", body)
          toast.success("Departamento salvo com sucesso!")
          setFormNome("")
          setFormBloco("")
          setFormSigla("")
          obterDepartamentos()
          handleClose()
          limparCampos()

        } catch (error) {
        }
        setloading(false)
      }

      async function atualizarDepartamento() {
        setloading(true)
        try {
          const body = {nome: formNome, bloco: formBloco, sigla: formSigla}
          await api.put(`/departamentos/${departamento.id}`, body)
          toast.success("Departamento atualizado com sucesso!")
          setFormNome("")
          setFormBloco("")
          setFormSigla("")
          obterDepartamentos()
          handleClose()
          limparCampos()

        } catch (error) {
        }

        setloading(false)
      }


    useEffect(() => {
        console.log("dentro do useEffect")
        if(atualizar) {
            departamento?.nome? setFormNome(departamento.nome) : ""
            departamento?.bloco? setFormBloco(departamento.bloco) : ""
            departamento?.sigla? setFormSigla(departamento.sigla) : ""
        }
    }, [open])

  if(loading) {
    return <CustomModal open={open} handleClose={() => {
        handleClose()
        limparCampos()
      }}>
        <Box sx={{display:'flex', justifyContent:'center', alignItems:'center'}}>
          <CircularProgress/>
        </Box>
      </CustomModal>
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
            <Typography variant='h6'>{atualizar ? 'atualizar departamento' : 'Cadastro de departamento'}</Typography>
          </Box>

          <TextField label='Nome' fullWidth sx={{mb:2}} value={formNome} onChange={(e) => setFormNome(e.target.value)}/>
          <TextField label='Bloco' fullWidth sx={{mb:2}} value={formBloco} onChange={(e) => setFormBloco(e.target.value)}/>
          <TextField label='Sigla' fullWidth sx={{mb:2}} value={formSigla} onChange={(e) => setFormSigla(e.target.value)}/>

          <Box sx={{display:"flex", justifyContent:"end", mt:2}}>
            <Button variant='contained' onClick={atualizar ? atualizarDepartamento : salvar}>{atualizar ? 'Atualizar' : 'Salvar'}</Button>
          </Box>

          
    </CustomModal>
    </>
  )
}

export default FormDepartamento