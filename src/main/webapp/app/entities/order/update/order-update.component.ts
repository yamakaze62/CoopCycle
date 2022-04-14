import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';

import { IOrder, Order } from '../order.model';
import { OrderService } from '../service/order.service';
import { IDeliverer } from 'app/entities/deliverer/deliverer.model';
import { DelivererService } from 'app/entities/deliverer/service/deliverer.service';
import { ICustomer } from 'app/entities/customer/customer.model';
import { CustomerService } from 'app/entities/customer/service/customer.service';
import { IRestaurant } from 'app/entities/restaurant/restaurant.model';
import { RestaurantService } from 'app/entities/restaurant/service/restaurant.service';
import { State } from 'app/entities/enumerations/state.model';

@Component({
  selector: 'jhi-order-update',
  templateUrl: './order-update.component.html',
})
export class OrderUpdateComponent implements OnInit {
  isSaving = false;
  stateValues = Object.keys(State);

  deliverersSharedCollection: IDeliverer[] = [];
  customersSharedCollection: ICustomer[] = [];
  restaurantsSharedCollection: IRestaurant[] = [];

  editForm = this.fb.group({
    id: [],
    idOrder: [null, [Validators.required]],
    idRestaurant: [null, [Validators.required]],
    idCustomer: [null, [Validators.required]],
    state: [],
    totalprice: [null, [Validators.min(5), Validators.max(200)]],
    date: [],
    deliverer: [],
    customer: [],
    restaurant: [],
  });

  constructor(
    protected orderService: OrderService,
    protected delivererService: DelivererService,
    protected customerService: CustomerService,
    protected restaurantService: RestaurantService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ order }) => {
      if (order.id === undefined) {
        const today = dayjs().startOf('day');
        order.date = today;
      }

      this.updateForm(order);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const order = this.createFromForm();
    if (order.id !== undefined) {
      this.subscribeToSaveResponse(this.orderService.update(order));
    } else {
      this.subscribeToSaveResponse(this.orderService.create(order));
    }
  }

  trackDelivererById(_index: number, item: IDeliverer): number {
    return item.id!;
  }

  trackCustomerById(_index: number, item: ICustomer): number {
    return item.id!;
  }

  trackRestaurantById(_index: number, item: IRestaurant): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IOrder>>): void {
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

  protected updateForm(order: IOrder): void {
    this.editForm.patchValue({
      id: order.id,
      idOrder: order.idOrder,
      idRestaurant: order.idRestaurant,
      idCustomer: order.idCustomer,
      state: order.state,
      totalprice: order.totalprice,
      date: order.date ? order.date.format(DATE_TIME_FORMAT) : null,
      deliverer: order.deliverer,
      customer: order.customer,
      restaurant: order.restaurant,
    });

    this.deliverersSharedCollection = this.delivererService.addDelivererToCollectionIfMissing(
      this.deliverersSharedCollection,
      order.deliverer
    );
    this.customersSharedCollection = this.customerService.addCustomerToCollectionIfMissing(this.customersSharedCollection, order.customer);
    this.restaurantsSharedCollection = this.restaurantService.addRestaurantToCollectionIfMissing(
      this.restaurantsSharedCollection,
      order.restaurant
    );
  }

  protected loadRelationshipsOptions(): void {
    this.delivererService
      .query()
      .pipe(map((res: HttpResponse<IDeliverer[]>) => res.body ?? []))
      .pipe(
        map((deliverers: IDeliverer[]) =>
          this.delivererService.addDelivererToCollectionIfMissing(deliverers, this.editForm.get('deliverer')!.value)
        )
      )
      .subscribe((deliverers: IDeliverer[]) => (this.deliverersSharedCollection = deliverers));

    this.customerService
      .query()
      .pipe(map((res: HttpResponse<ICustomer[]>) => res.body ?? []))
      .pipe(
        map((customers: ICustomer[]) =>
          this.customerService.addCustomerToCollectionIfMissing(customers, this.editForm.get('customer')!.value)
        )
      )
      .subscribe((customers: ICustomer[]) => (this.customersSharedCollection = customers));

    this.restaurantService
      .query()
      .pipe(map((res: HttpResponse<IRestaurant[]>) => res.body ?? []))
      .pipe(
        map((restaurants: IRestaurant[]) =>
          this.restaurantService.addRestaurantToCollectionIfMissing(restaurants, this.editForm.get('restaurant')!.value)
        )
      )
      .subscribe((restaurants: IRestaurant[]) => (this.restaurantsSharedCollection = restaurants));
  }

  protected createFromForm(): IOrder {
    return {
      ...new Order(),
      id: this.editForm.get(['id'])!.value,
      idOrder: this.editForm.get(['idOrder'])!.value,
      idRestaurant: this.editForm.get(['idRestaurant'])!.value,
      idCustomer: this.editForm.get(['idCustomer'])!.value,
      state: this.editForm.get(['state'])!.value,
      totalprice: this.editForm.get(['totalprice'])!.value,
      date: this.editForm.get(['date'])!.value ? dayjs(this.editForm.get(['date'])!.value, DATE_TIME_FORMAT) : undefined,
      deliverer: this.editForm.get(['deliverer'])!.value,
      customer: this.editForm.get(['customer'])!.value,
      restaurant: this.editForm.get(['restaurant'])!.value,
    };
  }
}
