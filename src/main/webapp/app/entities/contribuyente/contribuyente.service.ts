import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IContribuyente } from 'app/shared/model/contribuyente.model';

type EntityResponseType = HttpResponse<IContribuyente>;
type EntityArrayResponseType = HttpResponse<IContribuyente[]>;

@Injectable({ providedIn: 'root' })
export class ContribuyenteService {
  public resourceUrl = SERVER_API_URL + 'api/contribuyentes';

  constructor(protected http: HttpClient) {}

  create(contribuyente: IContribuyente): Observable<EntityResponseType> {
    return this.http.post<IContribuyente>(this.resourceUrl, contribuyente, { observe: 'response' });
  }

  update(contribuyente: IContribuyente): Observable<EntityResponseType> {
    return this.http.put<IContribuyente>(this.resourceUrl, contribuyente, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IContribuyente>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IContribuyente[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
