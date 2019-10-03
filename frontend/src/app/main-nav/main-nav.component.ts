import {Component, OnInit} from '@angular/core';
import { BreakpointObserver, Breakpoints } from '@angular/cdk/layout';
import { Observable } from 'rxjs';
import { map, shareReplay } from 'rxjs/operators';
import {environment} from "../../environments/environment";
import {WebsocketService} from "../websocket-service/websocket.service";
import {WS} from "../websocket-service/websocket.events";
import {UpdateModel} from "../system-update/model/update.model";
import {MatDialog, MatDialogRef} from "@angular/material/dialog";
import {UpdateDialogComponent} from "../system-update/update-dialog/update-dialog.component";
import {SystemUpdateService} from "../system-update/service/system-update.service";
import {VersionInfo} from "../system-update/model/version.info";
import {UpdateStatus} from "../system-update/model/update.status";
import {NewVersion} from "../system-update/model/new.version";

@Component({
  selector: 'app-main-nav',
  templateUrl: './main-nav.component.html',
  styleUrls: ['./main-nav.component.css']
})
export class MainNavComponent implements OnInit {

  updateBadgeVisible: boolean = false;
  ws: WebSocket;
  baseUrl = environment.baseUrl;
  updateData: UpdateModel = new UpdateModel();
  private updateDialogRef: MatDialogRef<UpdateDialogComponent>;

  isHandset$: Observable<boolean> = this.breakpointObserver.observe(Breakpoints.Handset)
    .pipe(
      map(result => result.matches),
      shareReplay()
    );

  constructor(private breakpointObserver: BreakpointObserver,
              private wsService: WebsocketService,
              private dialog: MatDialog,
              private systemUpdateService: SystemUpdateService) {
    // this.updateData = this.prepareTestData();
    this.updateData.updateStatus = UpdateStatus.UPDATE_IDLE;
    wsService.on<UpdateModel>(WS.ON.SYSTEM_UPDATE).subscribe((update) => {
      console.log(update);
      if (this.isUpdateAvailable(update)) {
        this.updateBadgeVisible = true;
        this.updateData = update;
        if (this.updateDialogRef && this.updateDialogRef.componentInstance.data) {
          this.updateDialogRef.componentInstance.data = update;
        }
      } else if (!this.updateBadgeVisible && update.updateStatus == UpdateStatus.UPDATE_IDLE) {
        this.updateData = update;
      }
    });
  }

  isUpdateAvailable(update): boolean {
    return update.updateStatus == UpdateStatus.UPDATE_AVAILABLE
      || update.updateStatus == UpdateStatus.UPDATE_DOWNLOADING
      || update.updateStatus == UpdateStatus.UPDATE_NEEDS_REBOOT;
  }

  prepareTestData(): UpdateModel {
    let updateModel = new UpdateModel();
    updateModel.updateStatus = UpdateStatus.UPDATE_IDLE;
    updateModel.currentVersion = new VersionInfo();
    updateModel.currentVersion.androidThingsVersion = "124";
    updateModel.currentVersion.buildId = "124";
    updateModel.newVersion = new NewVersion();
    updateModel.newVersion.downloadProgress = 0.5;
    updateModel.newVersion.versionInfo = updateModel.currentVersion;
    return updateModel;
  }

  ngOnInit(): void {
    this.systemUpdateService.currentVersion().subscribe(data => this.updateData.currentVersion = data,
      error => console.log(error));
    if (!localStorage.getItem("lastUpdate") || this.lastCheckMoreThanFiveHours()) {
      this.systemUpdateService.checkUpdate().subscribe();
      localStorage.setItem("lastUpdate", JSON.stringify(new Date()));
    }
  }

  private lastCheckMoreThanFiveHours(): boolean {
    let lastCheck: Date = new Date(JSON.parse(localStorage.getItem("lastUpdate")));
    let threshold: Date = new Date(lastCheck.getTime() + (1000 * 60 * 60 * 5));
    let now = new Date();
    return threshold < now;
  }

  openUpdateDialog(): void {
    this.updateDialogRef = this.dialog.open(UpdateDialogComponent, {data: this.updateData, width: "500px"});
  }
}
