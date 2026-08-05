import React, { useState } from 'react'
import CustomModal from './CustomModal'
import Box from '@mui/material/Box'
import IconButton from '@mui/material/IconButton'
import ClearIcon from '@mui/icons-material/Clear';
import Typography from '@mui/material/Typography';
import TextField from '@mui/material/TextField';
import Button from '@mui/material/Button';
import CustomAlert from './CustomAlert';
import { toast } from 'react-toastify';

const DeleteOptions = ({open, handleClose, deletar, mensagem, mensagemErro}) => {

  const [openAlert, setOpenAlert] = useState(false)
  const [openErroAlert, setOpenErroAlert] = useState(false)
  const [loading, setLoading] = useState(false)

  function deletarItem() {
    setLoading(true)
    try {
      deletar()
      
    } catch (error) {
    }
    setLoading(false)
  }

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
      <CustomModal open={open} handleClose={handleClose}>
            <Box sx={{display:"flex", justifyContent:"end"}}>
                          
                  <IconButton onClick={handleClose}>
                    <ClearIcon/>
                  </IconButton>
              </Box>
            <Box sx={{display:'flex', justifyContent:'center'}}>
              <Typography variant='h6' sx={{mb:2}}>Desejar deletar este item ?</Typography>
            </Box>
            <Box sx={{display:'flex', justifyContent:'center'}}>
              <Button onClick={handleClose} variant='contained' sx={{mr:1}} color='error'>Não</Button>
              <Button onClick={deletarItem} variant='contained' sx={{ml:1}}>Sim</Button>
            </Box>
        </CustomModal>

    </>
      
  )
}

export default DeleteOptions