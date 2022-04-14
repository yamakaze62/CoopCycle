import { IRestaurant } from 'app/entities/restaurant/restaurant.model';
import { IOrderContent } from 'app/entities/order-content/order-content.model';

export interface IProduct {
  id?: number;
  idProduct?: number;
  idRestaurant?: number;
  name?: string | null;
  price?: number | null;
  disponibility?: number | null;
  restaurant?: IRestaurant | null;
  ordercontents?: IOrderContent[] | null;
}

export class Product implements IProduct {
  constructor(
    public id?: number,
    public idProduct?: number,
    public idRestaurant?: number,
    public name?: string | null,
    public price?: number | null,
    public disponibility?: number | null,
    public restaurant?: IRestaurant | null,
    public ordercontents?: IOrderContent[] | null
  ) {}
}

export function getProductIdentifier(product: IProduct): number | undefined {
  return product.id;
}
