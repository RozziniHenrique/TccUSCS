package uscs.STEFER.infra;

import org.springframework.stereotype.Service;
import uscs.STEFER.model.Agendamento.Agendamento;

@Service
public class EmailService {

    public void enviarConfirmacao(Agendamento agendamento){
        System.out.println("\n--- [STEFER - NOTIFICAÇÃO DE SISTEMA] ---");
        System.out.println("Destinatário: " + agendamento.getCliente().getEmail());
        System.out.println("Assunto: Sua consulta foi agendada!");
        System.out.println("------------------------------------------");
        System.out.println("Olá, " + agendamento.getCliente().getNome() + "!");
        System.out.println("Sua consulta com o(a) " + agendamento.getFuncionario().getNome());
        System.out.println("foi confirmada para o dia: " + agendamento.getData());
        System.out.println("Especialidade: " + agendamento.getEspecialidade().getNome());
        System.out.println("------------------------------------------\n");
    }
}
