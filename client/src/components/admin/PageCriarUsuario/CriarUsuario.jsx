import { Box, Button, Grid, Paper, Typography, useScrollTrigger, useTheme } from '@mui/material'

import AccountCircleOutlinedIcon from '@mui/icons-material/AccountCircleOutlined';
import React, { useState } from 'react'
import FormAluno from '../PageAlunos/FormAluno';
import FormDocentes from '../PageDocentes/FormDocentes';
import FormUsuario from '../PageUsuarios/FormUsuario';

const CriarUsuario = () => {

    const [openUserForm, setOpenUserForm] = useState(false)
    const [openAlunoForm, setOpenAlunoForm] = useState(false)
    const [openDocenteForm, setOpenDocenteForm] = useState(false)

  return (
    <Box>
        <Typography variant='h5' sx={{p:2}}>Criar novo usuário</Typography>
        <Grid container direction='column'>
            <Grid container direction='row' spacing={2}>
                <Grid size={4}>
                    <Paper variant='outlined' sx={{p:2}}>
                        <Box sx={{display:'flex', justifyContent:'center', mb:2}}>
                            <AccountCircleOutlinedIcon sx={{height:100, width:100}}/>
                        </Box>
                        <Button fullWidth variant='contained' onClick={() => setOpenUserForm(true)}>Criar usuário admin</Button>
                    </Paper>
                </Grid>
                <Grid size={4}>
                    <Paper variant='outlined' sx={{p:2}}>
                        <Box sx={{display:'flex', justifyContent:'center', mb:2}}>
                            <AccountCircleOutlinedIcon sx={{height:100, width:100}}/>
                        </Box>
                        <Button fullWidth variant='contained' onClick={() => setOpenAlunoForm(true)}>Criar usuário aluno</Button>
                    </Paper>
                </Grid>
                <Grid size={4}>
                    <Paper variant='outlined' sx={{p:2}}>
                        <Box sx={{display:'flex', justifyContent:'center', mb:2}}>
                            <AccountCircleOutlinedIcon sx={{height:100, width:100}}/>
                        </Box>
                        <Button fullWidth variant='contained' onClick={() => setOpenDocenteForm(true)}>Criar usuário docente</Button>
                    </Paper>
                </Grid>
            </Grid>
        </Grid>

        <FormAluno open={openAlunoForm} handleClose={() => setOpenAlunoForm(false)} atualizar={false} obterAlunos={() => null}/>
        <FormDocentes open={openDocenteForm} handleClose={() => setOpenDocenteForm(false)} atualizar={false} obterDocentes={() => null}/>
        <FormUsuario open={openUserForm} handleClose={() => setOpenUserForm(false)} atualizar={false} obterUsuarios={() => null}/>
    </Box>
  )
}

export default CriarUsuario