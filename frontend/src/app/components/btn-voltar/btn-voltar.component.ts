import { Component, inject } from '@angular/core';
import { Location } from '@angular/common';

@Component({
  selector: 'app-btn-voltar',
  imports: [],
  templateUrl: './btn-voltar.component.html',
  styleUrl: './btn-voltar.component.css',
})
export class BtnVoltarComponent {
  private location = inject(Location);

  voltar() {
    this.location.back();
  }
}
