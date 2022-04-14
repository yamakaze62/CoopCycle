import { IOrder } from 'app/entities/order/order.model';
import { IProduct } from 'app/entities/product/product.model';

export interface IRestaurant {
  id?: number;
  idRestaurant?: number;
  name?: string | null;
  address?: string;
  telephone?: string;
  orders?: IOrder[] | null;
  products?: IProduct[] | null;
}

export class Restaurant implements IRestaurant {
  constructor(
    public id?: number,
    public idRestaurant?: number,
    public name?: string | null,
    public address?: string,
    public telephone?: string,
    public orders?: IOrder[] | null,
    public products?: IProduct[] | null
  ) {}
}

export function getRestaurantIdentifier(restaurant: IRestaurant): number | undefined {
  return restaurant.id;
}
