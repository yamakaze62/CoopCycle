import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IOrderContent, OrderContent } from '../order-content.model';
import { OrderContentService } from '../service/order-content.service';

@Injectable({ providedIn: 'root' })
export class OrderContentRoutingResolveService implements Resolve<IOrderContent> {
  constructor(protected service: OrderContentService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IOrderContent> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((orderContent: HttpResponse<OrderContent>) => {
          if (orderContent.body) {
            return of(orderContent.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new OrderContent());
  }
}
