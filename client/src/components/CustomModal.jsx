import Modal from '@mui/material/Modal'
import Paper from '@mui/material/Paper';
import React from 'react'
import Box from '@mui/material/Box';
import CircularProgress from '@mui/material/CircularProgress';

const CustomModal = ({open, handleClose,children, modalLoading}) => {

const style = {
  position: 'absolute',
  top: '50%',
  left: '50%',
  transform: 'translate(-50%, -50%)',
  width: 400,
  p: 4
};


  if(modalLoading) {
    <Box sx={{display:"flex", justifyContent:"center", alignItems:"center"}}>
        <CircularProgress/>
    </Box>
  }

  return (
    <Modal open={open} handleClose={handleClose}>

        <Paper sx={style}>
            {children}
        </Paper>
    </Modal>
  )
}

export default CustomModal