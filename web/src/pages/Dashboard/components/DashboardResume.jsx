import React from "react";
import { styles } from "../Dashboard.styles";
import { formatarMoeda } from "@/utils/formatters";

export default function DashboardResume({
  agendamentos,
  faturamento,
  clientesEmRisco,
  ticketMedio,
}) {
  const concluidos = agendamentos.filter((a) => a.concluido).length;
  const pendentes =
    agendamentos.length -
    concluidos -
    agendamentos.filter((a) => a.motivoCancelamento).length;

  return (
    <div style={styles.statsGrid}>
      <StatCard
        title="Receita Dia"
        value={formatarMoeda(faturamento)}
        color="#2E7D32"
      />

      <StatCard
        title="Ticket Médio"
        value={formatarMoeda(ticketMedio)}
        color="#4318FF"
      />

      <StatCard
        title="Clientes Sumidos (+30d)"
        value={clientesEmRisco}
        color={clientesEmRisco > 0 ? "#FF5B5B" : "#05CD99"}
      />

      <StatCard title="Pendentes Hoje" value={pendentes} color="#FFB800" />
    </div>
  );
}

const StatCard = ({ title, value, color }) => (
  <div
    style={{
      ...styles.statCard,
      borderLeft: `5px solid ${color}`,
      background: "#fff",
      padding: "20px",
      borderRadius: "16px",
      boxShadow: "0px 4px 12px rgba(0,0,0,0.05)",
    }}
  >
    <span style={{ fontSize: "0.85rem", color: "#A3AED0", fontWeight: "600" }}>
      {title}
    </span>
    <p
      style={{
        fontSize: "1.5rem",
        fontWeight: "700",
        color: color,
        margin: "5px 0 0 0",
      }}
    >
      {value}
    </p>
  </div>
);
