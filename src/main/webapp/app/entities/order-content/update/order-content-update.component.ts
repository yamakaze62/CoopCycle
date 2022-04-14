import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { IOrderContent, OrderContent } from '../order-content.model';
import { OrderContentService } from '../service/order-content.service';
import { IProduct } from 'app/entities/product/product.model';
import { ProductService } from 'app/entities/product/service/product.service';
import { IOrder } from 'app/entities/order/order.model';
import { OrderService } from 'app/entities/order/service/order.service';

@Component({
  selector: 'jhi-order-content-update',
  templateUrl: './order-content-update.component.html',
})
export class OrderContentUpdateComponent implements OnInit {
  isSaving = false;

  productsSharedCollection: IProduct[] = [];
  ordersSharedCollection: IOrder[] = [];

  editForm = this.fb.group({
    id: [],
    idProduct: [null, [Validators.required]],
    idOrder: [null, [Validators.required]],
    quantityAsked: [],
    productAvailable: [],
    products: [null, Validators.required],
    order: [],
  });

  constructor(
    protected orderContentService: OrderContentService,
    protected productService: ProductService,
    protected orderService: OrderService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ orderContent }) => {
      this.updateForm(orderContent);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const orderContent = this.createFromForm();
    if (orderContent.id !== undefined) {
      this.subscribeToSaveResponse(this.orderContentService.update(orderContent));
    } else {
      this.subscribeToSaveResponse(this.orderContentService.create(orderContent));
    }
  }

  trackProductById(_index: number, item: IProduct): number {
    return item.id!;
  }

  trackOrderById(_index: number, item: IOrder): number {
    return item.id!;
  }

  getSelectedProduct(option: IProduct, selectedVals?: IProduct[]): IProduct {
    if (selectedVals) {
      for (const selectedVal of selectedVals) {
        if (option.id === selectedVal.id) {
          return selectedVal;
        }
      }
    }
    return option;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IOrderContent>>): void {
    result.pipe(finalize(() => this.onSaveFinalize())).subscribe({
      next: () => this.onSaveSuccess(),
      error: () => this.onSaveError(),
    });
  }

  protected onSaveSuccess(): void {
    this.previousState();
  }

  protected onSaveError(): void {
    // Api for inheritance.
  }

  protected onSaveFinalize(): void {
    this.isSaving = false;
  }

  protected updateForm(orderContent: IOrderContent): void {
    this.editForm.patchValue({
      id: orderContent.id,
      idProduct: orderContent.idProduct,
      idOrder: orderContent.idOrder,
      quantityAsked: orderContent.quantityAsked,
      productAvailable: orderContent.productAvailable,
      products: orderContent.products,
      order: orderContent.order,
    });

    this.productsSharedCollection = this.productService.addProductToCollectionIfMissing(
      this.productsSharedCollection,
      ...(orderContent.products ?? [])
    );
    this.ordersSharedCollection = this.orderService.addOrderToCollectionIfMissing(this.ordersSharedCollection, orderContent.order);
  }

  protected loadRelationshipsOptions(): void {
    this.productService
      .query()
      .pipe(map((res: HttpResponse<IProduct[]>) => res.body ?? []))
      .pipe(
        map((products: IProduct[]) =>
          this.productService.addProductToCollectionIfMissing(products, ...(this.editForm.get('products')!.value ?? []))
        )
      )
      .subscribe((products: IProduct[]) => (this.productsSharedCollection = products));

    this.orderService
      .query()
      .pipe(map((res: HttpResponse<IOrder[]>) => res.body ?? []))
      .pipe(map((orders: IOrder[]) => this.orderService.addOrderToCollectionIfMissing(orders, this.editForm.get('order')!.value)))
      .subscribe((orders: IOrder[]) => (this.ordersSharedCollection = orders));
  }

  protected createFromForm(): IOrderContent {
    return {
      ...new OrderContent(),
      id: this.editForm.get(['id'])!.value,
      idProduct: this.editForm.get(['idProduct'])!.value,
      idOrder: this.editForm.get(['idOrder'])!.value,
      quantityAsked: this.editForm.get(['quantityAsked'])!.value,
      productAvailable: this.editForm.get(['productAvailable'])!.value,
      products: this.editForm.get(['products'])!.value,
      order: this.editForm.get(['order'])!.value,
    };
  }
}
