import { IProduct } from 'app/entities/product/product.model';
import { IOrder } from 'app/entities/order/order.model';

export interface IOrderContent {
  id?: number;
  idProduct?: number;
  idOrder?: number;
  quantityAsked?: number | null;
  productAvailable?: boolean | null;
  products?: IProduct[];
  order?: IOrder | null;
}

export class OrderContent implements IOrderContent {
  constructor(
    public id?: number,
    public idProduct?: number,
    public idOrder?: number,
    public quantityAsked?: number | null,
    public productAvailable?: boolean | null,
    public products?: IProduct[],
    public order?: IOrder | null
  ) {
    this.productAvailable = this.productAvailable ?? false;
  }
}

export function getOrderContentIdentifier(orderContent: IOrderContent): number | undefined {
  return orderContent.id;
}
