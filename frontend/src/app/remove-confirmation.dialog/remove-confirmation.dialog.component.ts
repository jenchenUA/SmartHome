import { Component, OnInit } from '@angular/core';
import {MatDialogRef} from "@angular/material/dialog";

@Component({
  selector: 'app-remove-confirmation.dialog',
  templateUrl: './remove-confirmation.dialog.component.html',
  styleUrls: ['./remove-confirmation.dialog.component.scss']
})
export class RemoveConfirmationDialogComponent implements OnInit {

  constructor(public dialogRef: MatDialogRef<RemoveConfirmationDialogComponent>) { }

  ngOnInit() {
  }

  onNoClick(): void {
    this.dialogRef.close(false);
  }

  confirm(): void {
    this.dialogRef.close(true);
  }
}
