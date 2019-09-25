import {Injectable} from "@angular/core";
import {HttpClient} from "@angular/common/http";
import {LightConfiguration} from "../model/light-configuration";
import {environment} from "../../../environments/environment";

@Injectable({
  providedIn: 'root'
})
export class LightConfigurationService {

  baseUrl = environment.baseUrl;

  constructor(private http: HttpClient) {}

  createLightConfiguration(configuration: LightConfiguration) {
    let body = JSON.stringify(configuration);
    const options = {headers: {'Content-Type': 'application/json'}};
    return this.http.post(this.baseUrl + "/light/configuration", body, options);
  }

  getLightConfigurations() {
    const options = {};
    return this.http.get(this.baseUrl + "/light/configuration", options);
  }

  delete(uid: string) {
    return this.http.delete(this.baseUrl + "/light/configuration/" + uid, {})
  }
}
