import React, { useEffect } from 'react'
import "./PageHorarios.css"
import { EventCalendar } from '@mui/x-scheduler';
import { useState } from 'react';
import FullCalendar from '@fullcalendar/react';
import dayGridPlugin from '@fullcalendar/daygrid'
import timeGridPlugin from '@fullcalendar/timegrid'
import Box from '@mui/material/Box';
import Typography from '@mui/material/Typography';

const PageHorarios = () => {
    
    const diasSemana = [
        "DOMINGO",
        "SEGUNDA",
        "TERCA",
        "QUARTA",
        "QUINTA",
        "SEXTA",
        "SABADO"
    ]

    const headerConfig = {
    left: "",
    center: "title"
        
    }

    const plusHours = (hora) => {
        return String(parseInt(hora.split(":")[0]) + 2) +":00"
    }

    useEffect(() => {
        
        async function getHorarios() {
            const listaEventos = []
            
            try {
                const res = await fetch("http://localhost:3000/horarios");
                const data = await res.json()
                console.log(data)

                data.map(horario => {
                    const novoEvento = {
                        id: horario.id,
                        title: horario.disciplina,
                        daysOfWeek: diasSemana.indexOf(horario.diaSemana).toString(),
                        startTime: horario.hora,
                        endTime: plusHours(horario.hora)
                    }
                listaEventos.push(novoEvento)
                
                })
                
                setEventos(listaEventos)
            } catch (error) {
            }
            

        } 
        getHorarios()
        
    }, [])

    const [eventos, setEventos] = useState([]);

  return (
    // <div id="main-container-calendar">
    //     <h2>Horários disciplinas</h2>
    //     <div id='calendario-div'>
    //         <FullCalendar 
    //         plugins={[timeGridPlugin]} 
    //         height="600px" 
    //         initialView="timeGridWeek" 
    //         events={eventos} 
    //         headerToolbar={headerConfig}/>
    //     </div>
    // </div>

    <Box sx={{p:2}}>
        <Typography variant='h4'>Horários Disciplinas</Typography>
        <Box sx={{width:"100%", flex: 1, minWidth:0}}>
            <FullCalendar 
             plugins={[timeGridPlugin]} 
             height="600px" 
             initialView="timeGridWeek" 
             events={eventos} 
             headerToolbar={headerConfig}/>

        </Box>
    </Box>
    
  )
}

export default PageHorarios