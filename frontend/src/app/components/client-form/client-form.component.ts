import { NgClass, NgIf } from '@angular/common';
import { Component, inject, input, OnInit } from '@angular/core';
import {
  FormGroup,
  FormBuilder,
  Validators,
  FormsModule,
  ReactiveFormsModule,
} from '@angular/forms';
import { Router } from '@angular/router';

import { BtnVoltarComponent } from '../btn-voltar/btn-voltar.component';

import { ClientApiService } from '../../services/client-api.service';
import { ClienteUpdateStorageService } from '../../services/cliente-update-storage.service';

import { ClienteCreateRequestDTO } from '../../models/ClienteCreateRequestDTO';
import { ClienteUpdateRequestDTO } from '../../models/ClienteUpdateRequestDTO';

@Component({
  selector: 'app-client-form',
  imports: [
    NgIf,
    FormsModule,
    ReactiveFormsModule,
    NgClass,
    BtnVoltarComponent,
  ],
  templateUrl: './client-form.component.html',
  styleUrl: './client-form.component.css',
})
export class ClientFormComponent implements OnInit {
  tipo = input.required<0 | 1>();

  private apiClientes = inject(ClientApiService);
  private clienteStorage = inject(ClienteUpdateStorageService);
  private router = inject(Router);

  form: FormGroup;
  tituloBotao1 = '';
  tituloBotao2 = '';
  cliente: ClienteUpdateRequestDTO | null = null;

  constructor(private fb: FormBuilder) {
    this.form = this.fb.group({
      nome: [
        '',
        [
          Validators.required,
          Validators.minLength(8),
          Validators.maxLength(100),
        ],
      ],
      email: ['', [Validators.required, Validators.email]],
      cpf: ['', [Validators.required, Validators.pattern(/^\d{11}$/)]],
      dataNascimento: ['', Validators.required],
      logradouro: '',
      complemento: '',
      numero: '',
      bairro: '',
      cidade: '',
      uf: '',
      cep: '',
    });
  }

  load() {
    if (this.cliente == null) {
      this.router.navigate(['/novo']);
      return;
    }
    this.form.setValue({
      nome: this.cliente.nome,
      email: this.cliente.email,
      cpf: this.cliente.cpf,
      dataNascimento: this.cliente.dataNascimento,
      logradouro: this.cliente.endereco.logradouro,
      complemento: this.cliente.endereco.complemento,
      numero: this.cliente.endereco.numero,
      bairro: this.cliente.endereco.bairro,
      cidade: this.cliente.endereco.cidade,
      uf: this.cliente.endereco.uf,
      cep: this.cliente.endereco.cep,
    });
  }

  ngOnInit(): void {
    const tipo = this.tipo();
    if (tipo == 0) {
      this.tituloBotao1 = 'Cadastrar';
      this.tituloBotao2 = 'Limpar';
    } else {
      this.tituloBotao1 = 'Atualizar';
      this.tituloBotao2 = 'Restaurar';
      this.cliente = this.clienteStorage.get();
      this.load();
    }
  }

  create() {
    const novoCliente: ClienteCreateRequestDTO = {
      nome: this.form.value['nome'],
      cpf: this.form.value['cpf'],
      dataNascimento: this.form.value['dataNascimento'],
      email: this.form.value['email'],
      endereco: {
        logradouro: this.form.value['logradouro'],
        numero: this.form.value['numero'],
        complemento: this.form.value['complemento'],
        bairro: this.form.value['bairro'],
        cidade: this.form.value['cidade'],
        uf: this.form.value['uf'],
        cep: this.form.value['cep'],
      },
    };
    this.apiClientes.post(novoCliente).subscribe({
      next: () => {
        alert('Cliente cadastrado com sucesso!');
        this.router.navigate(['/']);
      },
      error: () => {
        alert(
          'Não foi possível realizar o cadastro. O CPF informado já foi cadastrado anteriormente.'
        );
      },
    });
  }

  update() {
    if (this.cliente == null) return;
    const novoCliente: ClienteUpdateRequestDTO = {
      id: this.cliente.id,
      nome: this.form.value['nome'],
      cpf: this.form.value['cpf'],
      dataNascimento: this.form.value['dataNascimento'],
      email: this.form.value['email'],
      endereco: {
        id: this.cliente.endereco.id,
        logradouro: this.form.value['logradouro'],
        numero: this.form.value['numero'],
        complemento: this.form.value['complemento'],
        bairro: this.form.value['bairro'],
        cidade: this.form.value['cidade'],
        uf: this.form.value['uf'],
        cep: this.form.value['cep'],
      },
    };
    this.apiClientes.put(novoCliente).subscribe({
      next: () => {
        alert('Cliente atualizado com sucesso!');
        this.clienteStorage.set(novoCliente);
        this.router.navigate(['/']);
      },
      error: (error) => {
        alert(
          'Não foi possível realizar a atualização. O CPF informado já foi cadastrado anteriormente.'
        );
      },
    });
  }

  onSubmit() {
    const tipo = this.tipo();
    if (tipo == 0) this.create();
    else this.update();
  }

  onClean() {
    const tipo = this.tipo();
    if (tipo == 0) this.form.reset();
    else this.load();
    this.form.markAsUntouched();
  }

  isInvalid(field: string) {
    const control = this.form.get(field);
    return control?.invalid && control?.touched;
  }
}
