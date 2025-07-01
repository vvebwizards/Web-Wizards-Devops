import React, { useState, useEffect } from "react";
import { Modal, Button, Form, FormGroup, FormControl, FormLabel, Alert } from "react-bootstrap";
import { updateChambre } from "../services/chambreService";
import { getBlocs } from "../services/blocService";

const UpdateChambreModal = ({ show, onHide, chambre, onChambreUpdated }) => {
  const [numeroChambre, setNumeroChambre] = useState("");
  const [typeC, setTypeC] = useState("");
  const [selectedBlocId, setSelectedBlocId] = useState("");
  const [error, setError] = useState(null);
  const [blocs, setBlocs] = useState([]);
  const [loadingBlocs, setLoadingBlocs] = useState(true);

  // When the modal opens, pre-populate fields with chambre data.
  useEffect(() => {
    if (chambre) {
      setNumeroChambre(chambre.numeroChambre);
      setTypeC(chambre.typeC);
      // If there is a bloc then set its id, else set empty
      setSelectedBlocId(chambre.bloc && chambre.bloc.idBloc ? chambre.bloc.idBloc : "");
    }
  }, [chambre]);

  // Fetch blocs for the select box
  useEffect(() => {
    const fetchBlocs = async () => {
      try {
        const blocsData = await getBlocs();
        setBlocs(blocsData);
      } catch (err) {
        console.error("Unable to fetch blocs:", err);
      } finally {
        setLoadingBlocs(false);
      }
    };
    fetchBlocs();
  }, []);

  const handleSubmit = async (e) => {
    e.preventDefault();
    const updatedChambre = {
      ...chambre, // start with the existing chambre object
      numeroChambre: Number(numeroChambre),
      typeC: typeC,
      // Send bloc as an object with idBloc if selected, else null.
      bloc: selectedBlocId ? { idBloc: Number(selectedBlocId) } : null,
    };
    try {
      const result = await updateChambre(updatedChambre);
      onChambreUpdated(result);
      onHide();
    } catch (err) {
      setError("Failed to update chambre: " + err.message);
    }
  };

  return (
    <Modal show={show} onHide={onHide} centered>
      <Modal.Header closeButton>
        <Modal.Title>Update Chambre</Modal.Title>
      </Modal.Header>
      <Modal.Body>
        {error && <Alert variant="danger">{error}</Alert>}
        <Form onSubmit={handleSubmit}>
          <FormGroup className="mb-3">
            <FormLabel>Numero Chambre</FormLabel>
            <FormControl
              type="number"
              value={numeroChambre}
              onChange={(e) => setNumeroChambre(e.target.value)}
              required
            />
          </FormGroup>
          <FormGroup className="mb-3">
            <FormLabel>Type Chambre</FormLabel>
            <FormControl
              type="text"
              value={typeC}
              onChange={(e) => setTypeC(e.target.value)}
              placeholder="SIMPLE, DOUBLE, etc."
              required
            />
          </FormGroup>
          <FormGroup className="mb-3">
            <FormLabel>Select Bloc (Optional)</FormLabel>
            {loadingBlocs ? (
              <div>Loading blocs...</div>
            ) : (
              <FormControl
                as="select"
                value={selectedBlocId}
                onChange={(e) => setSelectedBlocId(e.target.value)}
              >
                <option value="">-- None --</option>
                {blocs.map((bloc) => (
                  <option key={bloc.idBloc} value={bloc.idBloc}>
                    {bloc.nomBloc} (ID: {bloc.idBloc})
                  </option>
                ))}
              </FormControl>
            )}
          </FormGroup>
          <Button variant="primary" type="submit">
            Update Chambre
          </Button>
        </Form>
      </Modal.Body>
    </Modal>
  );
};

export default UpdateChambreModal;
