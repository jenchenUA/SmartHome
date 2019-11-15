import {Component, Inject, OnInit, ViewChild} from '@angular/core';
import {MAT_DIALOG_DATA, MatDialogRef} from "@angular/material/dialog";
import {LightConfiguration} from "../../light-configuration/model/light-configuration";
import {WarmFloorConfiguration} from "../model/warm-floor-configuration";
import {ExtensionService} from "../../extensions/service/extension.service";
import {Extension} from "../../extensions/model/Extension";

@Component({
  selector: 'app-warm-floor-configuration-dialog',
  templateUrl: './warm-floor-configuration-dialog.component.html',
  styleUrls: ['./warm-floor-configuration-dialog.component.scss']
})
export class WarmFloorConfigurationDialogComponent implements OnInit {

  adcs: Extension[];
  gpioProviders: Extension[];

  constructor(
    public dialogRef: MatDialogRef<WarmFloorConfiguration>,
    private extensionService: ExtensionService,
    @Inject(MAT_DIALOG_DATA) public data: WarmFloorConfiguration) {}

  onNoClick(): void {
    this.dialogRef.close();
  }

  ngOnInit(): void {
    this.extensionService.getOnlineAdcs().subscribe((adcs: Extension[]) => this.adcs = adcs);
    this.extensionService.getOnlineGpioProviders()
      .subscribe((providers: Extension[]) => this.gpioProviders = providers);
  }

  createConfiguration() {
    this.data.uid = this.data.switcherPin + this.data.controlPin;
    this.dialogRef.close(this.data);
  }
}
