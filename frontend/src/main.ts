import { Component } from '@angular/core';
import { bootstrapApplication } from '@angular/platform-browser';
import { provideHttpClient } from '@angular/common/http';
import { ReservationListComponent } from './app/components/reservation-list/reservation-list.component';
//import { RoomAvailabilityComponent } from './app/components/room-availability/room-availability.component';

@Component({
  selector: 'app-root',
  template: `
    <div class="app-wrapper">
      <header class="main-header">
        <h1>🏠 student housing Reservation System</h1>
        <p class="subtitle">Manage your bookings with ease</p>
      </header>
      
      <main class="main-content">
        <app-reservation-list></app-reservation-list>
      </main>
      
      <footer class="main-footer">
        <p>© 2024 Hotel Reservation System</p>
      </footer>
    </div>
  `,
  standalone: true,
  imports: [ReservationListComponent],
  styles: [`
    .app-wrapper {
      min-height: 100vh;
      display: flex;
      flex-direction: column;
    }

    .main-header {
      background: white;
      padding: 2rem;
      text-align: center;
      box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
    }

    .subtitle {
      color: #666;
      margin-top: 0.5rem;
      font-size: 1.1rem;
    }

    .main-content {
      flex: 1;
      max-width: 1200px;
      margin: 0 auto;
      padding: 2rem;
      width: 100%;
    }

    .main-footer {
      background: #2d3748;
      color: white;
      text-align: center;
      padding: 1rem;
      margin-top: auto;
    }

    h1 {
      font-size: 2.5rem;
      color: #2d3748;
      margin: 0;
    }
  `]
})
export class App {
  name = 'Hotel Reservation System';
}

bootstrapApplication(App, {
  providers: [
    provideHttpClient()
  ]
});