import React from 'react'
import "./PageTarefas.css"
import { IoSearchOutline } from "react-icons/io5";
import ListaTarefas2 from './ListaTarefas2';

const PageTarefas = () => {
  return (
    <div className='main-container'>
        <h2>Tarefas Pendentes</h2>
        <div className="search-div">
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
        <ListaTarefas2/>
    </div>
  )
}

export default PageTarefas