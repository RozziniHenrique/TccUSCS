import React from "react";
import { styles } from "../Cliente.styles";

export default function ClienteTable({ clientes }) {
  const formatarMoeda = (valor) => {
    return new Intl.NumberFormat("pt-BR", {
      style: "currency",
      currency: "BRL",
    }).format(valor || 0);
  };

  return (
    <table style={styles.table}>
      <thead>
        <tr>
          <th style={styles.th}>NOME / ID</th>
          <th style={styles.th}>CONTATO</th>
          <th style={styles.th}>ÚLTIMA VISITA</th>
          <th style={{ ...styles.th, textAlign: "center" }}>VISITAS</th>
          <th style={styles.th}>TOTAL GASTO</th>
          <th style={styles.th}>STATUS</th>
        </tr>
      </thead>
      <tbody>
        {clientes.length === 0 ? (
          <tr>
            <td
              colSpan="6"
              style={{ textAlign: "center", padding: "20px", color: "#A3AED0" }}
            >
              Nenhum cliente encontrado na base de dados.
            </td>
          </tr>
        ) : (
          clientes.map((c) => {
            const isBalcao =
              c.nome?.toUpperCase().includes("BALCÃO") ||
              c.nome?.toUpperCase().includes("AVULSO");

            return (
              <tr
                key={c.id}
                style={{ backgroundColor: isBalcao ? "#F4F7FE" : "inherit" }}
              >
                <td style={styles.td}>
                  <div
                    style={{
                      fontWeight: "700",
                      color: isBalcao ? "#4318FF" : "#2B3674",
                      display: "flex",
                      alignItems: "center",
                      gap: "5px",
                    }}
                  >
                    {c.nome} {isBalcao && "⭐"}
                  </div>
                  <div style={{ fontSize: "0.75rem", color: "#A3AED0" }}>
                    ID: #{c.id}
                  </div>
                </td>

                <td style={styles.td}>
                  <div style={{ fontSize: "0.85rem", color: "#2B3674" }}>
                    {c.email}
                  </div>
                  {c.telefone ? (
                    <a
                      href={`https://wa.me/55${c.telefone.replace(/\D/g, "")}`}
                      target="_blank"
                      rel="noreferrer"
                      style={{
                        fontSize: "0.75rem",
                        color: "#05CD99",
                        textDecoration: "none",
                        fontWeight: "bold",
                        display: "block",
                        marginTop: "2px",
                      }}
                    >
                      {c.telefone} 🟢
                    </a>
                  ) : (
                    <span style={{ fontSize: "0.7rem", color: "#A3AED0" }}>
                      Sem telefone
                    </span>
                  )}
                </td>

                <td style={styles.td}>
                  <span style={{ fontSize: "0.9rem", color: "#2B3674" }}>
                    {c.ultimaVisita
                      ? new Date(c.ultimaVisita).toLocaleDateString()
                      : "---"}
                  </span>
                </td>

                <td style={{ ...styles.td, textAlign: "center" }}>
                  <span
                    style={{
                      background:
                        c.totalAgendamentos > 0 ? "#E2FFF3" : "#F4F7FE",
                      padding: "4px 12px",
                      borderRadius: "12px",
                      color: c.totalAgendamentos > 0 ? "#05CD99" : "#A3AED0",
                      fontWeight: "700",
                      fontSize: "0.85rem",
                    }}
                  >
                    {c.totalAgendamentos || 0}
                  </span>
                </td>

                <td
                  style={{ ...styles.td, fontWeight: "700", color: "#4318FF" }}
                >
                  {formatarMoeda(c.totalGasto)}
                </td>

                <td style={styles.td}>
                  <span style={styles.badge(c.ativo !== false)}>
                    {c.ativo !== false ? "ATIVO" : "INATIVO"}
                  </span>
                </td>
              </tr>
            );
          })
        )}
      </tbody>
    </table>
  );
}
