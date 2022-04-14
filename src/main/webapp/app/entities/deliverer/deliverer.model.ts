import { IOrder } from 'app/entities/order/order.model';
import { ICooperative } from 'app/entities/cooperative/cooperative.model';

export interface IDeliverer {
  id?: number;
  idDelivrer?: number;
  idCooperative?: number | null;
  name?: string | null;
  surname?: string | null;
  telephone?: string;
  latitude?: number;
  longitude?: number;
  orders?: IOrder[] | null;
  cooperative?: ICooperative | null;
}

export class Deliverer implements IDeliverer {
  constructor(
    public id?: number,
    public idDelivrer?: number,
    public idCooperative?: number | null,
    public name?: string | null,
    public surname?: string | null,
    public telephone?: string,
    public latitude?: number,
    public longitude?: number,
    public orders?: IOrder[] | null,
    public cooperative?: ICooperative | null
  ) {}
}

export function getDelivererIdentifier(deliverer: IDeliverer): number | undefined {
  return deliverer.id;
}
