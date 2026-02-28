import { useState, useEffect } from 'react';
import api from '../api';
import { CheckCircle, Clock, User } from 'lucide-react';

export function ListaAgendamentos({ onAtualizar }) {
  const [agendamentos, setAgendamentos] = useState([]);

  useEffect(() => {
    carregarAgendamentos();
  }, []);

  async function carregarAgendamentos() {
    try {
      const response = await api.get('/agendamentos');
      setAgendamentos(response.data.content || []);
    } catch (err) {
      console.error("Erro ao carregar agendamentos", err);
    }
  }

  async function finalizarServico(id) {
    const notaStr = prompt("Avalie o atendimento (1 a 5):", "5");
    const nota = parseInt(notaStr);

    if (!isNaN(nota) && nota >= 1 && nota <= 5) {
      try {
        // Rota: PUT /agendamentos/finalizar enviando { id, nota }
        await api.put(`/agendamentos/finalizar`, { id, nota });
        alert("Finalizado com sucesso! ✅");

        carregarAgendamentos(); // Recarrega a lista local
        if (onAtualizar) onAtualizar(); // Atualiza o faturamento no App.jsx
      } catch (err) {
        alert("Erro ao finalizar na API. Verifique o console.");
        console.error(err);
      }
    }
  }

  return (
    <div className="bg-zinc-900 rounded-xl border border-zinc-800 overflow-hidden">
      <table className="w-full text-left border-collapse">
        <thead className="bg-zinc-800/50 text-zinc-400 text-xs uppercase tracking-wider">
          <tr>
            <th className="p-4 font-semibold">Cliente / Barbeiro</th>
            <th className="p-4 font-semibold">Horário</th>
            <th className="p-4 font-semibold text-right">Ação</th>
          </tr>
        </thead>
        <tbody className="divide-y divide-zinc-800">
          {agendamentos.map((agend) => (
            <tr key={agend.id} className="hover:bg-zinc-800/30 transition-colors group">
              <td className="p-4">
                <div className="font-bold text-white text-base">
                  {agend.nomeCliente || "Cliente"}
                </div>
                <div className="text-sm text-zinc-500 flex items-center gap-1">
                  <User size={12} className="text-zinc-600" />
                  {agend.nomeFuncionario}
                </div>
              </td>
              <td className="p-4">
                <div className="flex items-center gap-2 text-zinc-300 font-mono text-sm">
                  <Clock size={14} className="text-emerald-500" />
                  {agend.data
                    ? new Date(agend.data).toLocaleString('pt-BR', {day:'2-digit', month:'2-digit', hour:'2-digit', minute:'2-digit'})
                    : '--:--'}
                </div>
              </td>
              <td className="p-4 text-right">
                <button
                  onClick={() => finalizarServico(agend.id)}
                  className="p-2 bg-emerald-500/10 hover:bg-emerald-500 text-emerald-500 hover:text-white rounded-lg transition-all duration-300"
                  title="Finalizar"
                >
                  <CheckCircle size={20} />
                </button>
              </td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
}