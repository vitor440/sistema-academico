import React from 'react'
import './PageExames.css'
import ListaAvaliacoes from './ListaAvaliacoes'
import ListaAvaliacoes2 from './ListaAvaliacoes2'

const PageExames = () => {
  return (
    <div className='resultados-container'>
        <h2>Provas Agendadas</h2>
        <div className="search-div">
            <div className="opcoes" id='pesquisa-exame'>
                <label htmlFor="exame-titulo">Pesquisar:</label>
                <input type="text" name="exame-titulo" id="exame-titulo" />
            </div>
            
            <div className="opcoes">
                <label htmlFor="select-disciplina">Disciplina:</label>
                <select name="select-disciplina" id="select-disciplina">
                    <option value="disciplina">Matemática</option>
                    <option value="disciplina">Física</option>
                    <option value="disciplina">Linguagens de programação</option>
                    <option value="disciplina">Engenharia de software I</option>
                    <option value="disciplina">Grafos</option>
                </select>
            </div>
            <div className="opcoes">
                <label htmlFor="select-data">Data:</label>
                <select name="select-data" id="select-data">
                    <option value="data">Mais Recentes</option>
                    <option value="data">Mais Antigos</option>
                </select>
            </div>

            <div className="opcoes">
                <label htmlFor="select-data">Horário:</label>
                <select name="select-data" id="select-data">
                    <option value="horario">08:00</option>
                    <option value="horario">10:00</option>
                    <option value="horario">14:00</option>
                    <option value="horario">16:00</option>
                    <option value="horario">18:00</option>
                </select>
            </div>
            <input type="submit" value="filtrar" id='filtrar-btn'/>
        </div>
        <div id='tabela-avaliacoes'>
            <ListaAvaliacoes2 />
        </div>
    </div>
  )
}

export default PageExames