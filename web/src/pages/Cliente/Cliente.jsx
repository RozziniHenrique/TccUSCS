import React, { useState, useEffect } from "react";
import api from "@/services/api";
import Layout from "@/components/layout";
import ClienteTable from "./components/ClienteTable";
import { styles } from "./Cliente.styles";
import { filtrarFuncionariosPorServico } from "@/utils/filtros";

export default function Clientes() {
  const [clientes, setClientes] = useState([]);
  const [funcionarios, setFuncionarios] = useState([]);
  const [servicos, setServicos] = useState([]);
  const [busca, setBusca] = useState("");
  const [loading, setLoading] = useState(true);

  const [isModalOpen, setIsModalOpen] = useState(false);
  const [novoAtendimento, setNovoAtendimento] = useState({
    funcionarioId: "",
    servicoId: "",
  });

  useEffect(() => {
    async function carregarDados() {
      try {
        setLoading(true);
        const [resClientes, resAgendamentos, resFunc, resServ] =
          await Promise.all([
            api.get("/clientes"),
            api.get("/agendamentos?size=1000"),
            api.get("/funcionarios"),
            api.get("/especialidades"),
          ]);

        const listaClientes =
          resClientes.data.content || resClientes.data || [];
        const listaAgendamentos =
          resAgendamentos.data.content || resAgendamentos.data || [];

        setFuncionarios(resFunc.data.content || resFunc.data || []);
        setServicos(resServ.data.content || resServ.data || []);

        const clientesComContagem = listaClientes.map((cliente) => {
          const total = listaAgendamentos.filter(
            (ag) =>
              ag.cliente === cliente.nome || ag.nomeCliente === cliente.nome,
          ).length;
          return { ...cliente, totalAgendamentos: total };
        });

        const listaOrdenada = clientesComContagem.sort((a, b) => {
          const nomeA = a.nome.toUpperCase();
          const nomeB = b.nome.toUpperCase();
          if (nomeA.includes("BALCÃO")) return -1;
          if (nomeB.includes("BALCÃO")) return 1;
          return nomeA.localeCompare(nomeB);
        });

        setClientes(listaOrdenada);
      } catch (err) {
        console.error("Erro ao carregar dados:", err);
      } finally {
        setLoading(false);
      }
    }
    carregarDados();
  }, []);

  const finalizarAtendimentoBalcao = async () => {
    const { funcionarioId, servicoId } = novoAtendimento;

    if (!funcionarioId || !servicoId) {
      alert("Selecione o profissional e o serviço!");
      return;
    }
    const clienteBalcao = clientes.find((c) =>
      c.nome.toUpperCase().includes("BALCÃO"),
    );

    const payload = {
      idFuncionario: Number(funcionarioId),
      idCliente: Number(clienteBalcao.id),
      idEspecialidade: Number(servicoId),
      data: new Date().toISOString().replace("T", " ").substring(0, 19),
    };

    try {
      await api.post("/agendamentos", payload);
      alert("Atendimento registrado com sucesso! ⚡");
      setIsModalOpen(false);
      window.location.reload();
    } catch (err) {
      const msgErro =
        err.response?.data?.mensagem || "Erro ao registrar venda.";
      alert(msgErro);
    }
  };

  const clientesFiltrados = clientes.filter(
    (c) =>
      c.nome.toLowerCase().includes(busca.toLowerCase()) ||
      (c.cpf && c.cpf.includes(busca)),
  );

  const funcionariosFiltradosBalcao = filtrarFuncionariosPorServico(
    funcionarios,
    novoAtendimento.servicoId,
  );

  return (
    <Layout titulo="👥 Gestão de Clientes">
      <div
        style={{
          display: "grid",
          gridTemplateColumns: "repeat(auto-fit, minmax(200px, 1fr))",
          gap: "20px",
          marginBottom: "30px",
        }}
      >
        <div style={styles.cardInformativo}>
          <p style={styles.label}>Total de Clientes</p>
          <h3 style={styles.valor}>{clientes.length}</h3>
        </div>
        <div style={styles.cardInformativo}>
          <p style={styles.label}>Clientes Ativos</p>
          <h3 style={{ ...styles.valor, color: "#05CD99" }}>
            {clientes.filter((c) => c.ativo !== false).length}
          </h3>
        </div>
      </div>

      <div
        style={{
          display: "flex",
          justifyContent: "space-between",
          marginBottom: "20px",
          alignItems: "center",
          backgroundColor: "#fff",
          padding: "15px",
          borderRadius: "16px",
        }}
      >
        <input
          type="text"
          placeholder="🔍 Buscar por nome ou CPF..."
          value={busca}
          onChange={(e) => setBusca(e.target.value)}
          style={{
            padding: "12px 20px",
            borderRadius: "12px",
            border: "1px solid #E0E5F2",
            width: "350px",
            outline: "none",
            fontSize: "0.9rem",
          }}
        />

        <button
          onClick={() => setIsModalOpen(true)}
          style={{
            backgroundColor: "#05CD99",
            color: "#fff",
            border: "none",
            padding: "10px 20px",
            borderRadius: "12px",
            fontWeight: "bold",
            cursor: "pointer",
            display: "flex",
            alignItems: "center",
            gap: "8px",
          }}
        >
          <span>⚡</span> Atendimento Balcão
        </button>
      </div>

      <div style={styles.card}>
        {loading ? (
          <p style={{ textAlign: "center", padding: "20px" }}>
            Carregando base de clientes...
          </p>
        ) : (
          <ClienteTable clientes={clientesFiltrados} />
        )}
      </div>

      {isModalOpen && (
        <div style={styles.overlay}>
          <div style={styles.modal}>
            <h2 style={{ color: "#2B3674", marginTop: 0 }}>
              Atendimento Rápido ⚡
            </h2>
            <p style={{ color: "#A3AED0", marginBottom: "20px" }}>
              Registre o serviço feito agora.
            </p>

            <label style={styles.label}>Serviço (Especialidade)</label>
            <select
              style={styles.inputModal}
              value={novoAtendimento.servicoId}
              onChange={(e) =>
                setNovoAtendimento({
                  ...novoAtendimento,
                  servicoId: e.target.value,
                  funcionarioId: "",
                })
              }
            >
              <option value="">Selecione o serviço...</option>
              {servicos.map((s) => (
                <option key={s.id} value={s.id}>
                  {s.nome}
                </option>
              ))}
            </select>
            <label style={styles.label}>Profissional</label>
            <select
              style={styles.inputModal}
              value={novoAtendimento.funcionarioId}
              onChange={(e) =>
                setNovoAtendimento({
                  ...novoAtendimento,
                  funcionarioId: e.target.value,
                })
              }
            >
              <option value="">Selecione o barbeiro...</option>
              {funcionariosFiltradosBalcao.map((f) => (
                <option key={f.id} value={f.id}>
                  {f.nome}
                </option>
              ))}
            </select>

            <div style={{ display: "flex", gap: "10px", marginTop: "20px" }}>
              <button
                onClick={() => setIsModalOpen(false)}
                style={{
                  flex: 1,
                  padding: "10px",
                  borderRadius: "10px",
                  border: "1px solid #ddd",
                  cursor: "pointer",
                }}
              >
                Cancelar
              </button>
              <button
                onClick={finalizarAtendimentoBalcao}
                style={{
                  flex: 1,
                  padding: "10px",
                  borderRadius: "10px",
                  backgroundColor: "#4318FF",
                  color: "#fff",
                  border: "none",
                  fontWeight: "bold",
                  cursor: "pointer",
                }}
              >
                Finalizar
              </button>
            </div>
          </div>
        </div>
      )}
    </Layout>
  );
}
