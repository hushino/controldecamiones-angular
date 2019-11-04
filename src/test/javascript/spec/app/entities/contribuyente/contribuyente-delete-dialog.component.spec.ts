import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { ControldecamionesTestModule } from '../../../test.module';
import { ContribuyenteDeleteDialogComponent } from 'app/entities/contribuyente/contribuyente-delete-dialog.component';
import { ContribuyenteService } from 'app/entities/contribuyente/contribuyente.service';

describe('Component Tests', () => {
  describe('Contribuyente Management Delete Component', () => {
    let comp: ContribuyenteDeleteDialogComponent;
    let fixture: ComponentFixture<ContribuyenteDeleteDialogComponent>;
    let service: ContribuyenteService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ControldecamionesTestModule],
        declarations: [ContribuyenteDeleteDialogComponent]
      })
        .overrideTemplate(ContribuyenteDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ContribuyenteDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ContribuyenteService);
      mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
      mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
    });

    describe('confirmDelete', () => {
      it('Should call delete service on confirmDelete', inject(
        [],
        fakeAsync(() => {
          // GIVEN
          spyOn(service, 'delete').and.returnValue(of({}));

          // WHEN
          comp.confirmDelete(123);
          tick();

          // THEN
          expect(service.delete).toHaveBeenCalledWith(123);
          expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
          expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
        })
      ));
    });
  });
});
