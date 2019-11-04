import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { take, map } from 'rxjs/operators';
import { InspectorService } from 'app/entities/inspector/inspector.service';
import { IInspector, Inspector } from 'app/shared/model/inspector.model';

describe('Service Tests', () => {
  describe('Inspector Service', () => {
    let injector: TestBed;
    let service: InspectorService;
    let httpMock: HttpTestingController;
    let elemDefault: IInspector;
    let expectedResult;
    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule]
      });
      expectedResult = {};
      injector = getTestBed();
      service = injector.get(InspectorService);
      httpMock = injector.get(HttpTestingController);

      elemDefault = new Inspector(0, 'image/png', 'AAAAAAA', 'image/png', 'AAAAAAA', 'AAAAAAA', 'AAAAAAA', 0, 'AAAAAAA', 'AAAAAAA');
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign({}, elemDefault);
        service
          .find(123)
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject({ body: elemDefault });
      });

      it('should create a Inspector', () => {
        const returnedFromService = Object.assign(
          {
            id: 0
          },
          elemDefault
        );
        const expected = Object.assign({}, returnedFromService);
        service
          .create(new Inspector(null))
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp));
        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject({ body: expected });
      });

      it('should update a Inspector', () => {
        const returnedFromService = Object.assign(
          {
            fotopatente: 'BBBBBB',
            fotocamion: 'BBBBBB',
            vehiculomodelo: 'BBBBBB',
            infoadicional: 'BBBBBB',
            celular: 1,
            cuit: 'BBBBBB',
            patente: 'BBBBBB'
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);
        service
          .update(expected)
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp));
        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject({ body: expected });
      });

      it('should return a list of Inspector', () => {
        const returnedFromService = Object.assign(
          {
            fotopatente: 'BBBBBB',
            fotocamion: 'BBBBBB',
            vehiculomodelo: 'BBBBBB',
            infoadicional: 'BBBBBB',
            celular: 1,
            cuit: 'BBBBBB',
            patente: 'BBBBBB'
          },
          elemDefault
        );
        const expected = Object.assign({}, returnedFromService);
        service
          .query(expected)
          .pipe(
            take(1),
            map(resp => resp.body)
          )
          .subscribe(body => (expectedResult = body));
        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a Inspector', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
