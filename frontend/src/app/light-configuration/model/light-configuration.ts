export class LightConfiguration {
  constructor(
    public uid: string,
    public buttonPin: string,
    public controlPin: string,
    public label: string,
    public gpioAddress: number
  ) { }
}
