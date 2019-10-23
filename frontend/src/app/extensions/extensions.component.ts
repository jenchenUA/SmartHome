import {Component, OnInit} from '@angular/core';
import {MatDialog} from "@angular/material/dialog";
import {ExtensionService} from "./service/extension.service";
import {Extension} from "./model/Extension";
import {ExtensionType} from "./model/ExtensionType";
import {ExtensionDialogComponent} from "./extension-dialog/extension-dialog.component";
import {MatSnackBar, MatSnackBarRef} from "@angular/material/snack-bar";
import {WebsocketService} from "../websocket-service/websocket.service";
import {WS} from "../websocket-service/websocket.events";
import {RemoveConfirmationDialogComponent} from "../remove-confirmation.dialog/remove-confirmation.dialog.component";

@Component({
  selector: 'app-extensions',
  templateUrl: './extensions.component.html',
  styleUrls: ['./extensions.component.scss']
})
export class ExtensionsComponent implements OnInit {

  fabIcon: string = "add";
  data: Extension[];
  ExtensionType = ExtensionType;

  constructor(private dialog: MatDialog,
              private snackBar: MatSnackBar,
              private extensionService: ExtensionService,
              private webSocketService: WebsocketService) {
  }

  ngOnInit() {
    this.extensionService.getAllExtensions().subscribe((data: Extension[]) => this.data = data);
    this.webSocketService.on<Extension>(WS.ON.EXTENSIONS).subscribe(result => {
      if (this.data.filter(extension => extension.id == result.id).length == 0) {
        this.data.push(result);
      }
    });
  }

  openDialog(): void {
    let data = new Extension(undefined, ExtensionType.GPIO, undefined, false);
    let dialogRef = this.dialog.open(ExtensionDialogComponent, {width: '500px', data: data});
    dialogRef.afterClosed().subscribe(result => {
      this.saveConfiguration(result);
    })
  }

  saveConfiguration(data) {
    if (data) {
      this.extensionService.create(data).subscribe(() => {},
        error => {
          console.log(error);
          this.showErrorSnackBar(data);
        });
    }
  }

  showErrorSnackBar(data) {
    let snackBarRef = this.showSnackBar("Нажаль виникла помилка", "Спробувати ще раз", 15000);
    snackBarRef.onAction().subscribe(() => {
      this.dialog.open(ExtensionDialogComponent, {width: '500px', data: data})
        .afterClosed()
        .subscribe(result => this.saveConfiguration(result));
    });
  }

  showSnackBar(message: string, action: string, duration: number): MatSnackBarRef<any> {
    return this.snackBar.open(message, action, {duration: duration})
  }

  onDeleteClick(id: number, i: number) {
    this.dialog.open(RemoveConfirmationDialogComponent).afterClosed().subscribe(result => {
      if (result) {
        this.extensionService.deleteExtension(id).subscribe(() => {
          this.data.splice(i, 1);
        });
      }
    });
  }
}
