import {Component, OnInit} from '@angular/core';
import {MatDialog} from "@angular/material/dialog";
import {RemoveConfirmationDialogComponent} from "../remove-confirmation.dialog/remove-confirmation.dialog.component";
import {WarmFloorService} from "./service/warm-floor.service";
import {WarmFloorView} from "./model/warm-floor-view";
import {WebsocketService} from "../websocket-service/websocket.service";
import {WS} from "../websocket-service/websocket.events";
import {WarmFloorConfigurationDialogComponent} from "./warm-floor-configuration-dialog/warm-floor-configuration-dialog.component";
import {WarmFloorConfiguration} from "./model/warm-floor-configuration";
import {MatSnackBar, MatSnackBarRef} from "@angular/material/snack-bar";
import {MatSliderChange} from "@angular/material/slider";

@Component({
  selector: 'app-warm-floor',
  templateUrl: './warm-floor.component.html',
  styleUrls: ['./warm-floor.component.scss']
})
export class WarmFloorComponent implements OnInit {

  fabIcon: string = "add";
  data: WarmFloorView[] = [];

  constructor(private warmFloorService: WarmFloorService,
              private dialog: MatDialog,
              private snackBar: MatSnackBar,
              private webSocketService: WebsocketService) {
  }

  ngOnInit() {
    this.warmFloorService.getWarmFloorViews().subscribe((data: WarmFloorView[]) => {
        this.data = data;
      },
      error => {
        console.log(error);
      });
    this.webSocketService.on<WarmFloorView[]>(WS.ON.WARM_FLOOR).subscribe(updates => {
      for (let i = 0; i < updates.length; i++) {
        let update = updates[i];
        let warmFloorViews = this.data.filter(view => view.uid == update.uid);
        if (warmFloorViews.length > 0) {
          warmFloorViews.forEach(view => {
            view.warmingEnabled = update.warmingEnabled;
            view.enabled = update.enabled;
            view.currentTemperature = update.currentTemperature;
          });
        } else {
          this.data.push(update);
        }
      }
    });
  }

  formatTemperature(temperature: number) {
    return Math.round(temperature * 10) / 10;
  }

  onDeleteClick(uid: string, i: number) {
    this.dialog.open(RemoveConfirmationDialogComponent).afterClosed().subscribe(result => {
      if (result) {
        this.warmFloorService.deleteConfiguration(uid).subscribe(() => {
          this.data.splice(i, 1);
        })
      }
    });
  }

  changeState(uid: string) {
    this.warmFloorService.changeState(uid).subscribe(() => {}, error => console.log(error));
  }

  setThreshold(uid: string, event: MatSliderChange) {
    this.warmFloorService.setThreshold(uid, event.value).subscribe(() => {}, error => console.log(error));
  }

  openDialog() {
    let configuration = new WarmFloorConfiguration();
    configuration.countOfMeasures = 1;
    configuration.gpioAddress = 0;
    configuration.threshold = 15;
    let matDialogRef = this.dialog.open(WarmFloorConfigurationDialogComponent, {width: "500px", data: configuration});
    matDialogRef.afterClosed().subscribe(result => {
      this.saveConfiguration(result);
    });
  }

  saveConfiguration(result) {
    if (result) {
      this.warmFloorService.create(result).subscribe(() => {
        this.showSnackBar("Конфігурацію успішно створено", "", 2000);
      }, error => {
        console.log(error);
        this.showErrorSnackBar(result);
      });
    }
  }

  showErrorSnackBar(result) {
    let snackBarRef = this.showSnackBar("Нажаль виникла помилка", "Спробувати ще раз", 15000);
    snackBarRef.onAction().subscribe(() => {
      this.dialog.open(WarmFloorConfigurationDialogComponent, {width: '500px', data: result})
        .afterClosed()
        .subscribe(result => this.saveConfiguration(result));
    });
  }

  showSnackBar(message: string, action: string, duration: number): MatSnackBarRef<any> {
    return this.snackBar.open(message, action, {duration: duration})
  }

  formatLabel(value: number) {
    return value;
  }
}
