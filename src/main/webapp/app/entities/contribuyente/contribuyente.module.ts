import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ControldecamionesSharedModule } from 'app/shared/shared.module';
import { ContribuyenteComponent } from './contribuyente.component';
import { ContribuyenteDetailComponent } from './contribuyente-detail.component';
import { ContribuyenteUpdateComponent } from './contribuyente-update.component';
import { ContribuyenteDeletePopupComponent, ContribuyenteDeleteDialogComponent } from './contribuyente-delete-dialog.component';
import { contribuyenteRoute, contribuyentePopupRoute } from './contribuyente.route';

const ENTITY_STATES = [...contribuyenteRoute, ...contribuyentePopupRoute];

@NgModule({
  imports: [ControldecamionesSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    ContribuyenteComponent,
    ContribuyenteDetailComponent,
    ContribuyenteUpdateComponent,
    ContribuyenteDeleteDialogComponent,
    ContribuyenteDeletePopupComponent
  ],
  entryComponents: [ContribuyenteDeleteDialogComponent]
})
export class ControldecamionesContribuyenteModule {}
