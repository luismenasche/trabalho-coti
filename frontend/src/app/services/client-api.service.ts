import { HttpClient } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';

import { ClienteCreateRequestDTO } from '../models/ClienteCreateRequestDTO';
import { ClienteUpdateRequestDTO } from '../models/ClienteUpdateRequestDTO';

@Injectable({
  providedIn: 'root',
})
export class ClientApiService {
  private httpClient = inject(HttpClient);

  private endpoint = 'http://localhost:8080/api/clientes';

  getAll() {
    return this.httpClient.get(this.endpoint);
  }

  getById(id: string) {
    return this.httpClient.get(this.endpoint + '/' + id);
  }

  post(cliente: ClienteCreateRequestDTO) {
    return this.httpClient.post(this.endpoint, cliente);
  }

  put(cliente: ClienteUpdateRequestDTO) {
    return this.httpClient.put(this.endpoint, cliente);
  }

  delete(id: string) {
    return this.httpClient.delete(this.endpoint + '/' + id);
  }
}
