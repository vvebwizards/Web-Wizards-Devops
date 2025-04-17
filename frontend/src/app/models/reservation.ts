export interface Reservation {
  idReservation: string;
  anneeUniversitaire: Date;
  estValid: boolean;
  chambre: Room;
}

export interface Room {
  idChambre: number;
  numeroChambre: number;
  typeC: string;
}
