import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Inspector } from 'app/shared/model/inspector.model';
import { InspectorService } from './inspector.service';
import { InspectorComponent } from './inspector.component';
import { InspectorDetailComponent } from './inspector-detail.component';
import { InspectorUpdateComponent } from './inspector-update.component';
import { InspectorDeletePopupComponent } from './inspector-delete-dialog.component';
import { IInspector } from 'app/shared/model/inspector.model';

@Injectable({ providedIn: 'root' })
export class InspectorResolve implements Resolve<IInspector> {
  constructor(private service: InspectorService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IInspector> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<Inspector>) => response.ok),
        map((inspector: HttpResponse<Inspector>) => inspector.body)
      );
    }
    return of(new Inspector());
  }
}

export const inspectorRoute: Routes = [
  {
    path: '',
    component: InspectorComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'Inspectors'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: InspectorDetailComponent,
    resolve: {
      inspector: InspectorResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Inspectors'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: InspectorUpdateComponent,
    resolve: {
      inspector: InspectorResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Inspectors'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: InspectorUpdateComponent,
    resolve: {
      inspector: InspectorResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Inspectors'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const inspectorPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: InspectorDeletePopupComponent,
    resolve: {
      inspector: InspectorResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Inspectors'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
