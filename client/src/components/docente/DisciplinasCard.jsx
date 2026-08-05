import Avatar from '@mui/material/Avatar'
import Box from '@mui/material/Box'
import Button from '@mui/material/Button'
import Card from '@mui/material/Card'
import CardContent from '@mui/material/CardContent'
import Grid from '@mui/material/Grid'
import Typography from '@mui/material/Typography'
import React from 'react'
import { useNavigate } from 'react-router-dom'

const DisciplinasCard = ({disciplina, buttonText, funcao}) => {
    console.log(disciplina)

    const navigate = useNavigate()
    function stringToColor(string) {
    let hash = 0;
    let i;

    for (i = 0; i < string.length; i += 1) {
        hash = string.charCodeAt(i) + ((hash << 5) - hash);
    }

    let color = '#';

    for (i = 0; i < 3; i += 1) {
        const value = (hash >> (i * 8)) & 0xff;
        color += `00${value.toString(16)}`.slice(-2);
    }

    return color;
}  

  function stringAvatar(name) {
    if(name) {
    return {
        sx: {
        bgcolor: stringToColor(name),
        },
        children: `${name.split(' ')[0][0]}${name.split(' ')[1][0]}`,
    };
}
    }

    function avatarName(nameList) {
        return nameList.length > 1 ? nameList[0][0] + nameList[nameList.length - 1][0] : nameList[0][0]
    }

  return (
    <Grid size={3}>
        <Card sx={{height:"230px", justifyContent:"center", 
        "&:hover": {
            borderColor:"#3fb566",
            cursor: "pointer"
        }
    }} elevation={3} variant='outlined' >
        <CardContent>
            <Box sx={{display:"flex", flexDirection:"column", justifyContent:"center", alignItems:"center"}}>
                <Avatar  sx={{height:"100px", width:"100px", mb:2, backgroundColor:"#3fb566", fontSize:"40px"}}> 
                    {avatarName(disciplina.nome.split(' '))}
                </Avatar>
                <Typography variant='h5'> {disciplina?.nome}</Typography>
                <Button variant='contained' fullWidth sx={{mt:2}} onClick={funcao}>{buttonText}</Button>
            </Box>
        </CardContent>
        </Card>
    </Grid>
  )
}

export default DisciplinasCard