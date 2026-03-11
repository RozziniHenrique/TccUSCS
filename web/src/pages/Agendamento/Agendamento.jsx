import React, { useState, useEffect } from "react";
import api from "@/services/api.js";
import Layout from "@/components/layout.jsx";
import AgendamentoForm from "./components/AgendamentoForm";
import { getErrorMessage } from "@/utils/errorHandler";

export default function Agendamento() {
  const [clientes, setClientes] = useState([]);
  const [funcionarios, setFuncionarios] = useState([]);
  const [especialidades, setEspecialidades] = useState([]);

  const estadoInicial = {
    idCliente: "",
    idFuncionario: "",
    idEspecialidade: "",
    data: "",
  };

  const [novoAgendamento, setNovoAgendamento] = useState(estadoInicial);

  const carregarDados = async () => {
    try {
      const [resCli, resFunc, resEsp] = await Promise.all([
        api.get("/clientes"),
        api.get("/funcionarios"),
        api.get("/especialidades"),
      ]);

      setClientes(
        (resCli.data.content || resCli.data || []).filter(
          (i) => i.ativo !== false,
        ),
      );
      setFuncionarios(
        (resFunc.data.content || resFunc.data || []).filter(
          (i) => i.ativo !== false,
        ),
      );
      setEspecialidades(
        (resEsp.data.content || resEsp.data || []).filter(
          (i) => i.ativo !== false,
        ),
      );
    } catch (err) {
      console.error("Erro ao carregar dados:", err);
    }
  };

  useEffect(() => {
    carregarDados();
  }, []);

  const handleAgendar = async (e) => {
    e.preventDefault();
    try {
      const dataFormatada = novoAgendamento.data.replace("T", " ");
      const payload = {
        idCliente: Number(novoAgendamento.idCliente),
        idFuncionario: Number(novoAgendamento.idFuncionario),
        idEspecialidade: Number(novoAgendamento.idEspecialidade),
        data:
          dataFormatada.length === 16 ? `${dataFormatada}:00` : dataFormatada,
      };

      await api.post("/agendamentos", payload);
      alert("✅ Agendamento realizado com sucesso!");
      setNovoAgendamento(estadoInicial);
    } catch (err) {
      const msg = getErrorMessage(err);
      alert(msg || "Erro ao agendar. Verifique a disponibilidade.");
    }
  };

  return (
    <Layout titulo="Agendar Atendimento">
      <AgendamentoForm
        novoAgendamento={novoAgendamento}
        setNovoAgendamento={setNovoAgendamento}
        onSubmit={handleAgendar}
        clientes={clientes}
        funcionarios={funcionarios}
        especialidades={especialidades}
      />
    </Layout>
  );
}
