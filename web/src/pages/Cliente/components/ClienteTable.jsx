import React from "react";
import { styles } from "../Cliente.styles";

export default function ClienteTable({ clientes }) {
  return (
    <table style={styles.table}>
      <thead>
        <tr>
          <th style={styles.th}>NOME</th>
          <th style={styles.th}>CPF</th>
          <th style={styles.th}>E-MAIL</th>
          <th style={styles.th}>STATUS</th>
        </tr>
      </thead>
      <tbody>
        {clientes.map((c) => (
          <tr key={c.id}>
            <td style={styles.td}>{c.nome}</td>
            <td style={styles.td}>{c.cpf}</td>
            <td style={styles.td}>{c.email}</td>
            <td style={styles.td}>
              <span style={styles.badge(c.ativo !== false)}>
                {c.ativo !== false ? "ATIVO" : "INATIVO"}
              </span>
            </td>
          </tr>
        ))}
      </tbody>
    </table>
  );
}
