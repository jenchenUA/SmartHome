import {Component, Inject, OnInit} from '@angular/core';
import {MAT_DIALOG_DATA, MatDialogRef} from "@angular/material/dialog";
import {LightConfiguration} from "../../light-configuration/model/light-configuration";
import {UpdateModel} from "../model/update.model";
import {SystemUpdateService} from "../service/system-update.service";
import {UpdateStatus} from "../model/update.status";

@Component({
  selector: 'app-update-dialog',
  templateUrl: './update-dialog.component.html',
  styleUrls: ['./update-dialog.component.scss']
})
export class UpdateDialogComponent implements OnInit {

  UpdateStatus = UpdateStatus;

  constructor(
    private dialogRef: MatDialogRef<UpdateDialogComponent>,
    private systemUpdateService: SystemUpdateService,
    @Inject(MAT_DIALOG_DATA) public data: UpdateModel
  ) {
    console.log(data);
  }

  ngOnInit() {
  }

  close(): void {
    this.dialogRef.close();
  }

  checkUpdate(): void {
    this.systemUpdateService.checkUpdate().subscribe();
  }

  applyUpdate(): void {
    this.systemUpdateService.performUpdate().subscribe();
  }

  applyAndReboot(): void {
    this.systemUpdateService.performUpdateAndReboot().subscribe();
  }
}
