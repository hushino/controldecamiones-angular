import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiDataUtils } from 'ng-jhipster';

import { IInspector } from 'app/shared/model/inspector.model';

@Component({
  selector: 'jhi-inspector-detail',
  templateUrl: './inspector-detail.component.html'
})
export class InspectorDetailComponent implements OnInit {
  inspector: IInspector;

  constructor(protected dataUtils: JhiDataUtils, protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ inspector }) => {
      this.inspector = inspector;
    });
  }

  byteSize(field) {
    return this.dataUtils.byteSize(field);
  }

  openFile(contentType, field) {
    return this.dataUtils.openFile(contentType, field);
  }
  previousState() {
    window.history.back();
  }
}
