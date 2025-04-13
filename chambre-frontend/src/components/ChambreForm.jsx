import React, { useState, useEffect } from "react";
import { addChambre } from "../services/chambreService";
import { getBlocs } from "../services/blocService";
import { Form, Button, FormGroup, FormControl, FormLabel, Alert } from "react-bootstrap";

const ChambreForm = ({ onChambreAdded }) => {
  const [numeroChambre, setNumeroChambre] = useState("");
  const [typeC, setTypeC] = useState("");
  const [selectedBlocId, setSelectedBlocId] = useState("");
  const [error, setError] = useState(null);
  const [blocs, setBlocs] = useState([]);
  const [loadingBlocs, setLoadingBlocs] = useState(true);

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
    const newChambre = {
      numeroChambre: Number(numeroChambre),
      typeC: typeC,
      bloc: selectedBlocId ? { idBloc: Number(selectedBlocId) } : null,
      reservations: [],
    };
    try {
      await addChambre(newChambre);
      onChambreAdded();
      setNumeroChambre("");
      setTypeC("");
      setSelectedBlocId("");
    } catch (err) {
      setError("Failed to add chambre: " + err.message);
    }
  };

  return (
    <>
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
                  {bloc.nomBloc}
                </option>
              ))}
            </FormControl>
          )}
        </FormGroup>

        <Button variant="success" type="submit">
          Add Chambre
        </Button>
      </Form>
    </>
  );
};

export default ChambreForm;
