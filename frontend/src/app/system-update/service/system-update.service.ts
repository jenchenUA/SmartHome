import { Injectable } from '@angular/core';
import {environment} from "../../../environments/environment";
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {VersionInfo} from "../model/version.info";

@Injectable({
  providedIn: 'root'
})
export class SystemUpdateService {

  baseUrl = environment.baseUrl;

  constructor(private http: HttpClient) { }

  checkUpdate(): Observable<boolean> {
    return this.http.get<boolean>(this.baseUrl + "/system/update", {});
  }

  performUpdate(): Observable<boolean> {
    return this.http.get<boolean>(this.baseUrl + "/system/update/perform", {});
  }

  performUpdateAndReboot(): Observable<boolean> {
    return this.http.get<boolean>(this.baseUrl + "/system/update/reboot", {});
  }

  currentVersion(): Observable<VersionInfo> {
    return this.http.get<VersionInfo>(this.baseUrl + "/system/update/version", {});
  }
}
