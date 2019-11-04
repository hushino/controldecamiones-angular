import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { ControldecamionesTestModule } from '../../../test.module';
import { InspectorDetailComponent } from 'app/entities/inspector/inspector-detail.component';
import { Inspector } from 'app/shared/model/inspector.model';

describe('Component Tests', () => {
  describe('Inspector Management Detail Component', () => {
    let comp: InspectorDetailComponent;
    let fixture: ComponentFixture<InspectorDetailComponent>;
    const route = ({ data: of({ inspector: new Inspector(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ControldecamionesTestModule],
        declarations: [InspectorDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(InspectorDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(InspectorDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.inspector).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
