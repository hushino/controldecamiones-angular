import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IInspector } from 'app/shared/model/inspector.model';
import { InspectorService } from './inspector.service';

@Component({
  selector: 'jhi-inspector-delete-dialog',
  templateUrl: './inspector-delete-dialog.component.html'
})
export class InspectorDeleteDialogComponent {
  inspector: IInspector;

  constructor(protected inspectorService: InspectorService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.inspectorService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'inspectorListModification',
        content: 'Deleted an inspector'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-inspector-delete-popup',
  template: ''
})
export class InspectorDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ inspector }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(InspectorDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.inspector = inspector;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/inspector', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/inspector', { outlets: { popup: null } }]);
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
