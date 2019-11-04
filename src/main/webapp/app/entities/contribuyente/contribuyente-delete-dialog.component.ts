import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IContribuyente } from 'app/shared/model/contribuyente.model';
import { ContribuyenteService } from './contribuyente.service';

@Component({
  selector: 'jhi-contribuyente-delete-dialog',
  templateUrl: './contribuyente-delete-dialog.component.html'
})
export class ContribuyenteDeleteDialogComponent {
  contribuyente: IContribuyente;

  constructor(
    protected contribuyenteService: ContribuyenteService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.contribuyenteService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'contribuyenteListModification',
        content: 'Deleted an contribuyente'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-contribuyente-delete-popup',
  template: ''
})
export class ContribuyenteDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ contribuyente }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(ContribuyenteDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.contribuyente = contribuyente;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/contribuyente', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/contribuyente', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          }
        );
      }, 0);
    });
  }

  ngOnDestroy() {
    this.ngbModalRef = null;
  }
}
