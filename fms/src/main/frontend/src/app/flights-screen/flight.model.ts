export class Flight{

  public id: string | undefined;
  public flightNumber: string | undefined;
  public arrival: string | undefined;
  public departure: string | undefined;
  public arrivalDate: string | undefined;
  public departureDate:string | undefined;

  public arrivalTime: string | undefined;

  public departureTime:string | undefined;

  constructor(id:string,flight_number:string, arrival:string, departure:string,arrival_date:string,arrival_time:string, departure_date:string,
               departure_time:string){
    this.flightNumber = flight_number;
    this.arrival = arrival;
    this.departure = departure;
    this.arrivalDate = arrival_date;
    this.departureDate = departure_date;
    this.arrivalTime = arrival_time;
    this.departureTime =departure_time;
    this.id = id
  }


}
