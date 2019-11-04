import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'contribuyente',
        loadChildren: () => import('./contribuyente/contribuyente.module').then(m => m.ControldecamionesContribuyenteModule)
      },
      {
        path: 'inspector',
        loadChildren: () => import('./inspector/inspector.module').then(m => m.ControldecamionesInspectorModule)
      }
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ])
  ]
})
export class ControldecamionesEntityModule {}
