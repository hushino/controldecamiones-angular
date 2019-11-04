export interface IInspector {
  id?: number;
  fotopatenteContentType?: string;
  fotopatente?: any;
  fotocamionContentType?: string;
  fotocamion?: any;
  vehiculomodelo?: string;
  infoadicional?: string;
  celular?: number;
  cuit?: string;
  patente?: string;
}

export class Inspector implements IInspector {
  constructor(
    public id?: number,
    public fotopatenteContentType?: string,
    public fotopatente?: any,
    public fotocamionContentType?: string,
    public fotocamion?: any,
    public vehiculomodelo?: string,
    public infoadicional?: string,
    public celular?: number,
    public cuit?: string,
    public patente?: string
  ) {}
}
