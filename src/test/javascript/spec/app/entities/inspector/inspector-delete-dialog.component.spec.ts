import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { ControldecamionesTestModule } from '../../../test.module';
import { InspectorDeleteDialogComponent } from 'app/entities/inspector/inspector-delete-dialog.component';
import { InspectorService } from 'app/entities/inspector/inspector.service';

describe('Component Tests', () => {
  describe('Inspector Management Delete Component', () => {
    let comp: InspectorDeleteDialogComponent;
    let fixture: ComponentFixture<InspectorDeleteDialogComponent>;
    let service: InspectorService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ControldecamionesTestModule],
        declarations: [InspectorDeleteDialogComponent]
      })
        .overrideTemplate(InspectorDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(InspectorDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(InspectorService);
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
