import {Injectable} from "@angular/core";
import {HttpClient} from "@angular/common/http";
import {LightConfiguration} from "../model/light-configuration";
import {Observable} from "rxjs";
import {LightConfigurationComponent} from "../light-configuration.component";
import {environment} from "../../../environments/environment";

@Injectable({
  providedIn: 'root'
})
export class LightConfigurationService {

  baseUrl = environment.baseUrl;

  constructor(private http: HttpClient) {

  }

  createLightConfiguration(configuration: LightConfiguration) {
    let body = JSON.stringify(configuration);
    const options = {headers: {'Content-Type': 'application/json'}};
    return this.http.post(this.baseUrl + "/light/config", body, options);
  }
}
