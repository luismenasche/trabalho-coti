import { NgClass, NgIf } from '@angular/common';
import { Component, output } from '@angular/core';
import {
  FormGroup,
  FormBuilder,
  Validators,
  FormsModule,
  ReactiveFormsModule,
} from '@angular/forms';

@Component({
  selector: 'app-consulta-id-form',
  imports: [NgIf, FormsModule, ReactiveFormsModule, NgClass],
  templateUrl: './consulta-id-form.component.html',
  styleUrl: './consulta-id-form.component.css',
})
export class ConsultaIdFormComponent {
  onSubmit = output<string>();

  form: FormGroup;

  // Regex para validar UUID
  private uuidRegex =
    /^[0-9a-fA-F]{8}\-[0-9a-fA-F]{4}\-[0-9a-fA-F]{4}\-[0-9a-fA-F]{4}\-[0-9a-fA-F]{12}$/;

  constructor(private fb: FormBuilder) {
    this.form = this.fb.group({
      id: ['', [Validators.required, Validators.pattern(this.uuidRegex)]],
    });
  }

  isInvalid(field: string) {
    const control = this.form.get(field);
    return control?.invalid && control?.touched;
  }

  consultar() {
    if (this.form.valid) {
      const id = this.form.value.id as string;
      this.onSubmit.emit(id);
    }
  }
}
