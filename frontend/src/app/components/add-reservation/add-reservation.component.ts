import { Component, EventEmitter, Input, Output } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Reservation } from '../../models/reservation';
import { ReservationService } from '../../services/reservation.service';

@Component({
  selector: 'app-add-reservation',
  standalone: true,
  imports: [CommonModule, FormsModule],
  template: `
    <div class="modal">
      <div class="modal-content">
        <h2>{{ editMode ? 'Update' : 'Add New' }} Reservation</h2>
        <form (ngSubmit)="onSubmit()" #reservationForm="ngForm">
          <div class="form-group">
            <label for="academicYear">Academic Year</label>
            <input 
              type="date" 
              id="academicYear"
              name="academicYear"
              [(ngModel)]="newReservation.anneeUniversitaire"
              required
              class="form-control"
            >
          </div>

          <div class="form-group">
            <label for="roomNumber">Room Number</label>
            <input 
              type="number" 
              id="roomNumber"
              name="roomNumber"
              [(ngModel)]="newReservation.chambre.numeroChambre"
              required
              class="form-control"
            >
          </div>

          <div class="form-group">
            <label for="roomType">Room Type</label>
            <select 
              id="roomType"
              name="roomType"
              [(ngModel)]="newReservation.chambre.typeC"
              required
              class="form-control"
            >
              <option value="SIMPLE">Simple</option>
              <option value="DOUBLE">Double</option>
              <option value="TRIPLE">Triple</option>
            </select>
          </div>

          <div class="form-group">
            <label>
              <input 
                type="checkbox" 
                name="isValid"
                [(ngModel)]="newReservation.estValid"
              >
              Is Valid
            </label>
          </div>

          <div class="form-actions">
            <button type="button" class="btn-secondary" (click)="onCancel()">Cancel</button>
            <button type="submit" class="btn-primary" [disabled]="!reservationForm.form.valid">
              {{ editMode ? 'Update' : 'Save' }} Reservation
            </button>
          </div>
        </form>
      </div>
    </div>
  `,
  styles: [`
    .modal {
      position: fixed;
      top: 0;
      left: 0;
      width: 100%;
      height: 100%;
      background: rgba(0, 0, 0, 0.5);
      display: flex;
      justify-content: center;
      align-items: center;
      z-index: 1000;
    }

    .modal-content {
      background: white;
      padding: 2rem;
      border-radius: 8px;
      width: 90%;
      max-width: 500px;
    }

    .form-group {
      margin-bottom: 1.5rem;
    }

    .form-group label {
      display: block;
      margin-bottom: 0.5rem;
      font-weight: 500;
    }

    .form-control {
      width: 100%;
      padding: 0.75rem;
      border: 1px solid #e2e8f0;
      border-radius: 6px;
      font-size: 1rem;
      transition: all 0.3s ease;
    }

    .form-control:focus {
      outline: none;
      border-color: #4299e1;
      box-shadow: 0 0 0 3px rgba(66, 153, 225, 0.2);
    }

    .form-actions {
      display: flex;
      justify-content: flex-end;
      gap: 1rem;
      margin-top: 2rem;
    }
  `]
})
export class AddReservationComponent {
  @Input() editMode = false;
  @Input() set reservation(value: Reservation | null) {
    if (value) {
      this.newReservation = { ...value };
    }
  }
  @Output() close = new EventEmitter<void>();
  @Output() reservationAdded = new EventEmitter<void>();

  newReservation: Reservation = {
    idReservation: '',
    anneeUniversitaire: new Date(),
    estValid: false,
    chambre: {
      idChambre: 1,
      numeroChambre: 0,
      typeC: 'SIMPLE'
    }
  };

  constructor(private reservationService: ReservationService) {}

  onSubmit() {
    if (this.editMode) {
      this.reservationService.updateReservation(this.newReservation)
        .subscribe(() => {
          this.reservationAdded.emit();
          this.close.emit();
        });
    } else {
      this.reservationService.createReservation(this.newReservation)
        .subscribe(() => {
          this.reservationAdded.emit();
          this.close.emit();
        });
    }
  }

  onCancel() {
    this.close.emit();
  }
}