import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IContribuyente } from 'app/shared/model/contribuyente.model';

@Component({
  selector: 'jhi-contribuyente-detail',
  templateUrl: './contribuyente-detail.component.html'
})
export class ContribuyenteDetailComponent implements OnInit {
  contribuyente: IContribuyente;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ contribuyente }) => {
      this.contribuyente = contribuyente;
    });
  }

  previousState() {
    window.history.back();
  }
}
