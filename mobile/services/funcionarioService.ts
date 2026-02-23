import api from './api';

export const listarFuncionarios = async (idEspecialidade?: number) => {
    try {
        const url = idEspecialidade ? `/funcionarios?idEspecialidade=${idEspecialidade}` : '/funcionarios';

        const response = await api.get(url);
        return response.data.content;
    } catch (error) {
        console.error("Erro na chamada da API:", error);
        throw error;
    }
};