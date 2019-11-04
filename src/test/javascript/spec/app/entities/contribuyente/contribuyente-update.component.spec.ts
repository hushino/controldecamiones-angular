import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { ControldecamionesTestModule } from '../../../test.module';
import { ContribuyenteUpdateComponent } from 'app/entities/contribuyente/contribuyente-update.component';
import { ContribuyenteService } from 'app/entities/contribuyente/contribuyente.service';
import { Contribuyente } from 'app/shared/model/contribuyente.model';

describe('Component Tests', () => {
  describe('Contribuyente Management Update Component', () => {
    let comp: ContribuyenteUpdateComponent;
    let fixture: ComponentFixture<ContribuyenteUpdateComponent>;
    let service: ContribuyenteService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ControldecamionesTestModule],
        declarations: [ContribuyenteUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(ContribuyenteUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ContribuyenteUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ContribuyenteService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Contribuyente(123);
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
        const entity = new Contribuyente();
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
