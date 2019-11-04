import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ControldecamionesSharedModule } from 'app/shared/shared.module';
import { InspectorComponent } from './inspector.component';
import { InspectorDetailComponent } from './inspector-detail.component';
import { InspectorUpdateComponent } from './inspector-update.component';
import { InspectorDeletePopupComponent, InspectorDeleteDialogComponent } from './inspector-delete-dialog.component';
import { inspectorRoute, inspectorPopupRoute } from './inspector.route';

const ENTITY_STATES = [...inspectorRoute, ...inspectorPopupRoute];

@NgModule({
  imports: [ControldecamionesSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    InspectorComponent,
    InspectorDetailComponent,
    InspectorUpdateComponent,
    InspectorDeleteDialogComponent,
    InspectorDeletePopupComponent
  ],
  entryComponents: [InspectorDeleteDialogComponent]
})
export class ControldecamionesInspectorModule {}
