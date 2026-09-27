import CssBaseline from '@mui/material/CssBaseline'
import { createTheme, ThemeProvider } from '@mui/material/styles'
import { ToastContainer } from 'react-toastify'
import './App.css'
import Header2 from './components/Header2'
import Main from './components/Main'

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
