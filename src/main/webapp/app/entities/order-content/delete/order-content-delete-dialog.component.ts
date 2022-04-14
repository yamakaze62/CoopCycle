import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IOrderContent } from '../order-content.model';
import { OrderContentService } from '../service/order-content.service';

@Component({
  templateUrl: './order-content-delete-dialog.component.html',
})
export class OrderContentDeleteDialogComponent {
  orderContent?: IOrderContent;

  constructor(protected orderContentService: OrderContentService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.orderContentService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
