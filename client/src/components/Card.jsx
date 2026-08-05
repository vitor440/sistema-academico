import Paper from '@mui/material/Paper'
import React from 'react'
import Box from '@mui/material/Box'
import Typography from '@mui/material/Typography'

const Card = ({Icone, titulo, content, cor}) => {
  return (
    <Paper variant='outlined' sx={{p:2}} elevation={0}>
        <Box sx={{display: "flex", alignItems:"center", height:"110px"}}>
            <Box sx={{mr:2}}><Icone fontSize="40px" color={cor}/></Box>
            <Box sx={{display:"flex", flexDirection:"column"}}>
                <Typography variant='h5'>{titulo}</Typography>
                <Typography variant='h4'>{content}</Typography>
            </Box>
        </Box>
    </Paper>
  )
}

export default Card