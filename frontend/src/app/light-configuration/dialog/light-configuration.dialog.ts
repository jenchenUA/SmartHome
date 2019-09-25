import {Component, Inject, ViewChild} from "@angular/core";
import {MAT_DIALOG_DATA, MatDialogRef} from "@angular/material/dialog";
import {LightConfiguration} from "../model/light-configuration";
import {LightConfigurationService} from "../service/light-configuration.service";
import {MatSnackBar} from "@angular/material/snack-bar";

@Component({
  selector: 'app-light-dialog',
  templateUrl: './light-configuration.dialog.html',
  styleUrls: ['./light-configuration.dialog.scss']
})
export class LightConfigurationDialog {

  configurationNotSame = true;
  @ViewChild("form", {static: false}) form: any;

  constructor(
    public dialogRef: MatDialogRef<LightConfigurationDialog>,
    @Inject(MAT_DIALOG_DATA) public data: LightConfiguration) {}

  onNoClick(): void {
    this.dialogRef.close();
  }

  createConfiguration() {
    if (this.data.buttonPin === this.data.controlPin) {
      this.configurationNotSame = false;
    } else {
      this.data.uid = this.data.buttonPin + this.data.controlPin;
      this.configurationNotSame = true;
      this.dialogRef.close(this.data);
    }
  }
}
