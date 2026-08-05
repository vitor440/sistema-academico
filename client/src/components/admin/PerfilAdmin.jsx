import { Avatar, Box, Button, Grid, Paper, Typography } from '@mui/material'
import AccountCircleIcon from '@mui/icons-material/AccountCircle';
import EmailIcon from '@mui/icons-material/Email';
import React, { useState } from 'react'
import FormUsuario from './PageUsuarios/FormUsuario';
import { useNavigate } from 'react-router-dom';

const PerfilAdmin = ({dadosPessoais}) => {
  const [open, setOpen] = useState(false)
  const navigate = useNavigate()
  return (
    <Box>
            <Typography variant='h5' sx={{mb:2}}>Dados Pessoais</Typography>
            <Box sx={{display:'flex', alignItems:'center', mb:3}}>
                <Avatar  sx={{height:"100px", width:"100px", mb:2, backgroundColor:"#3fb566", fontSize:"40px"}}>
                    {dadosPessoais?.username.charAt(0)}
                </Avatar>
                <Box sx={{display:'flex', flexDirection:'column', ml:4}}>
                    <Box>
                        <Typography variant='h5'>{dadosPessoais?.username}</Typography>

                    </Box>
                    <Box sx={{display:'flex', justifyContent:'space-between', mt:2}}>
                        <Box sx={{display:'flex'}}>
                            <AccountCircleIcon/>
                            <Typography>Admin</Typography>
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
                <FormUsuario open={open} handleClose={() => setOpen(false)} usuario={dadosPessoais} atualizar={true} obterUsuarios={() => navigate("/logout")}/>
            </Box>
            <Grid container direction='column' spacing={1}>
                <Grid container direction='row' spacing={2}>
                <Grid size={6} >
                    <Paper variant='outlined' sx={{p:2, height:120}}>
                        <Box sx={{display:'flex', flexDirection:'column', justifyContent:'start'}}>
                            <Typography variant='body2'>username</Typography>
                            <Typography variant='h6'>{dadosPessoais?.username}</Typography>
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
                            <Typography variant='body2'>Roles</Typography>
                            <Typography variant='h6'>ADMIN</Typography>
                        </Box>
                    </Paper>
                </Grid>
                </Grid>
                </Grid>
            </Box>
  )
}

export default PerfilAdmin