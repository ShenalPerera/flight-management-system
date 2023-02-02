export class AvailableFlightModel{
  public flightNumber: number;

  public departure: string;

  public arrival: string;

  public departureDate: string;

  public departureTime: string;

  public arrivalDate:string;

  public arrivalTime:string;

  public fare: number;


  constructor(flightNumber: number, departure: string, arrival: string, departureDate: string, departureTime: string, arrivalDate: string, arrivalTime: string, fare: number) {
    this.flightNumber = flightNumber;
    this.departure = departure;
    this.arrival = arrival;
    this.departureDate = departureDate;
    this.departureTime = departureTime;
    this.arrivalDate = arrivalDate;
    this.arrivalTime = arrivalTime;
    this.fare = fare;
  }

}
