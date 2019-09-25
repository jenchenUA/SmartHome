import { Injectable } from '@angular/core';
import {environment} from "../../../environments/environment";
import {HttpClient} from "@angular/common/http";

@Injectable({
  providedIn: 'root'
})
export class LightStateService {

  baseUrl = environment.baseUrl;

  constructor(private http: HttpClient) {}

  changeState(uid: string) {
    return this.http.put(this.baseUrl + "/light/" + uid + "/state", {}, {})
  }
}
