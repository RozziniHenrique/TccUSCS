import { BrowserRouter as Router, Routes, Route } from "react-router-dom";
import Login from "./pages/Login";
import Dashboard from "./pages/Dashboard";
import Servicos from "./pages/Servicos";
import Funcionarios from "./pages/Funcionarios";
import Agendamentos from "./pages/Agendamento";
import Clientes from "./pages/Clientes";

export default function App() {
  return (
    <Router>
      <Routes>
        <Route path="/" element={<Login />} />
        <Route path="/dashboard" element={<Dashboard />} />
        <Route path="/servicos" element={<Servicos />} />
        <Route path="/funcionarios" element={<Funcionarios />} />
        <Route path="/clientes" element={<Clientes />} />
        <Route path="/agendamentos" element={<Agendamentos />} />
      </Routes>
    </Router>
  );
}
