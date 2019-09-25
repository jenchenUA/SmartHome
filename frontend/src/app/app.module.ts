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
import {FormsModule} from "@angular/forms";
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

@NgModule({
  declarations: [
    AppComponent,
    LightConfigurationComponent,
    LightConfigurationDialog,
    FabButtonComponent,
    RemoveConfirmationDialogComponent
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
        FlexLayoutModule,
        MatMenuModule
    ],
  providers: [],
  bootstrap: [AppComponent],
  entryComponents: [
    LightConfigurationDialog,
    RemoveConfirmationDialogComponent
  ]
})
export class AppModule {
}
