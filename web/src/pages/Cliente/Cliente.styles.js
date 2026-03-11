export const styles = {
  card: {
    backgroundColor: "#FFF",
    padding: "25px",
    borderRadius: "15px",
    boxShadow: "0 10px 20px rgba(0,0,0,0.05)",
    border: "1px solid #E0E5F2",
  },
  table: { width: "100%", borderCollapse: "collapse" },
  th: {
    textAlign: "left",
    padding: "15px",
    color: "#A3AED0",
    fontSize: "0.8rem",
    fontWeight: "800",
    borderBottom: "2px solid #F4F7FE",
  },
  td: {
    padding: "15px",
    color: "#2B3674",
    fontSize: "0.95rem",
    borderBottom: "1px solid #F4F7FE",
    fontWeight: "500",
  },
  badge: (ativo) => ({
    padding: "5px 12px",
    borderRadius: "20px",
    fontSize: "0.7rem",
    fontWeight: "bold",
    backgroundColor: ativo ? "#E8F5E9" : "#FFEBEE",
    color: ativo ? "#2E7D32" : "#D32F2F",
  }),
};
