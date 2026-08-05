import Box from '@mui/material/Box'
import Button from '@mui/material/Button'
import Grid from '@mui/material/Grid'
import MenuItem from '@mui/material/MenuItem'
import Paper from '@mui/material/Paper'
import TextField from '@mui/material/TextField'
import Typography from '@mui/material/Typography'
import React, { useContext, useEffect, useState } from 'react'
import CustomTable from '../../CustomTable'
import { GlobalContext } from '../../../context/GlobalContext'
import api from '../../../services/api'

const NotasFrequencia = () => {
    const { ano, setAno, periodo, setPeriodo } = useContext(GlobalContext)
    const [matriculas, setMatriculas] = useState([])
    const [totalMatriculas, setTotalMatriculas] = useState(0)


    const columns = [
        { field: 'disciplina', headerName: 'Disciplina', flex: 0 },
        { field: 'P1', headerName: 'P1', flex: 1 },
        { field: 'E1', headerName: 'E1', flex: 1 },
        { field: 'P2', headerName: 'P2', flex: 1 },
        { field: 'E2', headerName: 'E2', flex: 1 },
        { field: 'P3', headerName: 'P3', flex: 1 },
        { field: 'E3', headerName: 'E3', flex: 1 },
        { field: 'P4', headerName: 'P4', flex: 1 },
        { field: 'E4', headerName: 'E4', flex: 1 },
        { field: 'P5', headerName: 'P5', flex: 1 },
        { field: 'E5', headerName: 'E5', flex: 1 },
        { field: 'P6', headerName: 'P6', flex: 1 },
        { field: 'E6', headerName: 'E6', flex: 1 },
        { field: 'P7', headerName: 'P7', flex: 1 },
        { field: 'E7', headerName: 'E7', flex: 1 },
        { field: 'P8', headerName: 'P8', flex: 1 },
        { field: 'E8', headerName: 'E8', flex: 1 },
        { field: 'ME', headerName: 'ME', flex: 1 },
        { field: 'PF', headerName: 'PF', flex: 1 },
        { field: 'MF', headerName: 'MF', flex: 1 },
        { field: 'FT', headerName: 'FT', flex: 1 },
        { field: 'EF', headerName: 'EF', flex: 1 },
    ];

    const [loading, setloading] = useState(false)
    const [paginationModel, setPaginationModel] = useState({
        page: 0,
        pageSize: 20
    })


    async function getDados() {

        const rows = []

        try {
            const response = await api.get("/matriculas", {
                params: {
                    pagina: paginationModel.page,
                    tamanho: paginationModel.pageSize,
                    semestre: periodo,
                    ano: ano,
                    statusSolicitacao: 'EFETIVADA'
                }
            })
            setTotalMatriculas(response.data.totalElements)
            const dados = response.data.content

            dados.map(d => {
                const row = {
                    id: d.id,
                    disciplina: d.disciplina,
                    P1: d.resultados[0] ? d.resultados[0].peso : "",
                    E1: d.resultados[0] ? d.resultados[0].nota : "",
                    P2: d.resultados[1] ? d.resultados[1].peso : "",
                    E2: d.resultados[1] ? d.resultados[1].nota : "",
                    P3: d.resultados[2] ? d.resultados[2].peso : "",
                    E3: d.resultados[2] ? d.resultados[2].nota : "",
                    P4: d.resultados[3] ? d.resultados[3].peso : "",
                    E4: d.resultados[3] ? d.resultados[3].nota : "",
                    P5: d.resultados[4] ? d.resultados[4].peso : "",
                    E5: d.resultados[4] ? d.resultados[4].nota : "",
                    P6: d.resultados[5] ? d.resultados[5].peso : "",
                    E6: d.resultados[5] ? d.resultados[5].nota : "",
                    P7: d.resultados[6] ? d.resultados[6].peso : "",
                    E7: d.resultados[6] ? d.resultados[6].nota : "",
                    P8: d.resultados[7] ? d.resultados[7].peso : "",
                    E8: d.resultados[7] ? d.resultados[7].nota : "",
                    ME: d.media,
                    PF: d.notaFinal,
                    MF: d.mediaFinal,
                    FT: d.faltas,
                    EF: d.efetivado
                }

                rows.push(row)
            })
            setMatriculas(rows)

        } catch (error) {
        }
    }

    function handleClick() {
        getDados()
    }


    useEffect(() => {
        getDados()
    }, [paginationModel])

    return (
        <Box>

            <Typography variant='h4' gutterBottom={true}>Notas e Frequências</Typography>

            <Paper sx={{ display: "flex", justifyContent: "space-between", alignContent: "center", p: 2, mb: 3 }} variant='outlined'>
                <TextField size='small' label="Ano" sx={{ width: "20%" }} defaultValue={ano} onChange={(e) => setAno(e.target.value)} />
                <TextField size='small' label="Semestre" select sx={{ width: "20%" }} defaultValue={periodo} onChange={(e) => setPeriodo(e.target.value)}>
                    <MenuItem key={1} value={1}>1° semestre</MenuItem>
                    <MenuItem key={2} value={2}>2° semestre</MenuItem>
                </TextField>

                <Button variant='contained' size='small' sx={{ height: "44px" }} onClick={handleClick}>Buscar</Button>
            </Paper>

            <Paper variant='outlined' sx={{ p: 2, mb: 3 }}>
                <Grid container direction="column">
                    <Grid container direction="row">
                        <Grid size={6}>
                            <Typography variant='body1'>Mátricula: 232321323</Typography>
                        </Grid>
                        <Grid size={6}>
                            <Typography variant='body1'>Período: 2026 / 1° semestre</Typography>
                        </Grid>
                    </Grid>
                    <Grid container direction="row">
                        <Grid size={6}>
                            <Typography variant='body1'>Curso: Ciências da computação</Typography>
                        </Grid>
                        <Grid size={6}>
                            <Typography variant='body1'>Período Atual: 5</Typography>
                        </Grid>
                    </Grid>
                </Grid>
            </Paper>

            <Paper variant='outlined' sx={{ mb: 3 }}>
                <CustomTable columns={columns} rows={matriculas} paginationModel={paginationModel} setPaginationModel={setPaginationModel} loading={loading} total={totalMatriculas}/>
            </Paper>

            <Paper variant='outlined' sx={{ p: 2, mb: 3 }}>
                <Grid container direction="column">
                    <Grid container direction="row">
                        <Grid size={4}>
                            <Typography variant='body1'>PX: Peso do exercício x</Typography>
                        </Grid>
                        <Grid size={4}>
                            <Typography variant='body1'>EX: Nota do exercício x</Typography>
                        </Grid>
                        <Grid size={4}>
                            <Typography variant='body1'>ME: Média dos exercícios</Typography>
                        </Grid>
                    </Grid>
                    <Grid container direction="row">
                        <Grid size={4}>
                            <Typography variant='body1'>PF: Nota da prova final</Typography>
                        </Grid>
                        <Grid size={4}>
                            <Typography variant='body1'>MF: Média final</Typography>
                        </Grid>
                        <Grid size={4}>
                            <Typography variant='body1'>FT: Quantidade de faltas</Typography>
                        </Grid>
                    </Grid>
                    <Grid container direction="row">
                        <Grid size={4}>
                            <Typography variant='body1'>ST: Situação</Typography>
                        </Grid>
                        <Grid size={4}>
                            <Typography variant='body1'>FT: Efetivação no histórico</Typography>
                        </Grid>
                    </Grid>
                </Grid>
            </Paper>

        </Box>
    )
}

export default NotasFrequencia