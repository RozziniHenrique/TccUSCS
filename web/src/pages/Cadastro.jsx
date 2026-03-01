import React, { useState } from "react";
import api from "../services/api";
import { useNavigate } from "react-router-dom";

export default function Cadastro() {
  const [nome, setNome] = useState("");
  const [email, setEmail] = useState("");
  const [senha, setSenha] = useState("");
  const navigate = useNavigate();

  async function handleRegister(e) {
    e.preventDefault();
    try {
      await api.post("/usuarios", { nome, email, senha, perfil: "CLIENTE" });
      alert("Cadastro realizado! Agora faça login.");
      navigate("/");
    } catch (err) {
      alert("Erro ao cadastrar. Tente outro e-mail.");
    }
  }

  return (
    <div style={styles.container}>
      <form onSubmit={handleRegister} style={styles.card}>
        <h1 style={styles.logo}>CRIAR CONTA</h1>
        <input
          placeholder="Nome Completo"
          onChange={(e) => setNome(e.target.value)}
          style={styles.input}
        />
        <input
          placeholder="E-mail"
          onChange={(e) => setEmail(e.target.value)}
          style={styles.input}
        />
        <input
          type="password"
          placeholder="Sua Senha"
          onChange={(e) => setSenha(e.target.value)}
          style={styles.input}
        />
        <button type="submit" style={styles.button}>
          CADASTRAR
        </button>
      </form>
    </div>
  );
}

const styles = {
  container: {
    height: "100vh",
    display: "flex",
    justifyContent: "center",
    alignItems: "center",
  },
  card: {
    backgroundColor: "#1e1e1e",
    padding: "40px",
    borderRadius: "12px",
    width: "100%",
    maxWidth: "400px",
    textAlign: "center",
  },
  logo: { color: "#d4af37", marginBottom: "30px", fontSize: "2.5rem" },
  input: {
    width: "100%",
    padding: "12px",
    marginBottom: "15px",
    borderRadius: "6px",
    border: "1px solid #333",
    backgroundColor: "#121212",
    color: "#fff",
    boxSizing: "border-box",
  },
  button: {
    width: "100%",
    padding: "12px",
    backgroundColor: "#d4af37",
    border: "none",
    borderRadius: "6px",
    fontWeight: "bold",
    cursor: "pointer",
  },
  link: {
    color: "#888",
    display: "block",
    marginTop: "15px",
    textDecoration: "none",
    fontSize: "0.9rem",
  },
};
