import {Component, OnInit, ViewChild} from '@angular/core';
import {LightConfiguration} from "./model/light-configuration";
import {LightConfigurationService} from "./service/light-configuration.service";
import {MatSnackBar, MatSnackBarRef} from "@angular/material/snack-bar";
import {LightConfigurationDialog} from "./dialog/light-configuration.dialog";
import {MatDialog} from "@angular/material/dialog";
import {LightStateService} from "./service/light-state.service";
import {RemoveConfirmationDialogComponent} from "../remove-confirmation.dialog/remove-confirmation.dialog.component";
import {Light} from "./model/light";
import {WebsocketService} from "../websocket-service/websocket.service";
import {WS} from "../websocket-service/websocket.events";

@Component({
  selector: 'app-light-configuration',
  templateUrl: './light-configuration.component.html',
  styleUrls: ['./light-configuration.component.scss']
})
export class LightConfigurationComponent implements OnInit {

  data: Light[];
  fabIcon: string = "add";

  constructor(private dialog: MatDialog,
              private configurationService: LightConfigurationService,
              private stateService: LightStateService,
              private snackBar: MatSnackBar,
              private webSocket: WebsocketService) {}

  ngOnInit() {
    this.configurationService.getLights().subscribe((data: Light[]) => this.data = data);
    this.webSocket.on<Light>(WS.ON.LIGHT_STATE).subscribe((newState) => {
      this.data.forEach(currentState => {
        if (currentState.uid == newState.uid) {
          currentState.state = newState.state;
        }
      })
    });
  }

  openDialog(): void {
    let data = new LightConfiguration("", "", "", "", 0);
    const dialogRef = this.dialog.open(LightConfigurationDialog, {width: '500px', data: data});
    dialogRef.afterClosed().subscribe(result => this.saveConfiguration(result));
  }

  saveConfiguration(result: LightConfiguration) {
    if (result) {
      this.configurationService.createLightConfiguration(result)
        .subscribe(
          () => {
            this.showSnackBar("Конфігурацію успішно створено", "", 2000);
            let view = new Light();
            view.label = result.label;
            view.uid = result.uid;
            view.state = false;
            this.data.push(view);
          },
          error => {
            console.log(error);
            this.showErrorSnackBar(result);
          }
        )
    }
  }

  showErrorSnackBar(result) {
    let snackBarRef = this.showSnackBar("Нажаль виникла помилка", "Спробувати ще раз", 15000);
    snackBarRef.onAction().subscribe(() => {
      this.dialog.open(LightConfigurationDialog, {width: '500px', data: result})
        .afterClosed()
        .subscribe(result => this.saveConfiguration(result));
    });
  }

  showSnackBar(message: string, action: string, duration: number): MatSnackBarRef<any> {
    return this.snackBar.open(message, action, {duration: duration})
  }

  changeState(uid: string) {
    this.stateService.changeState(uid).subscribe(
      () => this.showSnackBar("Успішно", "", 2000),
      error => {
        console.log(error);
        this.showSnackBar("Виникла помилка", "", 2000)
      }
    );
  }

  onDeleteClick(uid: string, index: number): void {
    const matDialogRef = this.dialog.open(RemoveConfirmationDialogComponent);
    matDialogRef.afterClosed()
      .subscribe(result => {
        if (result) {
          this.delete(uid, index);
        }
      });
  }

  delete(uid: string, index: number) {
    this.configurationService.delete(uid).subscribe(
      () => {
        this.data.splice(index, 1);
        this.showSnackBar("Успішно", "", 2000)
      },
      error => {
        console.log(error);
        this.showSnackBar("Виникла помилка", "", 2000)
      });
  }
}
