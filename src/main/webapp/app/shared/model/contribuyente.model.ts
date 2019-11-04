export interface IContribuyente {
  id?: number;
  nombre?: string;
  apellido?: string;
  cuit?: string;
  email?: string;
}

export class Contribuyente implements IContribuyente {
  constructor(public id?: number, public nombre?: string, public apellido?: string, public cuit?: string, public email?: string) {}
}
