export interface Fare {
  fareId: number;
  departure: string;
  arrival: string;
  fare: number | null;
  version: number;
}
