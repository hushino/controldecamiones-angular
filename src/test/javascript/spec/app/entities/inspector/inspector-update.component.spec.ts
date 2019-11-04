import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { ControldecamionesTestModule } from '../../../test.module';
import { InspectorUpdateComponent } from 'app/entities/inspector/inspector-update.component';
import { InspectorService } from 'app/entities/inspector/inspector.service';
import { Inspector } from 'app/shared/model/inspector.model';

describe('Component Tests', () => {
  describe('Inspector Management Update Component', () => {
    let comp: InspectorUpdateComponent;
    let fixture: ComponentFixture<InspectorUpdateComponent>;
    let service: InspectorService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ControldecamionesTestModule],
        declarations: [InspectorUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(InspectorUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(InspectorUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(InspectorService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Inspector(123);
        spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.update).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));

      it('Should call create service on save for new entity', fakeAsync(() => {
        // GIVEN
        const entity = new Inspector();
        spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.create).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));
    });
  });
});
