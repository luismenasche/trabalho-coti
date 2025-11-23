import { Injectable } from '@angular/core';

import { ClienteUpdateRequestDTO } from '../models/ClienteUpdateRequestDTO';

@Injectable({
  providedIn: 'root',
})
export class ClienteUpdateStorageService {
  get() {
    const clienteJson = sessionStorage.getItem('clienteUpdate');
    let cliente: ClienteUpdateRequestDTO | null;
    if (!clienteJson) cliente = null;
    else cliente = JSON.parse(clienteJson) as ClienteUpdateRequestDTO;
    return cliente;
  }

  set(novoCliente: ClienteUpdateRequestDTO | null) {
    if (!novoCliente) sessionStorage.removeItem('clienteUpdate');
    else sessionStorage.setItem('clienteUpdate', JSON.stringify(novoCliente));
  }
}
