import { Component, inject, OnInit } from '@angular/core';

import { ClientViewComponent } from '../client-view/client-view.component';
import { BtnVoltarComponent } from '../btn-voltar/btn-voltar.component';

import { ClientApiService } from '../../services/client-api.service';

import { ClienteResponseDTO } from '../../models/ClienteResponseDTO';

@Component({
  selector: 'app-list',
  imports: [ClientViewComponent, BtnVoltarComponent],
  templateUrl: './list.component.html',
  styleUrl: './list.component.css',
})
export class ListComponent implements OnInit {
  private apiClientes = inject(ClientApiService);

  clientes: ClienteResponseDTO[] = [];

  ngOnInit(): void {
    this.apiClientes
      .getAll()
      .subscribe(
        (response) => (this.clientes = response as ClienteResponseDTO[])
      );
  }

  afterDelete(index: number) {
    this.clientes.splice(index, 1);
  }
}
