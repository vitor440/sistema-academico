import Alert from '@mui/material/Alert'
import Snackbar from '@mui/material/Snackbar'
import React from 'react'

const CustomAlert = ({severity,open, handleClose, vertical, horizontal, mensagem}) => {
  return (
    
    <Snackbar open={open} onClose={handleClose} anchorOrigin={{vertical, horizontal}} key={vertical + horizontal} autoHideDuration={6000}>
            <Alert onClose={handleClose} variant='filled' severity={severity} sx={{width:"100%"}}>
                {mensagem}
            </Alert>
    </Snackbar>
  )
}

export default CustomAlert