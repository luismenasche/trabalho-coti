import { Component } from '@angular/core';

import { ClientFormComponent } from '../client-form/client-form.component';

@Component({
  selector: 'app-edit',
  imports: [ClientFormComponent],
  templateUrl: './edit.component.html',
  styleUrl: './edit.component.css',
})
export class EditComponent {}
