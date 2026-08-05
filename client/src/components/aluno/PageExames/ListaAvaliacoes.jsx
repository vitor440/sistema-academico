import React from 'react'
import "./ListaAvaliacoes.css"

const ListaAvaliacoes = ({Icone, titulo, content, cor}) => {
  return (
    <div className='lista-avaliacoes' >
        <h2>Próximas Avaliações</h2>
          <div className="lista">
            <ul>
              <div className="linha" id='header-lista'>
                <li>Prova</li>
                <li>Disciplina</li>
                <li>Data</li>
                <li>Horário</li>
              </div>
              <div className="linha">
                <li id='titulo'>Prova 1 - redes</li>
                <li>Redes de computadores</li>
                <li>24/10/2026</li>
                <li>08:00</li>
              </div>
              <div className="linha">
                <li id='titulo'>Prova 2 - redes</li>
                <li>Disciplina</li>
                <li>Data</li>
                <li>Horário</li>
              </div>
            </ul>
          </div>        
    </div>
  )
}

export default ListaAvaliacoes