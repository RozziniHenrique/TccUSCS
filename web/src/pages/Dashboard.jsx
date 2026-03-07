import React, { useState, useEffect } from "react";
import api from "../services/api";
import Layout from "../components/layout";

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
      setEspecialidades(
        (resEsp.data.content || resEsp.data || []).filter((e) => e.ativo),
      );
    } catch (err) {
      console.error(err);
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    carregarTudo();
  }, [filtros.data, filtros.especialidade]);

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
      <div style={styles.statsGrid}>
        <div style={styles.statCard}>
          <span style={styles.statTitle}>Hoje</span>
          <p style={styles.statNumber}>{agendamentos.length}</p>
        </div>
        <div style={styles.statCard}>
          <span style={styles.statTitle}>Receita</span>
          <p style={{ ...styles.statNumber, color: "#2E7D32" }}>
            {new Intl.NumberFormat("pt-BR", {
              style: "currency",
              currency: "BRL",
            }).format(resumo.faturamentoHoje || 0)}
          </p>
        </div>
      </div>

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
          <option value="">Serviços</option>
          {especialidades.map((e) => (
            <option key={e.id} value={e.id}>
              {e.nome}
            </option>
          ))}
        </select>
      </div>

      <div style={styles.tableCard}>
        <table style={styles.table}>
          <thead>
            <tr>
              <th style={styles.th}>HORA</th>
              <th style={styles.th}>CLIENTE</th>
              <th style={styles.th}>SERVIÇO</th>
              <th style={{ ...styles.th, textAlign: "right" }}>
                AÇÕES / STATUS
              </th>
            </tr>
          </thead>
          <tbody>
            {agendamentos.map((ag) => (
              <tr key={ag.id}>
                <td style={styles.td}>
                  {ag.data
                    ? new Date(ag.data).toLocaleTimeString("pt-BR", {
                        hour: "2-digit",
                        minute: "2-digit",
                      })
                    : "--:--"}
                </td>
                <td style={styles.td}>{ag.cliente?.nome || ag.nomeCliente}</td>
                <td style={styles.td}>
                  {ag.especialidade?.nome || ag.especialidade}
                </td>
                <td style={{ ...styles.td, textAlign: "right" }}>
                  {ag.concluido ? (
                    <span style={styles.badgeSuccess}>CONCLUÍDO</span>
                  ) : ag.motivoCancelamento ? (
                    <span style={styles.badgeCancel}>CANCELADO</span>
                  ) : (
                    <div style={styles.actionsContainer}>
                      <button
                        onClick={() => handleFinalizar(ag.id)}
                        style={styles.btnFinalizar}
                      >
                        OK
                      </button>
                      <button
                        onClick={() => handleCancelar(ag.id)}
                        style={styles.btnCancelar}
                      >
                        X
                      </button>
                    </div>
                  )}
                </td>
              </tr>
            ))}
          </tbody>
        </table>
      </div>
    </Layout>
  );
}

const styles = {
  statsGrid: {
    display: "grid",
    gridTemplateColumns: "repeat(auto-fit, minmax(200px, 1fr))",
    gap: "20px",
    marginBottom: "30px",
  },
  statCard: {
    backgroundColor: "#FFF",
    padding: "20px",
    borderRadius: "15px",
    border: "1px solid #E0E5F2",
  },
  statTitle: { color: "#A3AED0", fontSize: "0.85rem", fontWeight: "700" },
  statNumber: { fontSize: "1.8rem", color: "#2B3674", fontWeight: "800" },
  filterBar: { display: "flex", gap: "15px", marginBottom: "25px" },
  input: { padding: "10px", borderRadius: "10px", border: "1px solid #E0E5F2" },
  tableCard: {
    backgroundColor: "#FFF",
    padding: "20px",
    borderRadius: "15px",
    overflowX: "auto",
  },
  table: { width: "100%", borderCollapse: "collapse" },
  th: {
    textAlign: "left",
    padding: "12px",
    color: "#A3AED0",
    fontSize: "0.75rem",
    fontWeight: "bold",
  },
  td: { padding: "12px", borderBottom: "1px solid #F4F7FE", color: "#2B3674" },
  actionsContainer: { display: "flex", gap: "5px", justifyContent: "flex-end" },
  btnFinalizar: {
    backgroundColor: "#4A148C",
    color: "#FFF",
    border: "none",
    padding: "6px 12px",
    borderRadius: "6px",
    cursor: "pointer",
    fontWeight: "bold",
  },
  btnCancelar: {
    backgroundColor: "#FFF",
    color: "#D32F2F",
    border: "1px solid #D32F2F",
    padding: "6px 12px",
    borderRadius: "6px",
    cursor: "pointer",
    fontWeight: "bold",
  },
  badgeSuccess: {
    backgroundColor: "#E8F5E9",
    color: "#2E7D32",
    padding: "5px 10px",
    borderRadius: "6px",
    fontSize: "0.7rem",
    fontWeight: "bold",
  },
  badgeCancel: {
    backgroundColor: "#FFEBEE",
    color: "#C62828",
    padding: "5px 10px",
    borderRadius: "6px",
    fontSize: "0.7rem",
    fontWeight: "bold",
  },
};
