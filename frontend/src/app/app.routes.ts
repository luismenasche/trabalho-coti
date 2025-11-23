import { Routes } from '@angular/router';

import { ListComponent } from './components/list/list.component';
import { InicioComponent } from './components/inicio/inicio.component';
import { ConsultaIdComponent } from './components/consulta-id/consulta-id.component';
import { NewComponent } from './components/new/new.component';
import { EditComponent } from './components/edit/edit.component';

export const routes: Routes = [
  {
    path: 'atualiza', // Update / PUT
    component: EditComponent,
  },
  {
    path: 'consultaId', // Get por Id / GET
    component: ConsultaIdComponent,
  },
  {
    path: 'lista', // Get All / GET
    component: ListComponent,
  },
  {
    path: 'novo', // Create / POST
    component: NewComponent,
  },
  {
    path: '', // Menu Principal
    pathMatch: 'full',
    component: InicioComponent,
  },
];
