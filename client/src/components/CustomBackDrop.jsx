import { Backdrop, CircularProgress } from '@mui/material'
import React from 'react'

const CustomBackDrop = ({open, handleClose}) => {
  return (
    <div>
      <Backdrop
        sx={(theme) => ({ color: '#fff', zIndex: theme.zIndex.drawer + 1 })}
        open={open}
        onClick={handleClose}
      >
        <CircularProgress color="inherit" />
      </Backdrop>
    </div>
  )
}

export default CustomBackDrop