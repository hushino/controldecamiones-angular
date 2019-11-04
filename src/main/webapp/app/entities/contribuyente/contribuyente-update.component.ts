import { Component, OnInit } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { IContribuyente, Contribuyente } from 'app/shared/model/contribuyente.model';
import { ContribuyenteService } from './contribuyente.service';

@Component({
  selector: 'jhi-contribuyente-update',
  templateUrl: './contribuyente-update.component.html'
})
export class ContribuyenteUpdateComponent implements OnInit {
  isSaving: boolean;

  editForm = this.fb.group({
    id: [],
    nombre: [],
    apellido: [],
    cuit: [],
    email: [null, [Validators.pattern('')]]
  });

  constructor(protected contribuyenteService: ContribuyenteService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ contribuyente }) => {
      this.updateForm(contribuyente);
    });
  }

  updateForm(contribuyente: IContribuyente) {
    this.editForm.patchValue({
      id: contribuyente.id,
      nombre: contribuyente.nombre,
      apellido: contribuyente.apellido,
      cuit: contribuyente.cuit,
      email: contribuyente.email
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const contribuyente = this.createFromForm();
    if (contribuyente.id !== undefined) {
      this.subscribeToSaveResponse(this.contribuyenteService.update(contribuyente));
    } else {
      this.subscribeToSaveResponse(this.contribuyenteService.create(contribuyente));
    }
  }

  private createFromForm(): IContribuyente {
    return {
      ...new Contribuyente(),
      id: this.editForm.get(['id']).value,
      nombre: this.editForm.get(['nombre']).value,
      apellido: this.editForm.get(['apellido']).value,
      cuit: this.editForm.get(['cuit']).value,
      email: this.editForm.get(['email']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IContribuyente>>) {
    result.subscribe(() => this.onSaveSuccess(), () => this.onSaveError());
  }

  protected onSaveSuccess() {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError() {
    this.isSaving = false;
  }
}
