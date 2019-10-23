import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {environment} from "../../../environments/environment";
import {Extension} from "../model/Extension";

@Injectable({
  providedIn: 'root'
})
export class ExtensionService {

  baseUrl = environment.baseUrl;

  constructor(private http: HttpClient) { }

  create(extension: Extension) {
    const options = {};
    let body = JSON.stringify(extension);
    return this.http.post(this.baseUrl + "/extension", body, options);
  }

  getAllExtensions() {
    const options = {};
    return this.http.get(this.baseUrl + "/extension", options)
  }

  deleteExtension(uid: number) {
    const options = {};
    return this.http.delete(this.baseUrl + "/extension/" + uid, options)
  }
}
