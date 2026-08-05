import { useState, useEffect } from 'react'
import HeaderComponent from './components/HeaderComponent'
import Header2 from './components/Header2'
import Main from './components/Main'
import Error from './Error'
import './App.css'
import { createTheme, ThemeProvider } from '@mui/material/styles'
import CssBaseline from '@mui/material/CssBaseline'
import { ToastContainer } from 'react-toastify'

function App() {

  const roles = localStorage.getItem("roles")

  const theme = createTheme({
    palette: {
      mode: "dark",
      primary: {
        main: "#3fb566"
      },
      secondary: {
        main: "#d3cfcf"
      },
      background: {
        paper: '#191919',
        default: '#161515'
      },
      DataGrid: {
        headerBg: "#000",
        color: "#fff",
        bg: "#181818"
      }
    }
  })

  return (
    <>
    <ToastContainer position='top-right' autoClose={6000} theme='dark'/>
      <ThemeProvider theme={theme}>
        <CssBaseline/>
        <Header2/>
        <Main/>
      </ThemeProvider>
    </>
  )
}

export default App
