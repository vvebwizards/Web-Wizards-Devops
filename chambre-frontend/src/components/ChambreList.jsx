// src/components/ChambreList.jsx
import React, { useState, useEffect } from "react";
import { getChambres, removeChambre } from "../services/chambreService";
import ChambreForm from "./ChambreForm";
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

  const handleChambreAdded = () => {
    setShowForm(false);
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
              <th>Bloc Name</th>
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
                <td>
                  {chambre.bloc && chambre.bloc.nomBloc
                    ? chambre.bloc.nomBloc
                    : "N/A"}
                </td>
                <td>
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
    </Container>
  );
};

export default ChambreList;
