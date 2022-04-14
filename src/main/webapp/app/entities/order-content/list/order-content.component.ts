import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IOrderContent } from '../order-content.model';
import { OrderContentService } from '../service/order-content.service';
import { OrderContentDeleteDialogComponent } from '../delete/order-content-delete-dialog.component';

@Component({
  selector: 'jhi-order-content',
  templateUrl: './order-content.component.html',
})
export class OrderContentComponent implements OnInit {
  orderContents?: IOrderContent[];
  isLoading = false;

  constructor(protected orderContentService: OrderContentService, protected modalService: NgbModal) {}

  loadAll(): void {
    this.isLoading = true;

    this.orderContentService.query().subscribe({
      next: (res: HttpResponse<IOrderContent[]>) => {
        this.isLoading = false;
        this.orderContents = res.body ?? [];
      },
      error: () => {
        this.isLoading = false;
      },
    });
  }

  ngOnInit(): void {
    this.loadAll();
  }

  trackId(_index: number, item: IOrderContent): number {
    return item.id!;
  }

  delete(orderContent: IOrderContent): void {
    const modalRef = this.modalService.open(OrderContentDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.orderContent = orderContent;
    // unsubscribe not needed because closed completes on modal close
    modalRef.closed.subscribe(reason => {
      if (reason === 'deleted') {
        this.loadAll();
      }
    });
  }
}
