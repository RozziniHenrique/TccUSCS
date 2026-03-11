import React, { useState, useEffect } from "react";
import api from "@/services/api";
import Layout from "@/components/layout";
import ClienteTable from "./components/ClienteTable";
import { styles } from "./Cliente.styles";

export default function Clientes() {
  const [clientes, setClientes] = useState([]);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    async function carregarClientes() {
      try {
        const res = await api.get("/clientes");
        const lista = res.data.content || res.data || [];
        setClientes(lista);
      } catch (err) {
        console.error("Erro ao carregar clientes:", err);
      } finally {
        setLoading(false);
      }
    }
    carregarClientes();
  }, []);

  return (
    <Layout titulo="👥 Clientes Cadastrados">
      <div style={styles.card}>
        {loading ? (
          <p style={{ color: "#2B3674", textAlign: "center" }}>Carregando...</p>
        ) : (
          <ClienteTable clientes={clientes} />
        )}
      </div>
    </Layout>
  );
}
