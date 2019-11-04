import { Component, OnInit, ElementRef } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { JhiAlertService, JhiDataUtils } from 'ng-jhipster';
import { IInspector, Inspector } from 'app/shared/model/inspector.model';
import { InspectorService } from './inspector.service';

@Component({
  selector: 'jhi-inspector-update',
  templateUrl: './inspector-update.component.html'
})
export class InspectorUpdateComponent implements OnInit {
  isSaving: boolean;

  editForm = this.fb.group({
    id: [],
    fotopatente: [],
    fotopatenteContentType: [],
    fotocamion: [],
    fotocamionContentType: [],
    vehiculomodelo: [],
    infoadicional: [],
    celular: [],
    cuit: [],
    patente: []
  });

  constructor(
    protected dataUtils: JhiDataUtils,
    protected jhiAlertService: JhiAlertService,
    protected inspectorService: InspectorService,
    protected elementRef: ElementRef,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ inspector }) => {
      this.updateForm(inspector);
    });
  }

  updateForm(inspector: IInspector) {
    this.editForm.patchValue({
      id: inspector.id,
      fotopatente: inspector.fotopatente,
      fotopatenteContentType: inspector.fotopatenteContentType,
      fotocamion: inspector.fotocamion,
      fotocamionContentType: inspector.fotocamionContentType,
      vehiculomodelo: inspector.vehiculomodelo,
      infoadicional: inspector.infoadicional,
      celular: inspector.celular,
      cuit: inspector.cuit,
      patente: inspector.patente
    });
  }

  byteSize(field) {
    return this.dataUtils.byteSize(field);
  }

  openFile(contentType, field) {
    return this.dataUtils.openFile(contentType, field);
  }

  setFileData(event, field: string, isImage) {
    return new Promise((resolve, reject) => {
      if (event && event.target && event.target.files && event.target.files[0]) {
        const file: File = event.target.files[0];
        if (isImage && !file.type.startsWith('image/')) {
          reject(`File was expected to be an image but was found to be ${file.type}`);
        } else {
          const filedContentType: string = field + 'ContentType';
          this.dataUtils.toBase64(file, base64Data => {
            this.editForm.patchValue({
              [field]: base64Data,
              [filedContentType]: file.type
            });
          });
        }
      } else {
        reject(`Base64 data was not set as file could not be extracted from passed parameter: ${event}`);
      }
    }).then(
      // eslint-disable-next-line no-console
      () => console.log('blob added'), // success
      this.onError
    );
  }

  clearInputImage(field: string, fieldContentType: string, idInput: string) {
    this.editForm.patchValue({
      [field]: null,
      [fieldContentType]: null
    });
    if (this.elementRef && idInput && this.elementRef.nativeElement.querySelector('#' + idInput)) {
      this.elementRef.nativeElement.querySelector('#' + idInput).value = null;
    }
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const inspector = this.createFromForm();
    if (inspector.id !== undefined) {
      this.subscribeToSaveResponse(this.inspectorService.update(inspector));
    } else {
      this.subscribeToSaveResponse(this.inspectorService.create(inspector));
    }
  }

  private createFromForm(): IInspector {
    return {
      ...new Inspector(),
      id: this.editForm.get(['id']).value,
      fotopatenteContentType: this.editForm.get(['fotopatenteContentType']).value,
      fotopatente: this.editForm.get(['fotopatente']).value,
      fotocamionContentType: this.editForm.get(['fotocamionContentType']).value,
      fotocamion: this.editForm.get(['fotocamion']).value,
      vehiculomodelo: this.editForm.get(['vehiculomodelo']).value,
      infoadicional: this.editForm.get(['infoadicional']).value,
      celular: this.editForm.get(['celular']).value,
      cuit: this.editForm.get(['cuit']).value,
      patente: this.editForm.get(['patente']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IInspector>>) {
    result.subscribe(() => this.onSaveSuccess(), () => this.onSaveError());
  }

  protected onSaveSuccess() {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError() {
    this.isSaving = false;
  }
  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }
}
