export class Flight{

  public id: string | undefined;
  public flight_number: string | undefined;
  public arrival: string | undefined;
  public departure: string | undefined;
  public arrival_date: string | undefined;
  public departure_date:string | undefined;

  public arrival_time: string | undefined;

  public departure_time:string | undefined;

  constructor(id:string,flight_number:string, arrival:string, departure:string,arrival_date:string,arrival_time:string, departure_date:string,
               departure_time:string){
    this.flight_number = flight_number;
    this.arrival = arrival;
    this.departure = departure;
    this.arrival_date = arrival_date;
    this.departure_date = departure_date;
    this.arrival_time = arrival_time;
    this.departure_time =departure_time;
    this.id = id
  }


}
