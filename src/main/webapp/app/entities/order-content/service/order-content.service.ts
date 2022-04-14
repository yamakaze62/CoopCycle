import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IOrderContent, getOrderContentIdentifier } from '../order-content.model';

export type EntityResponseType = HttpResponse<IOrderContent>;
export type EntityArrayResponseType = HttpResponse<IOrderContent[]>;

@Injectable({ providedIn: 'root' })
export class OrderContentService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/order-contents');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(orderContent: IOrderContent): Observable<EntityResponseType> {
    return this.http.post<IOrderContent>(this.resourceUrl, orderContent, { observe: 'response' });
  }

  update(orderContent: IOrderContent): Observable<EntityResponseType> {
    return this.http.put<IOrderContent>(`${this.resourceUrl}/${getOrderContentIdentifier(orderContent) as number}`, orderContent, {
      observe: 'response',
    });
  }

  partialUpdate(orderContent: IOrderContent): Observable<EntityResponseType> {
    return this.http.patch<IOrderContent>(`${this.resourceUrl}/${getOrderContentIdentifier(orderContent) as number}`, orderContent, {
      observe: 'response',
    });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IOrderContent>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IOrderContent[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addOrderContentToCollectionIfMissing(
    orderContentCollection: IOrderContent[],
    ...orderContentsToCheck: (IOrderContent | null | undefined)[]
  ): IOrderContent[] {
    const orderContents: IOrderContent[] = orderContentsToCheck.filter(isPresent);
    if (orderContents.length > 0) {
      const orderContentCollectionIdentifiers = orderContentCollection.map(
        orderContentItem => getOrderContentIdentifier(orderContentItem)!
      );
      const orderContentsToAdd = orderContents.filter(orderContentItem => {
        const orderContentIdentifier = getOrderContentIdentifier(orderContentItem);
        if (orderContentIdentifier == null || orderContentCollectionIdentifiers.includes(orderContentIdentifier)) {
          return false;
        }
        orderContentCollectionIdentifiers.push(orderContentIdentifier);
        return true;
      });
      return [...orderContentsToAdd, ...orderContentCollection];
    }
    return orderContentCollection;
  }
}
