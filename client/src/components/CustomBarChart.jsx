import React from 'react'
import { ThemeProvider, createTheme } from '@mui/material/styles'
import { BarChart } from '@mui/x-charts'

const CustomBarChart = ({data, width, height, loading}) => {

const infoThemes = createTheme({
        palette: {
            mode: "dark",
        }})

  return (
    <ThemeProvider theme={infoThemes}>
        <BarChart
            loading={loading}
             width={width} // 860
             height={330} //300
             xAxis={[{data: data.map(d => d[0])}]}
             series={[{data: data.map(d => d[1])}]}/>

    </ThemeProvider>
        
    
  )
}

export default CustomBarChart