import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';

import {AppComponent} from './app.component';
import {LightConfigurationComponent} from './light-configuration/light-configuration.component';
import {BrowserAnimationsModule} from "@angular/platform-browser/animations";
import {MatInputModule} from "@angular/material/input";
import { MatFormFieldModule } from '@angular/material/form-field';
import {MatListModule} from "@angular/material/list";
import {MatSidenavModule} from "@angular/material/sidenav";
import {MatToolbarModule} from "@angular/material/toolbar";
import {MatButtonModule} from "@angular/material/button";
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {MatCardModule} from "@angular/material/card";
import {MatGridListModule} from "@angular/material/grid-list";
import {HttpClientModule} from "@angular/common/http";
import {MatSnackBarModule} from "@angular/material/snack-bar";
import {LightConfigurationDialog} from "./light-configuration/dialog/light-configuration.dialog";
import {MatDialogModule} from "@angular/material/dialog";
import {MatIconModule} from "@angular/material/icon";
import { FabButtonComponent } from './fab-button/fab-button.component';
import {FlexLayoutModule} from "@angular/flex-layout";
import {MatMenuModule} from "@angular/material/menu";
import {RemoveConfirmationDialogComponent} from './remove-confirmation.dialog/remove-confirmation.dialog.component';
import { MainNavComponent } from './main-nav/main-nav.component';
import { LayoutModule } from '@angular/cdk/layout';
import {MatBadgeModule} from "@angular/material/badge";
import { UpdateDialogComponent } from './system-update/update-dialog/update-dialog.component';
import {MatProgressSpinnerModule} from "@angular/material/progress-spinner";
import {MatProgressBarModule} from "@angular/material/progress-bar";
import { ExtensionsComponent } from './extensions/extensions.component';
import { ExtensionDialogComponent } from './extensions/extension-dialog/extension-dialog.component';
import {MatSelectModule} from "@angular/material/select";

@NgModule({
  declarations: [
    AppComponent,
    LightConfigurationComponent,
    LightConfigurationDialog,
    FabButtonComponent,
    RemoveConfirmationDialogComponent,
    MainNavComponent,
    UpdateDialogComponent,
    ExtensionsComponent,
    ExtensionDialogComponent
  ],
  imports: [
    BrowserModule,
    FormsModule,
    MatInputModule,
    MatFormFieldModule,
    BrowserAnimationsModule,
    MatListModule,
    MatSidenavModule,
    MatToolbarModule,
    MatButtonModule,
    MatCardModule,
    MatGridListModule,
    MatSnackBarModule,
    HttpClientModule,
    MatDialogModule,
    MatIconModule,
    MatToolbarModule,
    MatMenuModule,
    LayoutModule,
    MatBadgeModule,
    MatProgressSpinnerModule,
    MatProgressBarModule,
    MatSelectModule,
    ReactiveFormsModule
  ],
  providers: [],
  bootstrap: [AppComponent],
  entryComponents: [
    LightConfigurationDialog,
    RemoveConfirmationDialogComponent,
    UpdateDialogComponent,
    ExtensionDialogComponent
  ]
})
export class AppModule {
}
