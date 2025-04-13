// src/components/ChambreList.jsx
import React, { useState, useEffect } from "react";
import { getChambres, removeChambre } from "../services/chambreService";
import ChambreForm from "./ChambreForm";
import UpdateChambreModal from "./UpdateChambreModal";
import {
  Container,
  Table,
  Alert,
  Button,
  Card,
} from "react-bootstrap";

const ChambreList = () => {
  const [chambres, setChambres] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);
  const [showForm, setShowForm] = useState(false);
  const [editingChambre, setEditingChambre] = useState(null);
  const [showUpdateModal, setShowUpdateModal] = useState(false);

  // Fetch chambres from backend
  const fetchChambres = async () => {
    try {
      const data = await getChambres();
      setChambres(data);
    } catch (err) {
      setError("Failed to fetch chambres: " + err.message);
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    fetchChambres();
  }, []);

  // Delete a chambre
  const handleDelete = async (idChambre) => {
    if (window.confirm("Are you sure you want to delete this chambre?")) {
      try {
        await removeChambre(idChambre);
        fetchChambres();
      } catch (err) {
        setError("Error deleting chambre: " + err.message);
      }
    }
  };

  // Callback when a new chambre is added from the form
  const handleChambreAdded = () => {
    setShowForm(false);
    fetchChambres();
  };

  // Open the update modal with selected chambre details
  const handleEdit = (chambre) => {
    setEditingChambre(chambre);
    setShowUpdateModal(true);
  };

  // Callback when update is successful
  const handleChambreUpdated = () => {
    setShowUpdateModal(false);
    setEditingChambre(null);
    fetchChambres();
  };

  return (
    <Container fluid className="p-3">
      <h1 className="mb-3">Liste des Chambres</h1>

      {error && (
        <Alert variant="danger" className="mb-4">
          {error}
        </Alert>
      )}

      <Button
        variant="success"
        className="mb-3"
        onClick={() => setShowForm((prev) => !prev)}
      >
        {showForm ? "Hide Form" : "Add New Chambre"}
      </Button>

      {showForm && (
        <Card className="mb-4">
          <Card.Body>
            <ChambreForm onChambreAdded={handleChambreAdded} />
          </Card.Body>
        </Card>
      )}

      {loading && <Alert variant="info">Loading chambres...</Alert>}

      {!loading && chambres.length > 0 && (
        <Table striped bordered hover responsive>
          <thead className="table-dark">
            <tr>
              <th>ID</th>
              <th>Numero Chambre</th>
              <th>Type Chambre</th>
              <th>Reservations Count</th>
              <th>Bloc ID</th>
              <th>Actions</th>
            </tr>
          </thead>
          <tbody>
            {chambres.map((chambre) => (
              <tr key={chambre.idChambre}>
                <td>{chambre.idChambre}</td>
                <td>{chambre.numeroChambre}</td>
                <td>{chambre.typeC}</td>
                <td>{chambre.reservations ? chambre.reservations.length : 0}</td>
                <td>{chambre.bloc && chambre.bloc.idBloc ? chambre.bloc.idBloc : "N/A"}</td>
                <td>
                  <Button
                    variant="warning"
                    size="sm"
                    onClick={() => handleEdit(chambre)}
                    className="me-2"
                  >
                    Edit
                  </Button>
                  <Button
                    variant="danger"
                    size="sm"
                    onClick={() => handleDelete(chambre.idChambre)}
                  >
                    Delete
                  </Button>
                </td>
              </tr>
            ))}
          </tbody>
        </Table>
      )}

      {!loading && chambres.length === 0 && (
        <Alert variant="warning">No chambres found.</Alert>
      )}

      {/** Update Modal */}
      {showUpdateModal && editingChambre && (
        <UpdateChambreModal
          show={showUpdateModal}
          onHide={() => setShowUpdateModal(false)}
          chambre={editingChambre}
          onChambreUpdated={handleChambreUpdated}
        />
      )}
    </Container>
  );
};

export default ChambreList;
