export class WarmFloorConfiguration {

  public uid: string;
  public label: string;
  public resistanceOfThermoresistor: number;
  public resistanceOfSupportResistor: number;
  public bOfThermoresistor: number;
  public switcherPin: string;
  public controlPin: string;
  public channel: number;
  public countOfMeasures: number;
  public gpioAddress: number;
  public voltage: number;
  public adcAddress: number;
  public threshold: number;

  constructor() {
  }
}
