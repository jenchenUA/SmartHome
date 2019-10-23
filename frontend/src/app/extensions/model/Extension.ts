import {ExtensionType} from "./ExtensionType";

export class Extension {
  constructor(public address: number,
              public type: ExtensionType,
              public id: number,
              public online: boolean) {}
}
