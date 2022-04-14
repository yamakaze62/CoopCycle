import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IOrderContent } from '../order-content.model';

@Component({
  selector: 'jhi-order-content-detail',
  templateUrl: './order-content-detail.component.html',
})
export class OrderContentDetailComponent implements OnInit {
  orderContent: IOrderContent | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ orderContent }) => {
      this.orderContent = orderContent;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
