import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ReservationService } from '../../services/reservation.service';
import { Reservation } from '../../models/reservation';
import { FormsModule } from '@angular/forms';
import { AddReservationComponent } from '../add-reservation/add-reservation.component';

@Component({
  selector: 'app-reservation-list',
  standalone: true,
  imports: [CommonModule, FormsModule, AddReservationComponent],
  template: `
    <div class="container reservation-container">
      <div class="header-actions">
        <h2>📋 Reservations</h2>
        <button class="btn-primary" (click)="showCreateForm()">
          <span>+ New Reservation</span>
        </button>
      </div>

      <div class="search-bar">
        <input 
          type="text" 
          [(ngModel)]="searchTerm" 
          (input)="filterReservations()"
          placeholder="Search reservations..."
          class="search-input"
        >
      </div>

      <div class="table-container">
        <table class="table">
          <thead>
            <tr>
              <th>ID</th>
              <th>Academic Year</th>
              <th>Room Number</th>
              <th>Room Type</th>
              <th>Valid?</th>
              <th>Actions</th>
            </tr>
          </thead>
          <tbody>
            <tr *ngFor="let reservation of filteredReservations"
                [class.selected]="selectedReservation?.idReservation === reservation.idReservation"
                (click)="selectReservation(reservation)">
              <td>{{ reservation.idReservation }}</td>
              <td>{{ reservation.anneeUniversitaire | date:'yyyy' }}</td>
              <td>{{ reservation.chambre.numeroChambre }}</td>
              <td>{{ reservation.chambre.typeC }}</td>
              <td>{{ reservation.estValid ? '✅' : '❌' }}</td>
              <td class="actions">
                <button class="btn-secondary" (click)="editReservation(reservation)">✏️ Edit</button>
                <button class="btn-danger" (click)="confirmDelete(reservation)">🗑️ Delete</button>
              </td>
            </tr>
          </tbody>
        </table>
      </div>

      <!-- Delete Confirmation Modal -->
      <div class="modal" *ngIf="showDeleteModal">
        <div class="modal-content">
          <h3>Confirm Delete</h3>
          <p>Are you sure you want to delete this reservation?</p>
          <div class="modal-actions">
            <button class="btn-secondary" (click)="showDeleteModal = false">Cancel</button>
            <button class="btn-danger" (click)="deleteReservation()">Delete</button>
          </div>
        </div>
      </div>

      <!-- Add/Edit Reservation Modal -->
      <app-add-reservation
        *ngIf="showAddModal"
        [editMode]="isEditMode"
        [reservation]="selectedReservation"
        (close)="hideCreateForm()"
        (reservationAdded)="onReservationAdded()"
      ></app-add-reservation>
    </div>
  `,
  styles: [`
    .reservation-container {
      padding: 2rem;
    }

    .header-actions {
      display: flex;
      justify-content: space-between;
      align-items: center;
      margin-bottom: 2rem;
    }

    .search-bar {
      margin-bottom: 1.5rem;
    }

    .search-input {
      width: 100%;
      padding: 0.75rem;
      border: 1px solid #e2e8f0;
      border-radius: 6px;
      font-size: 1rem;
      transition: all 0.3s ease;
    }

    .search-input:focus {
      outline: none;
      border-color: #4299e1;
      box-shadow: 0 0 0 3px rgba(66, 153, 225, 0.2);
    }

    .table-container {
      overflow-x: auto;
    }

    .table {
      width: 100%;
      border-collapse: separate;
      border-spacing: 0;
      margin-bottom: 1rem;
    }

    .table th {
      background: #f7fafc;
      padding: 1rem;
      text-align: left;
      font-weight: 600;
      border-bottom: 2px solid #e2e8f0;
    }

    .table td {
      padding: 1rem;
      border-bottom: 1px solid #e2e8f0;
      transition: all 0.2s ease;
    }

    .table tr:hover td {
      background: #f7fafc;
    }

    .table tr.selected td {
      background: #ebf8ff;
    }

    .actions {
      display: flex;
      gap: 0.5rem;
    }

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

    .modal-actions {
      display: flex;
      justify-content: flex-end;
      gap: 1rem;
      margin-top: 1.5rem;
    }
  `]
})
export class ReservationListComponent implements OnInit {
  reservations: Reservation[] = [];
  filteredReservations: Reservation[] = [];
  selectedReservation: Reservation | null = null;
  searchTerm: string = '';
  showDeleteModal: boolean = false;
  showAddModal: boolean = false;
  isEditMode: boolean = false;

  constructor(private reservationService: ReservationService) {}

  ngOnInit() {
    this.loadReservations();
  }

  loadReservations() {
    this.reservationService.getAllReservations()
      .subscribe(data => {
        this.reservations = data;
        this.filterReservations();
      });
  }

  filterReservations() {
    if (!this.searchTerm) {
      this.filteredReservations = this.reservations;
      return;
    }
  
    const search = this.searchTerm.toLowerCase();
    this.filteredReservations = this.reservations.filter(reservation =>
      reservation.idReservation.toLowerCase().includes(search) ||
      reservation.chambre.numeroChambre.toString().includes(search) ||
      reservation.chambre.typeC.toLowerCase().includes(search)
    );
  }

  selectReservation(reservation: Reservation) {
    this.selectedReservation = reservation;
  }

  editReservation(reservation: Reservation) {
    this.selectedReservation = reservation;
    this.isEditMode = true;
    this.showAddModal = true;
    event?.stopPropagation();
  }

  confirmDelete(reservation: Reservation) {
    this.selectedReservation = reservation;
    this.showDeleteModal = true;
    event?.stopPropagation();
  }

  deleteReservation() {
    if (this.selectedReservation?.idReservation) {
      this.reservationService.deleteReservation(this.selectedReservation.idReservation.toString())
        .subscribe(() => {
          this.loadReservations();
          this.showDeleteModal = false;
          this.selectedReservation = null;
        });
    }
  }

  showCreateForm() {
    this.isEditMode = false;
    this.selectedReservation = null;
    this.showAddModal = true;
  }

  hideCreateForm() {
    this.showAddModal = false;
    this.isEditMode = false;
    this.selectedReservation = null;
  }

  onReservationAdded() {
    this.loadReservations();
  }
}