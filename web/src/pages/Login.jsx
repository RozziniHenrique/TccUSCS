import React, { useState } from "react";
import api from "../services/api";
import { useNavigate, Link } from "react-router-dom";

export default function Login() {
  const [email, setEmail] = useState("");
  const [senha, setSenha] = useState("");
  const [loading, setLoading] = useState(false);
  const navigate = useNavigate();

  async function handleLogin(e) {
    e.preventDefault();
    try {
      const response = await api.post("/login", {
        login: email,
        senha: senha,
      });

      localStorage.setItem("user", JSON.stringify(response.data));
      navigate("/dashboard");
    } catch (err) {
      alert("Acesso negado. Verifique suas credenciais.");
    }
  }

  return (
    <div style={styles.container}>
      <form onSubmit={handleLogin} style={styles.card}>
        <h1 style={styles.logo}>STEFER 💈</h1>

        <input
          type="email"
          placeholder="E-mail"
          value={email}
          onChange={(e) => setEmail(e.target.value)}
          required
          style={styles.input}
        />

        <input
          type="password"
          placeholder="Senha"
          value={senha}
          onChange={(e) => setSenha(e.target.value)}
          required
          style={styles.input}
        />

        <button
          type="submit"
          style={loading ? { ...styles.button, opacity: 0.7 } : styles.button}
          disabled={loading}
        >
          {loading ? "CARREGANDO..." : "ENTRAR"}
        </button>

        <Link to="/cadastro" style={styles.link}>
          Ainda não tem conta? Cadastre-se
        </Link>
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
