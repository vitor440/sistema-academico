import { StrictMode } from 'react'
import { createRoot } from 'react-dom/client'
import './index.css'
import MainContent from './components/aluno/dashboard/MainContent.jsx'
import PageTarefas from './components/aluno/PageTarefas/PageTarefas.jsx'
import PageExames from './components/aluno/PageExames/PageExames.jsx'
import PageResultados from "./components/aluno/PageResultados/PageResultados.jsx"
import PageHorarios from "./components/aluno/PageHorarios/PageHorarios.jsx"
import Callback from './Callback.jsx'
import { createBrowserRouter, RouterProvider, Route } from 'react-router-dom'
import App from './App.jsx'
import Login from './Login.jsx'
import Error from './Error.jsx'
import ProtectRouter from './router/ProtectRouter.jsx'
import AdminMain from './components/admin/dashboard/AdminMain.jsx'
import PageDepartamentos from './components/admin/PageDepartamentos/PageDepartamentos.jsx'
import PageCursos from './components/admin/PageCursos/PageCursos.jsx'
import PageDocentes from './components/admin/PageDocentes/PageDocentes.jsx'
import PageAlunos from './components/admin/PageAlunos/PageAlunos.jsx'
import PageUsuarios from './components/admin/PageUsuarios/PageUsuarios.jsx'
import PageMatriculas from './components/admin/PageMatricula/PageMatriculas.jsx'
import { GlobalContext, GlobalContextProvider } from './context/GlobalContext.jsx'
import Tarefas from './components/aluno/PageTarefas/Tarefas.jsx'
import Exames from './components/aluno/PageExames/Exames.jsx'
import NotasFrequencia from './components/aluno/Notas_Frequencia/NotasFrequencia.jsx'
import DocenteDashboard from './components/docente/dashboard/DocenteDashboard.jsx'
import LancarFaltas from './components/docente/PageLancarFaltas/LancarFaltas.jsx'
import DisciplinasFaltas from './components/docente/PageLancarFaltas/DisciplinasFaltas.jsx'
import DisciplinaNotas from './components/docente/PageLancarNotas/DisciplinaNotas.jsx'
import DisciplinaResultados from './components/docente/PageLancarNotas/DisciplinaResultados.jsx'
import LancarNotas from './components/docente/PageLancarNotas/LancarNotas.jsx'
import AddExames from './components/docente/PageAddExames/AddExames.jsx'
import Alunos from './components/docente/PageAlunos/Alunos.jsx'
import Turmas from './components/docente/PageTurmas/Turmas.jsx'
import DetalheTurma from './components/docente/PageTurmas/DetalheTurma.jsx'
import PageHorarios2 from './components/aluno/PageHorarios/PageHorarios2.jsx'
import CriarUsuario from './components/admin/PageCriarUsuario/CriarUsuario.jsx'
import Disciplinas from './components/admin/PageDisciplinas/Disciplinas.jsx'
import EditarResultados from './components/docente/PageEditarResultados/EditarResultados.jsx'
import EditarNotasExames from './components/docente/PageEditarResultados/EditarNotasExames.jsx'
import EditarNotas from './components/docente/PageEditarResultados/EditarNotas.jsx'
import SolicitacoesMatricula from './components/docente/PageSolicitacoes/SolicitacoesMatricula.jsx'
import DetalhesPerfil from './components/DetalhesPerfil.jsx'
import DocenteHorarios from './components/docente/PageHorarios/DocenteHorarios.jsx'
import EfetivarMatriculas from './components/docente/PageEfetivarMatriculas/EfetivarMatriculas.jsx'
import ProvaFinal from './components/docente/PageProvaFinal/ProvaFinal.jsx'
import SolicitacaoMatricula from './components/aluno/PageSolicitacao/SolicitacaoMatricula.jsx'


const router = createBrowserRouter([
  {
    path:"/alunos",
    element:<ProtectRouter roles={["ALUNO"]}>
                <App/>
            </ProtectRouter>,
    children:[
      {
        index: true,
        element: <MainContent/>
      },
      {
        path:"tarefas",
        // element: <PageTarefas/>
        element: <Tarefas/>
      },
      {
        path:"exames",
        element: <Exames/>
      },
      {
        path:"resultados",
        element: <PageResultados/>
      },
      {
        path:"horarios",
        element: <PageHorarios2/>
      },
      {
        path:"notasEFrequencia",
        element: <NotasFrequencia/>
      },
      {
        path:"perfil",
        element: <DetalhesPerfil role='ALUNO'/>
      }, 
      {
        path:"matriculas",
        element: <SolicitacaoMatricula/>
      }
    ]
  },
  {
    path:"/login",
    element: <Login/>
  },
  {
    path:"/",
    element: <Login/>
  },
  {
    path:"/callback",
    element: <Callback/>
  },
  {
    path: "/logout",
    element: <Error/>
  },

  {
    path: "/admin",
    element:<ProtectRouter roles={["ADMIN"]}>
                <App/>
            </ProtectRouter>,
    children: [
      {
        index: true,
        element: <AdminMain/>
      },
      {
        path: "departamentos",
        element: <PageDepartamentos/>
      },
      {
        path: "cursos",
        element: <PageCursos/>
      },
      {
        path: "docentes",
        element: <PageDocentes/>
      },
      {
        path: "alunos",
        element: <PageAlunos/>
      },
      {
        path: "usuarios",
        element: <PageUsuarios/>
      },
      {
        path: "matriculas",
        element: <PageMatriculas/>
      },
      {
        path:"disciplinas",
        element: <Disciplinas/>
      },
      {
        path:"criarUsuario",
        element: <CriarUsuario/>
      },
     {
       path:"perfil",
       element: <DetalhesPerfil role='ADMIN'/>
      }
    ]
  },
  {
    path:"/docentes",
    element: <ProtectRouter roles={["DOCENTE"]}>
                <App/>
            </ProtectRouter>,
    children:[
      {
        index: true,
        element: <DocenteDashboard/>
      },
      {
        path:"turmasFrequencia",
        element: <DisciplinasFaltas/>
      },
      {
        path: "turmasFrequencia/:disciplinaId/faltas",
        element: <LancarFaltas/>
      },
      {
        path: "turmasNotas",
        element: <DisciplinaResultados/>
      },
      {
        path: "turmasNotas/:disciplinaId/exames",
        element: <DisciplinaNotas/>
      },
      {
        path: "turmasNotas/:disciplinaId/exames/:exameId",
        element: <LancarNotas/>
      },
      {
        path: "addExame",
        element: <AddExames/>
      },
      {
        path: "alunos",
        element: <Alunos/>
      },
      {
        path: "turmas",
        element: <Turmas/>
      },
      {
        path: "turmas/:disciplinaId",
        element: <DetalheTurma/>
      },
      {
        path: "editarResultados",
        element: <EditarResultados/>
      },
      {
        path: "editarResultados/:disciplinaId/exames",
        element: <EditarNotasExames/>
      },
      {
        path: "editarResultados/:disciplinaId/exames/:exameId",
        element: <EditarNotas/>
      },
      {
        path:"solicitacoesMatriculas",
        element: <SolicitacoesMatricula/>
      },
      {
        path: "perfil",
        element: <DetalhesPerfil role='DOCENTE'/>
      },
      {
        path: "horarios",
        element: <DocenteHorarios/>
      },
      {
        path: "efetivarMatriculas",
        element: <EfetivarMatriculas/>
      },
      {
        path:"provasFinais",
        element: <ProvaFinal/>
      }
    ]
  }

])

createRoot(document.getElementById('root')).render(
  <StrictMode>
    <GlobalContextProvider> 
      <RouterProvider router={router}/>
    </GlobalContextProvider>
  </StrictMode>,
)
