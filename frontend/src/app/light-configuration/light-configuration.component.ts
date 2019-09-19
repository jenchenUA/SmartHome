import {Component, OnInit, ViewChild} from '@angular/core';
import {LightConfiguration} from "./model/light-configuration";
import {LightConfigurationService} from "./service/light-configuration.service";
import {MatSnackBar} from "@angular/material/snack-bar";

@Component({
  selector: 'app-light-configuration',
  templateUrl: './light-configuration.component.html',
  styleUrls: ['./light-configuration.component.css']
})
export class LightConfigurationComponent implements OnInit {

  configuration = new LightConfiguration("", "", "", "", 0);
  configurationNotSame = true;
  @ViewChild("form", {static: false}) form: any;

  constructor(private configurationService: LightConfigurationService, private snackBar: MatSnackBar) {
  }

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
            this.showSnackBar("Created!");
            this.resetForm();
          },
          error => console.log(error)
        )
    }
  }

  resetForm() {
    this.form.reset();
    Object.keys(this.form.form.controls).forEach(key => {
      this.form.form.controls[key].clearValidators();
      this.form.form.controls[key].updateValueAndValidity();
    });
  }

  showSnackBar(message: string) {
    this.snackBar.open(message, "", {duration: 2000})
  }
}
