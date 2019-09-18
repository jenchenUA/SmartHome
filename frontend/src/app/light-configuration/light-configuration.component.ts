import {Component, OnInit} from '@angular/core';
import {LightConfiguration} from "./model/light-configuration";
import {LightConfigurationService} from "./service/light-configuration.service";

@Component({
  selector: 'app-light-configuration',
  templateUrl: './light-configuration.component.html',
  styleUrls: ['./light-configuration.component.css']
})
export class LightConfigurationComponent implements OnInit {

  configuration = new LightConfiguration("","", "", "", 0);
  done = false;
  configurationNotSame = true;

  constructor(private configurationService: LightConfigurationService) { }

  ngOnInit() {
  }

  create() {
    if (this.configuration.buttonPin === this.configuration.controlPin) {
      this.configurationNotSame = false;
    } else {
      this.configurationNotSame = true;
      this.configuration.uid = this.configuration.buttonPin + this.configuration.controlPin;
      this.configurationService.createLightConfiguration(this.configuration)
        .subscribe(
          () => {
            this.done = true;
          },
          error => console.log(error)
        )
    }
  }
}
