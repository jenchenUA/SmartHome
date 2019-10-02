import {VersionInfo} from "./version.info";
import {NewVersion} from "./new.version";

export class UpdateModel {
  updateStatus: string;
  currentVersion: VersionInfo;
  newVersion: NewVersion;
}
