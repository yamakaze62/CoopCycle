import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { OrderContentComponent } from './list/order-content.component';
import { OrderContentDetailComponent } from './detail/order-content-detail.component';
import { OrderContentUpdateComponent } from './update/order-content-update.component';
import { OrderContentDeleteDialogComponent } from './delete/order-content-delete-dialog.component';
import { OrderContentRoutingModule } from './route/order-content-routing.module';

@NgModule({
  imports: [SharedModule, OrderContentRoutingModule],
  declarations: [OrderContentComponent, OrderContentDetailComponent, OrderContentUpdateComponent, OrderContentDeleteDialogComponent],
  entryComponents: [OrderContentDeleteDialogComponent],
})
export class OrderContentModule {}
