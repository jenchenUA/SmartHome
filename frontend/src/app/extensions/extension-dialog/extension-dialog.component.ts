import {Component, Inject, OnInit} from '@angular/core';
import {MAT_DIALOG_DATA, MatDialogRef} from "@angular/material/dialog";
import {Extension} from "../model/Extension";
import {ExtensionType} from "../model/ExtensionType";

@Component({
  selector: 'app-extension-dialog',
  templateUrl: './extension-dialog.component.html',
  styleUrls: ['./extension-dialog.component.scss']
})
export class ExtensionDialogComponent implements OnInit {

  ExtensionType = ExtensionType;
  allExtension = [
    {value: "32", text: "0100000", type: ExtensionType.GPIO},
    {value: "33", text: "0100001", type: ExtensionType.GPIO},
    {value: "34", text: "0100010", type: ExtensionType.GPIO},
    {value: "35", text: "0100011", type: ExtensionType.GPIO},
    {value: "36", text: "0100100", type: ExtensionType.GPIO},
    {value: "37", text: "0100101", type: ExtensionType.GPIO},
    {value: "38", text: "0100110", type: ExtensionType.GPIO},
    {value: "39", text: "0100111", type: ExtensionType.GPIO},
    {value: "72", text: "ADDR to GND - 1001000", type: ExtensionType.ADC},
    {value: "73", text: "ADDR to VDD - 1001001", type: ExtensionType.ADC},
    {value: "74", text: "ADDR to SDA - 1001010", type: ExtensionType.ADC},
    {value: "75", text: "ADDR to SCL - 1001011", type: ExtensionType.ADC},
  ];
  selectedExtensions = [];

  constructor(
    public dialogRef: MatDialogRef<ExtensionDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public data: Extension) { }

  ngOnInit() {
    this.filterExtensions();
  }

  closeDialog() {
    this.dialogRef.close();
  }

  createExtension() {
    this.dialogRef.close(this.data);
  }

  filterExtensions() {
    console.log(this.data.type);
    this.selectedExtensions = this.allExtension.filter(e => e.type == this.data.type);
  }
}
