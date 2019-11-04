import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IInspector } from 'app/shared/model/inspector.model';

type EntityResponseType = HttpResponse<IInspector>;
type EntityArrayResponseType = HttpResponse<IInspector[]>;

@Injectable({ providedIn: 'root' })
export class InspectorService {
  public resourceUrl = SERVER_API_URL + 'api/inspectors';

  constructor(protected http: HttpClient) {}

  create(inspector: IInspector): Observable<EntityResponseType> {
    return this.http.post<IInspector>(this.resourceUrl, inspector, { observe: 'response' });
  }

  update(inspector: IInspector): Observable<EntityResponseType> {
    return this.http.put<IInspector>(this.resourceUrl, inspector, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IInspector>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IInspector[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
