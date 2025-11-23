import { Component, inject, input, output } from '@angular/core';
import { Router } from '@angular/router';

import { ClientApiService } from '../../services/client-api.service';
import { ClienteUpdateStorageService } from '../../services/cliente-update-storage.service';

import { ClienteResponseDTO } from '../../models/ClienteResponseDTO';
import { ClienteUpdateRequestDTO } from '../../models/ClienteUpdateRequestDTO';

@Component({
  selector: 'app-client-view',
  imports: [],
  templateUrl: './client-view.component.html',
  styleUrl: './client-view.component.css',
})
export class ClientViewComponent {
  cliente = input.required<ClienteResponseDTO>();
  afterDelete = output();

  private apiClientes = inject(ClientApiService);
  private clienteStorage = inject(ClienteUpdateStorageService);
  private router = inject(Router);

  atualizar() {
    const cliente = this.cliente();
    const clienteUpdate: ClienteUpdateRequestDTO = {
      id: cliente.id,
      nome: cliente.nome,
      email: cliente.email,
      cpf: cliente.cpf,
      dataNascimento: cliente.dataNascimento,
      endereco: {
        id: cliente.enderecos[0].id,
        logradouro: cliente.enderecos[0].logradouro,
        numero: cliente.enderecos[0].numero,
        complemento: cliente.enderecos[0].complemento,
        bairro: cliente.enderecos[0].bairro,
        cidade: cliente.enderecos[0].cidade,
        uf: cliente.enderecos[0].uf,
        cep: cliente.enderecos[0].cep,
      },
    };
    this.clienteStorage.set(clienteUpdate);
    this.router.navigate(['/atualiza']);
  }

  excluir() {
    const c = confirm('Deseja realmente realizar a exclusão?');
    if (!c) return;
    const id = this.cliente().id;
    this.apiClientes.delete(id).subscribe(() => {
      this.afterDelete.emit();
    });
  }
}
