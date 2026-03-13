export const filtrarFuncionariosPorServico = (funcionarios, idServico) => {
  if (!idServico) return funcionarios;
  return funcionarios.filter((f) =>
    f.especialidades?.some((esp) => String(esp.id) === String(idServico)),
  );
};
