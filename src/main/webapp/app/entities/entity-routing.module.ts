import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'cooperative',
        data: { pageTitle: 'coopCycleApp.cooperative.home.title' },
        loadChildren: () => import('./cooperative/cooperative.module').then(m => m.CooperativeModule),
      },
      {
        path: 'deliverer',
        data: { pageTitle: 'coopCycleApp.deliverer.home.title' },
        loadChildren: () => import('./deliverer/deliverer.module').then(m => m.DelivererModule),
      },
      {
        path: 'restaurant',
        data: { pageTitle: 'coopCycleApp.restaurant.home.title' },
        loadChildren: () => import('./restaurant/restaurant.module').then(m => m.RestaurantModule),
      },
      {
        path: 'customer',
        data: { pageTitle: 'coopCycleApp.customer.home.title' },
        loadChildren: () => import('./customer/customer.module').then(m => m.CustomerModule),
      },
      {
        path: 'product',
        data: { pageTitle: 'coopCycleApp.product.home.title' },
        loadChildren: () => import('./product/product.module').then(m => m.ProductModule),
      },
      {
        path: 'order',
        data: { pageTitle: 'coopCycleApp.order.home.title' },
        loadChildren: () => import('./order/order.module').then(m => m.OrderModule),
      },
      {
        path: 'order-content',
        data: { pageTitle: 'coopCycleApp.orderContent.home.title' },
        loadChildren: () => import('./order-content/order-content.module').then(m => m.OrderContentModule),
      },
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ]),
  ],
})
export class EntityRoutingModule {}
