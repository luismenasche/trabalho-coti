import { Component, inject } from '@angular/core';

import { ConsultaIdFormComponent } from '../consulta-id-form/consulta-id-form.component';
import { ClientViewComponent } from '../client-view/client-view.component';
import { BtnVoltarComponent } from '../btn-voltar/btn-voltar.component';

import { ClientApiService } from '../../services/client-api.service';

import { ClienteResponseDTO } from '../../models/ClienteResponseDTO';

@Component({
  selector: 'app-consulta-id',
  imports: [ConsultaIdFormComponent, ClientViewComponent, BtnVoltarComponent],
  templateUrl: './consulta-id.component.html',
  styleUrl: './consulta-id.component.css',
})
export class ConsultaIdComponent {
  private apiClientes = inject(ClientApiService);

  cliente: ClienteResponseDTO | null = null;

  consulta(id: string) {
    this.apiClientes.getById(id).subscribe({
      next: (response) => (this.cliente = response as ClienteResponseDTO),
      error: () => {
        this.cliente = null;
        alert('Cliente não encontrado!');
      },
    });
  }

  afterDelete() {
    this.cliente = null;
  }
}
