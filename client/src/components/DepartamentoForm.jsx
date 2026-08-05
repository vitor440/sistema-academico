import React from 'react'
import "./DepartamentoForm.css"

const DepartamentoForm = () => {
  return (
    <div className='form-container'>
        <form id='departamento-form' >
            <h2>Cadastro de departamento</h2>
            <div id="form-inputs">
                <div id="form-input" >
                    <label htmlFor="nome">Nome:</label>
                    <input type="text" name="nome" id="nome" style={{width:"492px", marginTop:"2rem"}}/>
                </div>
                <div id="form-input-inline">
                    <div id="form-input">
                        <label htmlFor="nome">Bloco:</label>
                        <input type="text" name="bloco" id="bloco" />
                    </div>
                    <div id="form-input">
                        <label htmlFor="nome">Sigla:</label>
                        <input type="text" name="sigla" id="sigla" />
                    </div>
                </div>
            </div>
            <div id="btn-div">
            <input type="submit" value="Salvar" id='submit-btn'/>
            <button id='cancel-btn'>Cancelar</button>

            </div>
        </form>
    </div>
  )
}

export default DepartamentoForm