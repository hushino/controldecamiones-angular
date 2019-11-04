import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import './vendor';
import { ControldecamionesSharedModule } from 'app/shared/shared.module';
import { ControldecamionesCoreModule } from 'app/core/core.module';
import { ControldecamionesAppRoutingModule } from './app-routing.module';
import { ControldecamionesHomeModule } from './home/home.module';
import { ControldecamionesEntityModule } from './entities/entity.module';
// jhipster-needle-angular-add-module-import JHipster will add new module here
import { JhiMainComponent } from './layouts/main/main.component';
import { NavbarComponent } from './layouts/navbar/navbar.component';
import { FooterComponent } from './layouts/footer/footer.component';
import { PageRibbonComponent } from './layouts/profiles/page-ribbon.component';
import { ErrorComponent } from './layouts/error/error.component';

@NgModule({
  imports: [
    BrowserModule,
    ControldecamionesSharedModule,
    ControldecamionesCoreModule,
    ControldecamionesHomeModule,
    // jhipster-needle-angular-add-module JHipster will add new module here
    ControldecamionesEntityModule,
    ControldecamionesAppRoutingModule
  ],
  declarations: [JhiMainComponent, NavbarComponent, ErrorComponent, PageRibbonComponent, FooterComponent],
  bootstrap: [JhiMainComponent]
})
export class ControldecamionesAppModule {}
