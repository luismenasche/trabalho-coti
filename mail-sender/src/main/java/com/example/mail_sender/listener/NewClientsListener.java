package com.example.mail_sender.listener;

import com.example.mail_sender.dto.MensagemClienteDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
@RequiredArgsConstructor
public class NewClientsListener {
    private final JavaMailSender javaMailSender;
    private final ObjectMapper objectMapper;

    @RabbitListener(queues = "${spring.rabbitmq.mailqueue}")
    public void listener(MensagemClienteDTO mensagem) {
        try {
            String prettyJson = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(mensagem);

            System.out.println(prettyJson);

            SimpleMailMessage mail = new SimpleMailMessage();
            mail.setTo(mensagem.getEmail());
            mail.setSubject("Novo Cliente Cadastrado em " +
                    LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy hh:mm:ss"))
            );
            mail.setText("Um novo cliente foi cadastrado com sucesso.\n\nDados:\n\n" + prettyJson);
            javaMailSender.send(mail);
        } catch (JsonProcessingException ex) {
            ex.printStackTrace();
        }
    }
}
