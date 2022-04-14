import { IDeliverer } from 'app/entities/deliverer/deliverer.model';

export interface ICooperative {
  id?: number;
  idCooperative?: number;
  name?: string | null;
  deliverers?: IDeliverer[] | null;
}

export class Cooperative implements ICooperative {
  constructor(public id?: number, public idCooperative?: number, public name?: string | null, public deliverers?: IDeliverer[] | null) {}
}

export function getCooperativeIdentifier(cooperative: ICooperative): number | undefined {
  return cooperative.id;
}
