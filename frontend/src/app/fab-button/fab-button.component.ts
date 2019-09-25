import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {speedDialFabAnimations} from "./fab-button.animations";

@Component({
  selector: 'app-fab-button',
  templateUrl: './fab-button.component.html',
  styleUrls: ['./fab-button.component.scss'],
  animations: speedDialFabAnimations
})
export class FabButtonComponent {

  @Input() icon: string;
  @Output() onClick: EventEmitter<any> = new EventEmitter();
  fabTogglerState = 'inactive';

  constructor() { }

  onToggleFab() {
    this.onClick.emit();
  }
}
