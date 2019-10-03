import {Component, Inject, OnInit} from '@angular/core';
import {MAT_DIALOG_DATA, MatDialogRef} from "@angular/material/dialog";
import {LightConfiguration} from "../../light-configuration/model/light-configuration";
import {UpdateModel} from "../model/update.model";
import {SystemUpdateService} from "../service/system-update.service";
import {UpdateStatus} from "../model/update.status";
import {MatSnackBar} from "@angular/material/snack-bar";

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
    private snackBar: MatSnackBar,
    @Inject(MAT_DIALOG_DATA) public data: UpdateModel
  ) {}

  ngOnInit() {
  }

  isNewVersionInformationVisible(): boolean {
    return this.data.updateStatus == UpdateStatus.UPDATE_AVAILABLE ||
      this.data.updateStatus == UpdateStatus.UPDATE_DOWNLOADING || this.data.updateStatus == UpdateStatus.UPDATE_FINALIZING ||
      this.data.updateStatus == UpdateStatus.UPDATE_NEEDS_REBOOT
  }

  close(): void {
    this.dialogRef.close();
  }

  checkUpdate(): void {
    this.data.updateStatus = UpdateStatus.UPDATE_CHECKING;
    this.systemUpdateService.checkUpdate().subscribe(result => {
      if (!result) {
        this.showSnackBar("Перевірка оновлення не почалося, спробуйте ще раз");
      } else {
        this.showSnackBar("Успішно");
      }
    },
    error => {
      console.log(error);
      this.showSnackBar("Виникла помилка")
    });
  }

  applyUpdate(): void {
    this.systemUpdateService.performUpdate().subscribe(result => {
      if (!result) {
        this.showSnackBar("Оновлення не почалося, спробуйте ще раз");
      } else {
        this.showSnackBar("Успішно");
      }
    },
    error => {
      console.log(error);
      this.showSnackBar("Виникла помилка")
    });
  }

  applyAndReboot(): void {
    this.systemUpdateService.performUpdateAndReboot().subscribe(result => {
      if (!result) {
        this.showSnackBar("Перезавантаження не почалося, спробуйте ще раз");
      } else {
        this.showSnackBar("Успішно");
      }
    },
    error => {
      console.log(error);
      this.showSnackBar("Виникла помилка")
    });
  }

  showSnackBar(message: string): void {
    this.snackBar.open(message, "", {duration: 2000})
  }
}
