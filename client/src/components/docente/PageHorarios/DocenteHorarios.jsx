import React, { useContext, useEffect, useState } from 'react'
import { StandaloneWeekView } from '@mui/x-scheduler/week-view';
import { pt } from 'date-fns/locale/pt'
import dayjs from 'dayjs';
import { Box, Button, MenuItem, TextField, Typography } from '@mui/material';
import { GlobalContext } from '../../../context/GlobalContext';
import api from '../../../services/api';

const DocenteHorarios = () => {

  const { ano, setAno, periodo, setPeriodo } = useContext(GlobalContext)

  const [horarios, setHorarios] = useState([])

  function converteDiaSemana(diaSemana) {
    switch (diaSemana) {
      case 'DOMINGO':
        return 0
      case 'SEGUNDA':
        return 1
      case 'TERCA':
        return 2
      case 'QUARTA':
        return 3
      case 'QUINTA':
        return 4
      case 'SEXTA':
        return 5
      case 'SABADO':
        return 6
    }
  }

  async function getHorarios() {
    try {
      let listaHorarios = []
      const response = await api.get("/horarios", {
        params: {
          semestre: periodo,
          ano: ano,
        }
      })

      const dados = response.data.content

      console.log(dados)

      dados.map(d => {

        let inicioSemana = dayjs(new Date()).startOf("week")
        const fimSemana = dayjs(new Date()).endOf("week")

        const diaSemana = converteDiaSemana(d.diaSemana)

        while (inicioSemana.isBefore(fimSemana)) {
          if (inicioSemana.day() === diaSemana) {
            const novoHorario = {
              id: d.id,
              title: d.disciplina,
              start: dayjs(inicioSemana).hour(Number(d.horario.split(":")[0])).minute(0).second(0).format("YYYY-MM-DDTHH:mm:ss"),
              end: dayjs(inicioSemana).hour(Number(d.horario.split(":")[0]) + 2).minute(0).second(0).format("YYYY-MM-DDTHH:mm:ss"),
              resource: 'disciplina'
            }
            // setHorarios((prev) => [...prev, novoHorario])
            listaHorarios.push(novoHorario)
          }
          inicioSemana = inicioSemana.add(1, 'day')
        }

        setHorarios(listaHorarios)
      })
    } catch (error) {

    }
  }

  useEffect(() => {
    getHorarios()
  }, [])

  return (
    <Box sx={{ p: 2 }}>
      <Box sx={{ display: 'flex', justifyContent: 'space-between' }}>
        <Typography variant='h4' gutterBottom={true}>Horários Disciplinas</Typography>
      </Box>
      <Box sx={{ width: "100%", flex: 1, minWidth: 0 }}>
        <StandaloneWeekView
          events={horarios}
          onEventsChange={setHorarios}
          readOnly
          defaultPreferences={{ ampm: false, isSidePanelOpen: false, showWeekends: false }}
          defaultVisibleDate={new Date()}
          viewConfig={{ week: { startTime: 7, endTime: 22 } }}
          dateLocale={pt}
          resources={[{ id: "disciplina", title: "disciplina", eventColor: "teal" }]}
        />

      </Box>
    </Box>
  )
}

export default DocenteHorarios