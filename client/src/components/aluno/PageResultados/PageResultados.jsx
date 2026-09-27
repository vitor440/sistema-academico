import Box from '@mui/material/Box'
import Grid from '@mui/material/Grid'
import Typography from '@mui/material/Typography'
import { useContext, useEffect, useState } from 'react'
import { GlobalContext } from '../../../context/GlobalContext'
import api from '../../../services/api'
import ResultadoComponent from './ResultadoComponent'

const PageResultados = () => {

  const { ano, setAno, periodo, setPeriodo } = useContext(GlobalContext)
  const [resultados, setResultados] = useState([])
  async function getResultados() {
    try {
      const response = await api.get("/resultados", {
        params: {
          pagina: 0,
          tamanho: 30,
          semestre: periodo,
          ano: ano
        }
      })

      const dados = response.data.content
      setResultados(dados)
    } catch (error) {

    }
  }

  function handleClick() {
    getResultados()
  }

  useEffect(() => {
    getResultados()
  }, [])

  if (resultados.length === 0) {
    return <Box>
      <Typography>Nenhum resultado de exame</Typography>
    </Box>
  }

  return (
    <Box sx={{ p: 2 }}>
      <Box sx={{ display: 'flex', justifyContent: 'space-between' }}>
        <Typography variant='h4' gutterBottom={true}>Resultados de exames</Typography>
      </Box>
      {/* <ResultadoComponent dados={resultados}/> */}



      <Grid container direction="column" spacing={2} >

        <Grid container direction="row" spacing={2}>
          {
            resultados?.map(resultado => {
              return <ResultadoComponent resultado={resultado} />
            })
          }

        </Grid>
      </Grid>
    </Box>
  )
}

export default PageResultados