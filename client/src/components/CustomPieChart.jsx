import React from 'react'
import { ThemeProvider, createTheme } from '@mui/material/styles'
import { PieChart } from '@mui/x-charts'

const CustomPieChart = ({data, width, height, loading}) => {



  return (
   
        <PieChart width={width} loading={loading} height={250} series={[ // height/width = 200
            {
            data: data,
            highlightScope: { fade: 'global', highlight: 'item' },
            faded: { innerRadius: 30, additionalRadius: -30, color: 'gray' },
            }
        ]}/>

  )
}

export default CustomPieChart