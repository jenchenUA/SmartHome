import { Injectable } from '@angular/core';
import {environment} from "../../../environments/environment";
import {HttpClient} from "@angular/common/http";

@Injectable({
  providedIn: 'root'
})
export class SystemUpdateService {

  baseUrl = environment.baseUrl;

  constructor(private http: HttpClient) { }

  checkUpdate() {
    return this.http.get(this.baseUrl + "/system/update", {});
  }

  performUpdate() {
    return this.http.get(this.baseUrl + "/system/update/perform", {});
  }

  performUpdateAndReboot() {
    return this.http.get(this.baseUrl + "/system/update/reboot", {});
  }
}
