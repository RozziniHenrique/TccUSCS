import React, { useState, useEffect } from "react";
import api from "../services/api";
import Layout from "../components/layout";
import { formatarMoeda, formatarHora } from "../utils/formatters";
import { styles } from "./Dashboard.styles";

export default function Dashboard() {
  const [agendamentos, setAgendamentos] = useState([]);
  const [especialidades, setEspecialidades] = useState([]);
  const [loading, setLoading] = useState(true);
  const [filtros, setFiltros] = useState({
    data: new Date().toISOString().split("T")[0],
    especialidade: "",
  });
  const [resumo, setResumo] = useState({ faturamentoHoje: 0, totalGeral: 0 });

  const carregarTudo = async () => {
    setLoading(true);
    try {
      const queryAgend = `/agendamentos?data=${filtros.data}${filtros.especialidade ? `&idEspecialidade=${filtros.especialidade}` : ""}`;
      const queryDash = `/dashboard?inicio=${filtros.data}&fim=${filtros.data}`;

      const [resAgend, resDash, resEsp] = await Promise.all([
        api.get(queryAgend),
        api.get(queryDash),
        api.get("/especialidades"),
      ]);

      setAgendamentos(resAgend.data.content || resAgend.data || []);
      setResumo(resDash.data);
      setEspecialidades(resEsp.data.content || resEsp.data || []);
    } catch (err) {
      console.error("Erro na carga de dados:", err);
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    carregarTudo();
  }, [filtros.data, filtros.especialidade]);

  // Handlers (Lógica de ação)
  const handleFinalizar = async (id) => {
    if (!window.confirm("Concluir atendimento?")) return;
    try {
      await api.put("/agendamentos/finalizar", { id: Number(id), nota: 5 });
      carregarTudo();
    } catch (err) {
      alert("Erro ao finalizar.");
    }
  };

  const handleCancelar = async (id) => {
    const motivo = prompt("Motivo:");
    if (!motivo) return;
    try {
      await api.delete("/agendamentos", {
        data: { idAgendamento: Number(id), motivo },
      });
      carregarTudo();
    } catch (err) {
      alert("Erro ao cancelar.");
    }
  };

  return (
    <Layout titulo="Painel de Controle">
      {/* Cards de Resumo */}
      <div style={styles.statsGrid}>
        <StatCard title="Hoje" value={agendamentos.length} />
        <StatCard
          title="Receita"
          value={formatarMoeda(resumo.faturamentoHoje)}
          isMoney
        />
      </div>

      {/* Barra de Filtros */}
      <div style={styles.filterBar}>
        <input
          type="date"
          style={styles.input}
          value={filtros.data}
          onChange={(e) => setFiltros({ ...filtros, data: e.target.value })}
        />

        <select
          style={styles.input}
          value={filtros.especialidade}
          onChange={(e) =>
            setFiltros({ ...filtros, especialidade: e.target.value })
          }
        >
          <option value="">Todos os Serviços</option>
          {especialidades.map((e) => (
            <option key={e.id} value={e.id}>
              {e.nome}
            </option>
          ))}
        </select>
      </div>

      {/* Tabela de Agendamentos */}
      <div style={styles.tableCard}>
        <table style={styles.table}>
          <thead>
            <tr>
              <th style={styles.th}>HORA</th>
              <th style={styles.th}>CLIENTE</th>
              <th style={styles.th}>SERVIÇO</th>
              <th style={{ ...styles.th, textAlign: "right" }}>AÇÕES</th>
            </tr>
          </thead>
          <tbody>
            {agendamentos.length === 0 ? (
              <tr>
                <td colSpan="4" style={styles.emptyRow}>
                  Nenhum agendamento encontrado.
                </td>
              </tr>
            ) : (
              agendamentos.map((ag) => (
                <tr key={ag.id}>
                  <td style={styles.td}>{formatarHora(ag.data)}</td>
                  <td style={styles.td}>
                    {ag.cliente?.nome || ag.nomeCliente}
                  </td>
                  <td style={styles.td}>
                    {ag.especialidade?.nome || ag.especialidade}
                  </td>
                  <td style={{ ...styles.td, textAlign: "right" }}>
                    <StatusOuAcoes
                      ag={ag}
                      onFinalizar={handleFinalizar}
                      onCancelar={handleCancelar}
                    />
                  </td>
                </tr>
              ))
            )}
          </tbody>
        </table>
      </div>
    </Layout>
  );
}

const StatCard = ({ title, value, isMoney }) => (
  <div style={styles.statCard}>
    <span style={styles.statTitle}>{title}</span>
    <p style={{ ...styles.statNumber, color: isMoney ? "#2E7D32" : "#2B3674" }}>
      {value}
    </p>
  </div>
);

const StatusOuAcoes = ({ ag, onFinalizar, onCancelar }) => {
  if (ag.concluido) return <span style={styles.badgeSuccess}>CONCLUÍDO</span>;
  if (ag.motivoCancelamento)
    return <span style={styles.badgeCancel}>CANCELADO</span>;
  return (
    <div style={styles.actionsContainer}>
      <button onClick={() => onFinalizar(ag.id)} style={styles.btnFinalizar}>
        OK
      </button>
      <button onClick={() => onCancelar(ag.id)} style={styles.btnCancelar}>
        X
      </button>
    </div>
  );
};
