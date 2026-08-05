
import React, { useState } from 'react'
import { Avatar, Box, Button, Grid, Paper, Typography } from '@mui/material'
import AccountCircleIcon from '@mui/icons-material/AccountCircle';
import EmailIcon from '@mui/icons-material/Email';
import FormAluno from '../admin/PageAlunos/FormAluno';
import { useNavigate } from 'react-router-dom';

const PerfilAluno = ({dadosPessoais}) => {
  const [open, setOpen] = useState(false)
  const navigate = useNavigate()
  return (
    <Box>
            <Typography variant='h5' sx={{mb:2}}>Dados Pessoais</Typography>
            <Box sx={{display:'flex', alignItems:'center', mb:3}}>
                <Avatar  sx={{height:"100px", width:"100px", mb:2, backgroundColor:"#3fb566", fontSize:"40px"}}>
                    {dadosPessoais?.nome.charAt(0)}
                </Avatar>
                <Box sx={{display:'flex', flexDirection:'column', ml:4}}>
                    <Box>
                        <Typography variant='h5'>{dadosPessoais?.nome}</Typography>

                    </Box>
                    <Box sx={{display:'flex', justifyContent:'space-between', mt:2}}>
                        <Box sx={{display:'flex'}}>
                            <AccountCircleIcon/>
                            <Typography>Aluno</Typography>
                        </Box>
                        <Box sx={{display:'flex', ml:2}}>
                            <EmailIcon/>
                            <Typography>{dadosPessoais?.email}</Typography>
                        </Box>
                    </Box>
                </Box>
            </Box>
            <Box sx={{display:'flex', justifyContent:'center'}}>
                <Button variant='outlined' sx={{mb:2}} onClick={() => setOpen(true)}>Editar Perfil</Button>
                <FormAluno open={open} handleClose={() => setOpen(false)} aluno={dadosPessoais} atualizar={true} obterAlunos={() => navigate("/logout")}/>
            </Box>
            <Grid container direction='column' spacing={1}>
                <Grid container direction='row' spacing={2}>
                <Grid size={6} >
                    <Paper variant='outlined' sx={{p:2, height:120}}>
                        <Box sx={{display:'flex', flexDirection:'column', justifyContent:'start'}}>
                            <Typography variant='body2'>Matricula</Typography>
                            <Typography variant='h6'>{dadosPessoais?.matricula}</Typography>
                        </Box>
                    </Paper>
                </Grid>
                <Grid size={6} >
                <Paper variant='outlined' sx={{p:2, height:120}}>
                    <Box sx={{display:'flex', flexDirection:'column', justifyContent:'start'}}>
                        <Typography variant='body2'>cpf</Typography>
                        <Typography variant='h6'>{dadosPessoais?.cpf}</Typography>
                    </Box>
                </Paper>
                </Grid>
                </Grid>
                <Grid container direction='row' spacing={2}>
                <Grid size={6} >
                <Paper variant='outlined' sx={{p:2, height:120}}>
                    <Box sx={{display:'flex', flexDirection:'column', justifyContent:'start'}}>
                        <Typography variant='body2'>nome</Typography>
                        <Typography variant='h6'>{dadosPessoais?.nome}</Typography>
                    </Box>
                </Paper>
                </Grid>
                <Grid size={6} >
                <Paper variant='outlined' sx={{p:2, height:120}}>
                    <Box sx={{display:'flex', flexDirection:'column', justifyContent:'start'}}>
                        <Typography variant='body2'>email</Typography>
                        <Typography variant='h6'>{dadosPessoais?.email}</Typography>
                    </Box>
                </Paper>
                </Grid>
                </Grid>
                <Grid container direction='row' spacing={2}>
                <Grid size={6} >
                <Paper variant='outlined' sx={{p:2, height:120}}>
                    <Box sx={{display:'flex', flexDirection:'column', justifyContent:'start'}}>
                        <Typography variant='body2'>telefone</Typography>
                        <Typography variant='h6'>{dadosPessoais?.telefone}</Typography>
                    </Box>
                </Paper>
                </Grid>
                <Grid size={6} >
                <Paper variant='outlined' sx={{p:2, height:120}}>
                    <Box sx={{display:'flex', flexDirection:'column', justifyContent:'start'}}>
                        <Typography variant='body2'>Data de nascimento</Typography>
                        <Typography variant='h6'>{dadosPessoais?.dataNascimento}</Typography>
                    </Box>
                </Paper>
                </Grid>
                </Grid>
                <Grid container direction='row' spacing={2}>
                <Grid size={6} >
                <Paper variant='outlined' sx={{p:2, height:120}}>
                    <Box sx={{display:'flex', flexDirection:'column', justifyContent:'start'}}>
                        <Typography variant='body2'>curso id</Typography>
                        <Typography variant='h6'>{dadosPessoais?.cursoId}</Typography>
                    </Box>
                </Paper>
                </Grid>
                <Grid size={6} >
                <Paper variant='outlined' sx={{p:2, height:120}}>
                    <Box sx={{display:'flex', flexDirection:'column', justifyContent:'start'}}>
                        <Typography variant='body2'>usuario id</Typography>
                        <Typography variant='h6'>{dadosPessoais?.usuarioId}</Typography>
                    </Box>
                </Paper>
                </Grid >
                </Grid>
                </Grid>
            </Box>
  )
}

export default PerfilAluno