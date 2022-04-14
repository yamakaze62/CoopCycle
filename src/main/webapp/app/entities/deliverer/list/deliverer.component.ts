import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IDeliverer } from '../deliverer.model';
import { DelivererService } from '../service/deliverer.service';
import { DelivererDeleteDialogComponent } from '../delete/deliverer-delete-dialog.component';

@Component({
  selector: 'jhi-deliverer',
  templateUrl: './deliverer.component.html',
})
export class DelivererComponent implements OnInit {
  deliverers?: IDeliverer[];
  isLoading = false;

  constructor(protected delivererService: DelivererService, protected modalService: NgbModal) {}

  loadAll(): void {
    this.isLoading = true;

    this.delivererService.query().subscribe({
      next: (res: HttpResponse<IDeliverer[]>) => {
        this.isLoading = false;
        this.deliverers = res.body ?? [];
      },
      error: () => {
        this.isLoading = false;
      },
    });
  }

  ngOnInit(): void {
    this.loadAll();
  }

  trackId(_index: number, item: IDeliverer): number {
    return item.id!;
  }

  delete(deliverer: IDeliverer): void {
    const modalRef = this.modalService.open(DelivererDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.deliverer = deliverer;
    // unsubscribe not needed because closed completes on modal close
    modalRef.closed.subscribe(reason => {
      if (reason === 'deleted') {
        this.loadAll();
      }
    });
  }
}
