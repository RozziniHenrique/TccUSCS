import React from "react";
import { BrowserRouter, Routes, Route, Navigate } from "react-router-dom";

import Login from "./pages/Login";
import Cadastro from "./pages/Cadastro";

const Dashboard = () => (
  <div style={{ padding: "50px", color: "#d4af37" }}>
    <h1>Bem-vindo à STEFER! 💈</h1>
    <p>Você está logado.</p>
    <button
      onClick={() => {
        localStorage.clear();
        window.location.href = "/";
      }}
    >
      Sair
    </button>
  </div>
);

export default function App() {
  return (
    <BrowserRouter>
      <Routes>
        <Route path="/" element={<Login />} />

        <Route path="/cadastro" element={<Cadastro />} />

        <Route path="/dashboard" element={<Dashboard />} />

        <Route path="*" element={<Navigate to="/" />} />
      </Routes>
    </BrowserRouter>
  );
}
