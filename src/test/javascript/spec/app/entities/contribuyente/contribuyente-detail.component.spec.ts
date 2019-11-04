import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { ControldecamionesTestModule } from '../../../test.module';
import { ContribuyenteDetailComponent } from 'app/entities/contribuyente/contribuyente-detail.component';
import { Contribuyente } from 'app/shared/model/contribuyente.model';

describe('Component Tests', () => {
  describe('Contribuyente Management Detail Component', () => {
    let comp: ContribuyenteDetailComponent;
    let fixture: ComponentFixture<ContribuyenteDetailComponent>;
    const route = ({ data: of({ contribuyente: new Contribuyente(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ControldecamionesTestModule],
        declarations: [ContribuyenteDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(ContribuyenteDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ContribuyenteDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.contribuyente).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
