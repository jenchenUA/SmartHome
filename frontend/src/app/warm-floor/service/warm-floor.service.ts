import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {environment} from "../../../environments/environment";
import {WarmFloorConfiguration} from "../model/warm-floor-configuration";
import {Observable} from "rxjs";
import {WarmFloorView} from "../model/warm-floor-view";

@Injectable({
  providedIn: 'root'
})
export class WarmFloorService {

  private baseUrl: string = environment.baseUrl;

  constructor(private http: HttpClient) { }

  public getWarmFloorViews() {
    return this.http.get(this.baseUrl + "/warm-floor/views", {});
  }

  public create(configuration: WarmFloorConfiguration) {
    let body = JSON.stringify(configuration);
    return this.http.post(this.baseUrl + "/warm-floor/configuration", body, {});
  }

  public deleteConfiguration(uid: string) {
    return this.http.delete(this.baseUrl + "/warm-floor/configuration/" + uid, {});
  }

  changeState(uid: string) {
    return this.http.put(this.baseUrl + "/warm-floor/" + uid + "/state", {}, {});
  }

  setThreshold(uid: string, value: number) {
    return this.http.put(this.baseUrl + "/warm-floor/" + uid + "/temperature/" + value, {}, {})
  }
}
