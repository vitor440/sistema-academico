import React from 'react'

const ListaTarefas2 = () => {
  return (
    <div className='lista-avaliacoes2' >
        <h2>Próximas Avaliações</h2>
          <div className="lista">
            <ul>
              <div className="linha" id='header-lista'>
                <li>Tarefa</li>
                <li>Disciplina</li>
                <li>Data de entrega</li>
                <li>Nota</li>
              </div>
              <div className="linha">
                <li id='titulo'>Atividade 03 - Diagrama de Atividade</li>
                <li>Redes de computadores</li>
                <li>domingo, 17 mai. 2026, 23:59</li>
                <li>08:00</li>
              </div>
            </ul>
          </div>        
    </div>
  )
}

export default ListaTarefas2