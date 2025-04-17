// import { Component, OnInit } from '@angular/core';
// import { CommonModule } from '@angular/common';
// import { ReservationService } from '../../services/reservation.service';
// import { FormsModule } from '@angular/forms';

// @Component({
//   selector: 'app-room-availability',
//   standalone: true,
//   imports: [CommonModule, FormsModule],
//   template: `
//     <div class="container availability-container">
//       <h2>🏠 Room Availability</h2>
      
//       <div class="room-selector">
//         <label for="roomSelect">Select Room:</label>
//         <select 
//           id="roomSelect" 
//           [(ngModel)]="roomId"
//           (change)="loadAvailablePlaces()"
//           class="room-select"
//         >
//           <option [value]="1">Room 101</option>
//           <option [value]="2">Room 102</option>
//           <option [value]="3">Room 103</option>
//         </select>
//       </div>

//       <div class="availability-card" [class.available]="availablePlaces > 0">
//         <div class="availability-icon">
//           {{ availablePlaces > 0 ? '✅' : '❌' }}
//         </div>
//         <div class="availability-info">
//           <h3>Room {{roomId}}</h3>
//           <p class="places">Available Places: {{availablePlaces}}</p>
//           <p class="status">
//             {{ availablePlaces > 0 ? 'Room is available' : 'Room is fully booked' }}
//           </p>
//         </div>
//       </div>

//       <div class="refresh-section">
//         <button class="btn-secondary" (click)="loadAvailablePlaces()">
//           🔄 Refresh Availability
//         </button>
//       </div>
//     </div>
//   `,
//   styles: [`
//     .availability-container {
//       padding: 2rem;
//       margin-bottom: 2rem;
//     }

//     .room-selector {
//       margin: 1.5rem 0;
//     }

//     .room-select {
//       width: 100%;
//       padding: 0.75rem;
//       border: 1px solid #e2e8f0;
//       border-radius: 6px;
//       font-size: 1rem;
//       margin-top: 0.5rem;
//       background: white;
//       cursor: pointer;
//     }

//     .availability-card {
//       display: flex;
//       align-items: center;
//       padding: 2rem;
//       background: #fff5f5;
//       border-radius: 12px;
//       margin: 1.5rem 0;
//       transition: all 0.3s ease;
//     }

//     .availability-card.available {
//       background: #f0fff4;
//     }

//     .availability-icon {
//       font-size: 2.5rem;
//       margin-right: 1.5rem;
//     }

//     .availability-info h3 {
//       margin: 0;
//       font-size: 1.5rem;
//     }

//     .places {
//       font-size: 1.1rem;
//       margin: 0.5rem 0;
//       color: #4a5568;
//     }

//     .status {
//       font-weight: 500;
//       color: #2d3748;
//     }

//     .refresh-section {
//       margin-top: 1.5rem;
//       text-align: center;
//     }

//     .btn-secondary {
//       display: inline-flex;
//       align-items: center;
//       gap: 0.5rem;
//     }
//   `]
// })
// export class RoomAvailabilityComponent implements OnInit {
//   roomId: number = 1;
//   availablePlaces: number = 0;

//   constructor(private reservationService: ReservationService) {}

//   ngOnInit() {
//     this.loadAvailablePlaces();
//   }

//   loadAvailablePlaces() {
//     this.reservationService.getAvailablePlaces(this.roomId)
//       .subscribe(places => {
//         this.availablePlaces = places;
//       });
//   }
// }