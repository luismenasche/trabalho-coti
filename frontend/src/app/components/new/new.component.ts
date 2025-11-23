import { Component } from '@angular/core';

import { ClientFormComponent } from '../client-form/client-form.component';

@Component({
  selector: 'app-new',
  imports: [ClientFormComponent],
  templateUrl: './new.component.html',
  styleUrl: './new.component.css',
})
export class NewComponent {}
