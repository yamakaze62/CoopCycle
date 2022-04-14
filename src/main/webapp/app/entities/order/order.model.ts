import dayjs from 'dayjs/esm';
import { IOrderContent } from 'app/entities/order-content/order-content.model';
import { IDeliverer } from 'app/entities/deliverer/deliverer.model';
import { ICustomer } from 'app/entities/customer/customer.model';
import { IRestaurant } from 'app/entities/restaurant/restaurant.model';
import { State } from 'app/entities/enumerations/state.model';

export interface IOrder {
  id?: number;
  idOrder?: number;
  idRestaurant?: number;
  idCustomer?: number;
  state?: State | null;
  totalprice?: number | null;
  date?: dayjs.Dayjs | null;
  orderContents?: IOrderContent[] | null;
  deliverer?: IDeliverer | null;
  customer?: ICustomer | null;
  restaurant?: IRestaurant | null;
}

export class Order implements IOrder {
  constructor(
    public id?: number,
    public idOrder?: number,
    public idRestaurant?: number,
    public idCustomer?: number,
    public state?: State | null,
    public totalprice?: number | null,
    public date?: dayjs.Dayjs | null,
    public orderContents?: IOrderContent[] | null,
    public deliverer?: IDeliverer | null,
    public customer?: ICustomer | null,
    public restaurant?: IRestaurant | null
  ) {}
}

export function getOrderIdentifier(order: IOrder): number | undefined {
  return order.id;
}
