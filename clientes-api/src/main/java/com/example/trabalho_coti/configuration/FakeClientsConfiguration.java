package com.example.trabalho_coti.configuration;

import com.example.trabalho_coti.dto.basic.EnderecoDTO;
import com.example.trabalho_coti.dto.controller.ClienteRequestDTO;
import com.example.trabalho_coti.service.ClienteService;
import com.github.javafaker.Faker;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.time.ZoneId;

@Configuration
@RequiredArgsConstructor
public class FakeClientsConfiguration {
    private final Faker faker = new Faker();
    private final ClienteService clienteService;

    @Bean
    public CommandLineRunner populateWithFakeClients() {
        return (args -> {
            for (int i = 0; i < 10; i++) {
                ClienteRequestDTO fakeClient = new ClienteRequestDTO();
                fakeClient.setNome(faker.name().fullName());
                fakeClient.setEmail(faker.internet().emailAddress());
                fakeClient.setCpf(faker.number().digits(11));
                fakeClient.setDataNascimento(LocalDate.ofInstant(
                        faker.date().birthday().toInstant(), ZoneId.systemDefault()));
                EnderecoDTO fakeAddress = new EnderecoDTO();
                fakeAddress.setLogradouro(faker.address().streetName());
                fakeAddress.setNumero(faker.address().buildingNumber());
                fakeAddress.setComplemento(faker.address().secondaryAddress());
                fakeAddress.setBairro(faker.address().cityPrefix());
                fakeAddress.setCidade(faker.address().city());
                fakeAddress.setUf(faker.address().stateAbbr());
                fakeAddress.setCep(faker.number().digits(8));
                fakeClient.setEndereco(fakeAddress);
                clienteService.create(fakeClient);
            }
        });
    }
}
