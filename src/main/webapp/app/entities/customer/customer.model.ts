import { IOrder } from 'app/entities/order/order.model';

export interface ICustomer {
  id?: number;
  idCustomer?: number;
  name?: string | null;
  surname?: string | null;
  address?: string;
  telephone?: string;
  orders?: IOrder[] | null;
}

export class Customer implements ICustomer {
  constructor(
    public id?: number,
    public idCustomer?: number,
    public name?: string | null,
    public surname?: string | null,
    public address?: string,
    public telephone?: string,
    public orders?: IOrder[] | null
  ) {}
}

export function getCustomerIdentifier(customer: ICustomer): number | undefined {
  return customer.id;
}
