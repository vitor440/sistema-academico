import Grid from '@mui/material/Grid'
import React, { useState } from 'react'
import { useNavigate } from 'react-router-dom'
import CardContent from '@mui/material/CardContent'
import Box from '@mui/material/Box'
import Typography from '@mui/material/Typography'
import Card from '@mui/material/Card'
import IconButton from '@mui/material/IconButton'
import EditOutlinedIcon from '@mui/icons-material/EditOutlined';
import DeleteOutlineOutlinedIcon from '@mui/icons-material/DeleteOutlineOutlined';
import { Button } from '@mui/material'
import api from '../../services/api'
import DeleteOptions from '../DeleteOptions'
import FormExames from './PageAddExames/FormExames'
import { toast } from 'react-toastify'

const ExameCard = ({exame, link, obterExames}) => {

    const navigate = useNavigate()
    const [deleteOptions, setDeleteOptions] = useState(false)
    const [open, setOpen] = useState(false)

    async function deletarExame() {
        try {
            await api.delete(`/exames/${exame.id}`)
            obterExames()
            setDeleteOptions(false)
            toast.success("Exame Deletado!")
        } catch (error) {
            
        }
    }

  return (
    <Grid size={3}>
        <Card sx={{height:"290px", justifyContent:"center", 
        "&:hover": {
            borderColor:"#3fb566",
            cursor: "pointer"
        }
    }} elevation={3} variant='outlined' onClick={link ? () => navigate(link) : null}>
        <CardContent>
            <Box sx={{display:"flex", flexDirection:"column", justifyContent:"center"}}>
                <Typography variant='h5' sx={{mb: 3, height:65}}> {exame.nome}</Typography>
                <Typography variant='h6'> Data: {exame.data}</Typography>
                <Typography variant='h6'> Hora: {exame.hora}</Typography>
                <Typography variant='h6'> Tipo: {exame.tipo}</Typography>
                <Typography variant='h6'> Peso: {exame.peso}</Typography>
                <Box sx={{display:'flex', justifyContent:'space-between', mt:1}}>
                    <Box sx={{display:'flex', justifyContent:'start'}}>
                        <IconButton onClick={() => setOpen(true)}>
                            <EditOutlinedIcon/>
                        </IconButton>

                        <IconButton onClick={() => setDeleteOptions(true)}>
                            <DeleteOutlineOutlinedIcon/>
                        </IconButton>
                    </Box>
                </Box>
            </Box>
        </CardContent>
        </Card>
        <DeleteOptions 
        open={deleteOptions} 
        handleClose={() => setDeleteOptions(false)} 
        deletar={deletarExame} mensagem={"Docente deletado com sucesso!"} mensagemErro={"Erro ao deletar docente!"}/>
        <FormExames open={open} handleClose={() => setOpen(false)} atualizar={true} exame={exame} obterExames={obterExames}/>
    </Grid>
  )
}

export default ExameCard