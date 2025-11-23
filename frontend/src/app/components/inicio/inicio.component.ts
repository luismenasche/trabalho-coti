import { Component, inject } from '@angular/core';
import { Router } from '@angular/router';

import { ClientApiService } from '../../services/client-api.service';

@Component({
  selector: 'app-inicio',
  imports: [],
  templateUrl: './inicio.component.html',
  styleUrl: './inicio.component.css',
})
export class InicioComponent {
  private router = inject(Router);
  private clientApi = inject(ClientApiService);

  irPara(rota: string) {
    this.router.navigate([rota]);
  }
}
