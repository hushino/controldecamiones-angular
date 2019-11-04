import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Contribuyente } from 'app/shared/model/contribuyente.model';
import { ContribuyenteService } from './contribuyente.service';
import { ContribuyenteComponent } from './contribuyente.component';
import { ContribuyenteDetailComponent } from './contribuyente-detail.component';
import { ContribuyenteUpdateComponent } from './contribuyente-update.component';
import { ContribuyenteDeletePopupComponent } from './contribuyente-delete-dialog.component';
import { IContribuyente } from 'app/shared/model/contribuyente.model';

@Injectable({ providedIn: 'root' })
export class ContribuyenteResolve implements Resolve<IContribuyente> {
  constructor(private service: ContribuyenteService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IContribuyente> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<Contribuyente>) => response.ok),
        map((contribuyente: HttpResponse<Contribuyente>) => contribuyente.body)
      );
    }
    return of(new Contribuyente());
  }
}

export const contribuyenteRoute: Routes = [
  {
    path: '',
    component: ContribuyenteComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'Contribuyentes'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: ContribuyenteDetailComponent,
    resolve: {
      contribuyente: ContribuyenteResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Contribuyentes'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: ContribuyenteUpdateComponent,
    resolve: {
      contribuyente: ContribuyenteResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Contribuyentes'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: ContribuyenteUpdateComponent,
    resolve: {
      contribuyente: ContribuyenteResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Contribuyentes'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const contribuyentePopupRoute: Routes = [
  {
    path: ':id/delete',
    component: ContribuyenteDeletePopupComponent,
    resolve: {
      contribuyente: ContribuyenteResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Contribuyentes'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
