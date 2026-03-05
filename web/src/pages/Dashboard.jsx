import React, { useState, useEffect } from "react";
import { useNavigate } from "react-router-dom";
import api from "../services/api";

const styles = {
  dashboardContainer: {
    display: "flex",
    height: "100vh",
    backgroundColor: "#121212",
    color: "#fff",
  },
  sidebar: {
    width: "250px",
    backgroundColor: "#1e1e1e",
    padding: "20px",
    display: "flex",
    flexDirection: "column",
    borderRight: "1px solid #333",
  },
  logo: { color: "#d4af37", textAlign: "center", marginBottom: "40px" },
  nav: { flex: 1, display: "flex", flexDirection: "column", gap: "10px" },
  navItem: {
    backgroundColor: "transparent",
    color: "#ccc",
    border: "none",
    padding: "12px",
    textAlign: "left",
    cursor: "pointer",
    borderRadius: "6px",
    fontSize: "1rem",
  },
  logoutButton: {
    backgroundColor: "#c0392b",
    color: "#fff",
    border: "none",
    padding: "10px",
    borderRadius: "6px",
    cursor: "pointer",
    fontWeight: "bold",
  },
  mainContent: { flex: 1, padding: "40px", overflowY: "auto" },
  header: {
    marginBottom: "30px",
    borderBottom: "1px solid #333",
    paddingBottom: "10px",
  },
  statsGrid: {
    display: "grid",
    gridTemplateColumns: "repeat(auto-fit, minmax(200px, 1fr))",
    gap: "20px",
    marginBottom: "40px",
  },
  statCard: {
    backgroundColor: "#1e1e1e",
    padding: "20px",
    borderRadius: "10px",
    textAlign: "center",
    border: "1px solid #333",
  },
  statNumber: {
    fontSize: "2rem",
    color: "#d4af37",
    fontWeight: "bold",
    marginTop: "10px",
  },
  tableSection: {
    backgroundColor: "#1e1e1e",
    padding: "20px",
    borderRadius: "10px",
  },
  table: { width: "100%", borderCollapse: "collapse", marginTop: "20px" },
  th: {
    textAlign: "left",
    padding: "12px",
    borderBottom: "2px solid #333",
    color: "#d4af37",
  },
  td: { padding: "12px", borderBottom: "1px solid #333", color: "#ccc" },
  actionBtn: {
    border: "none",
    borderRadius: "4px",
    cursor: "pointer",
    padding: "5px 8px",
    fontSize: "1rem",
    marginLeft: "5px",
  },
};

export default function Dashboard() {
  const navigate = useNavigate();
  const user = JSON.parse(localStorage.getItem("user"));

  const [agendamentos, setAgendamentos] = useState([]);
  const [loading, setLoading] = useState(true);
  const [dadosDashboard, setDadosDashboard] = useState({
    faturamentoTotal: 0,
    totalAgendamentos: 0,
    rankingFuncionarios: [],
  });

  const [dataFiltro, setDataFiltro] = useState(
    new Date().toISOString().split("T")[0],
  );

  async function carregarDadosDashboard() {
    try {
      const response = await api.get(
        `/dashboard?inicio=${dataFiltro}&fim=${dataFiltro}`,
      );
      console.log("Dados que vieram do Java:", response.data);
      setDadosDashboard(response.data);
    } catch (err) {
      console.error("Erro ao buscar dados do dashboard", err);
    }
  }

  async function carregarAgendamentos() {
    setLoading(true);
    try {
      const response = await api.get(`/agendamentos?data=${dataFiltro}`);
      setAgendamentos(response.data.content || []);
    } catch (err) {
      console.error("Erro ao buscar", err);
    } finally {
      setLoading(false);
    }
  }

  useEffect(() => {
    carregarAgendamentos();
    carregarDadosDashboard();
  }, [dataFiltro]);

  const handleLogout = () => {
    localStorage.removeItem("user");
    navigate("/");
  };

  const handleFinalizar = async (id) => {
    const nota = prompt("Qual a nota de 1 a 5 para este atendimento?");
    if (!nota) return;
    try {
      await api.put("/agendamentos/finalizar", { id, nota: parseInt(nota) });
      alert("Atendimento finalizado!");
      carregarAgendamentos();
      carregarDadosDashboard();
    } catch (err) {
      alert("Erro ao finalizar");
    }
  };

  const handleCancelar = async (id) => {
    const motivo = prompt("Qual o motivo?");
    if (!motivo) return;

    try {
      await api.delete("/agendamentos", {
        data: { idAgendamento: id, motivo },
      });
      alert("Cancelado!");
      setAgendamentos([]);
      carregarAgendamentos();
    } catch (err) {
      console.error("Erro completo:", err.response?.data);
      alert("Erro ao cancelar: Verifique o console.");
    }
  };

  return (
    <div style={styles.dashboardContainer}>
      <aside style={styles.sidebar}>
        <h2 style={styles.logo}>STEFER 💈</h2>
        <nav style={styles.nav}>
          <button style={styles.navItem}>📅 Agendamentos</button>
          <button style={styles.navItem}>✂️ Serviços</button>
          <button style={styles.navItem}>👥 Funcionários</button>
          <button style={styles.navItem}>📊 Relatórios</button>
        </nav>
        <button onClick={handleLogout} style={styles.logoutButton}>
          Sair
        </button>
      </aside>

      <main style={styles.mainContent}>
        <header style={styles.header}>
          <h1>Bem-vindo, {user?.nome || "Gestor"}!</h1>
          <p>
            {new Date().toLocaleDateString("pt-BR", {
              weekday: "long",
              day: "numeric",
              month: "long",
            })}
          </p>
        </header>

        <section style={styles.statsGrid}>
          <div style={styles.statCard}>
            <h3>Cortes Hoje</h3>
            <p style={styles.statNumber}>{agendamentos.length}</p>
          </div>
          <div style={styles.statCard}>
            <h3>Receita Estimada do Dia</h3>
            <p style={styles.statNumber}>
              {new Intl.NumberFormat("pt-BR", {
                style: "currency",
                currency: "BRL",
              }).format(dadosDashboard.faturamentoHoje || 0)}
            </p>
          </div>
          <div style={styles.statCard}>
            <h3>Total Geral de Agendamentos</h3>
            <p style={styles.statNumber}>{dadosDashboard.totalGeral || 0}</p>
          </div>
        </section>

        <div
          style={{
            marginBottom: "20px",
            display: "flex",
            alignItems: "center",
            gap: "10px",
          }}
        >
          <label style={{ color: "#d4af37", fontWeight: "bold" }}>
            Filtrar por Dia:
          </label>
          <input
            type="date"
            value={dataFiltro}
            onChange={(e) => setDataFiltro(e.target.value)}
            style={{
              backgroundColor: "#1e1e1e",
              color: "#fff",
              border: "1px solid #333",
              padding: "8px",
              borderRadius: "5px",
            }}
          />
        </div>

        <section style={styles.tableSection}>
          <h2>Próximos Agendamentos</h2>
          {loading ? (
            <p style={{ textAlign: "center", marginTop: "20px" }}>
              Carregando...
            </p>
          ) : (
            <table style={styles.table}>
              <thead>
                <tr>
                  <th style={styles.th}>Cliente</th>
                  <th style={styles.th}>Serviço</th>
                  <th style={styles.th}>Data/Hora</th>
                  <th style={styles.th}>Barbeiro</th>
                  <th style={styles.th}>STATUS</th>
                  <th style={styles.th}>AVALIAÇÃO</th>
                  <th style={styles.th}>AÇÕES</th>
                </tr>
              </thead>
              <tbody>
                {agendamentos.map((agend) => (
                  <tr
                    key={agend.id}
                    style={{ opacity: agend.motivoCancelamento ? 0.5 : 1 }}
                  >
                    <td style={styles.td}>{agend.nomeCliente}</td>
                    <td style={styles.td}>{agend.especialidade}</td>
                    <td style={styles.td}>
                      {new Date(agend.data).toLocaleString("pt-BR")}
                    </td>
                    <td style={styles.td}>{agend.nomeFuncionario}</td>
                    <td style={styles.td}>
                      <span
                        style={{
                          padding: "4px 10px",
                          borderRadius: "12px",
                          fontSize: "0.75rem",
                          fontWeight: "bold",
                          backgroundColor: agend.motivoCancelamento
                            ? "#7f8c8d"
                            : agend.concluido
                              ? "#2e7d32"
                              : "#d4af37",
                          color: "#fff",
                        }}
                      >
                        {agend.motivoCancelamento
                          ? "CANCELADO"
                          : agend.concluido
                            ? "CONCLUÍDO"
                            : "PENDENTE"}
                      </span>
                    </td>
                    <td style={styles.td}>
                      {agend.nota > 0 ? (
                        <span
                          style={{ color: "#d4af37", letterSpacing: "2px" }}
                        >
                          {"⭐".repeat(agend.nota)}
                        </span>
                      ) : (
                        <span style={{ color: "#666", fontSize: "0.8rem" }}>
                          ---
                        </span>
                      )}
                    </td>
                    <td style={styles.td}>
                      {!agend.concluido && !agend.motivoCancelamento && (
                        <>
                          <button
                            title="Finalizar"
                            onClick={() => handleFinalizar(agend.id)}
                            style={{
                              ...styles.actionBtn,
                              backgroundColor: "#2e7d32",
                            }}
                          >
                            ✅
                          </button>
                          <button
                            title="Cancelar"
                            onClick={() => handleCancelar(agend.id)}
                            style={{
                              ...styles.actionBtn,
                              backgroundColor: "#c0392b",
                            }}
                          >
                            ❌
                          </button>
                        </>
                      )}
                    </td>
                  </tr>
                ))}
              </tbody>
            </table>
          )}
        </section>
      </main>
    </div>
  );
}
