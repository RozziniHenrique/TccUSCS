package uscs.STEFER.model.Usuario;

public enum UsuarioRole {
    ADMIN("admin"),
    GESTOR("gestor"),
    FUNCIONARIO("funcionario"),
    CLIENTE("cliente");

    private String role;

    UsuarioRole(String role) {
        this.role = role;
    }

    public String getRole() {
        return role;
    }
}
