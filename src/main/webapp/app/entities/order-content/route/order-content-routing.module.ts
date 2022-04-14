import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { OrderContentComponent } from '../list/order-content.component';
import { OrderContentDetailComponent } from '../detail/order-content-detail.component';
import { OrderContentUpdateComponent } from '../update/order-content-update.component';
import { OrderContentRoutingResolveService } from './order-content-routing-resolve.service';

const orderContentRoute: Routes = [
  {
    path: '',
    component: OrderContentComponent,
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: OrderContentDetailComponent,
    resolve: {
      orderContent: OrderContentRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: OrderContentUpdateComponent,
    resolve: {
      orderContent: OrderContentRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: OrderContentUpdateComponent,
    resolve: {
      orderContent: OrderContentRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(orderContentRoute)],
  exports: [RouterModule],
})
export class OrderContentRoutingModule {}
