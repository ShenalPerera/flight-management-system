export class Flight{


  public flight_number: number | undefined;
  public arrival: string | undefined;
  public departure: string | undefined;
  public arrival_date: Date | undefined;
  public departure_date:Date | undefined

  constructor(flight_number:number, arrival:string, departure:string,arrival_date:Date, departure_date:Date){
    this.flight_number = flight_number;
    this.arrival = arrival;
    this.departure = departure;
    this.arrival_date = arrival_date;
    this.departure_date = departure_date;
  }


}
