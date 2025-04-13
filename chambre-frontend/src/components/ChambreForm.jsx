import React, { useState, useEffect } from "react";
import { addChambre } from "../services/chambreService";
import { getBlocs } from "../services/blocService";

const ChambreForm = ({ onChambreAdded }) => {
  // Form fields
  const [numeroChambre, setNumeroChambre] = useState("");
  const [typeC, setTypeC] = useState("");
  const [selectedBlocId, setSelectedBlocId] = useState("");
  const [error, setError] = useState(null);

  // Blocs state for select dropdown
  const [blocs, setBlocs] = useState([]);
  const [loadingBlocs, setLoadingBlocs] = useState(true);

  // Fetch blocs on mount
  useEffect(() => {
    const fetchBlocs = async () => {
      try {
        const blocsData = await getBlocs();
        setBlocs(blocsData);
      } catch (error) {
        console.error("Unable to fetch blocs:", error);
      } finally {
        setLoadingBlocs(false);
      }
    };

    fetchBlocs();
  }, []);

  const handleSubmit = async (e) => {
    e.preventDefault();
    // Create the new chambre object.
    // Here, the bloc is added as an object with its id.
    const newChambre = {
      numeroChambre: Number(numeroChambre),
      typeC: typeC,
      // Set bloc with the selected bloc id if one is chosen.
      bloc: selectedBlocId ? { idBloc: Number(selectedBlocId) } : null,
      // Leave reservations empty on create.
      reservations: []
    };

    try {
      const added = await addChambre(newChambre);
      onChambreAdded(added);
      // Reset form fields after submission.
      setNumeroChambre("");
      setTypeC("");
      setSelectedBlocId("");
    } catch (err) {
      setError("Failed to add chambre: " + err.message);
    }
  };

  return (
    <div>
      <h2>Add a New Chambre</h2>
      {error && <p style={{ color: "red" }}>{error}</p>}
      <form onSubmit={handleSubmit}>
        <div>
          <label>Numero Chambre: </label>
          <input
            type="number"
            value={numeroChambre}
            onChange={(e) => setNumeroChambre(e.target.value)}
            required
          />
        </div>
        <div>
          <label>Type Chambre: </label>
          <input
            type="text"
            value={typeC}
            onChange={(e) => setTypeC(e.target.value)}
            placeholder="SIMPLE, DOUBLE, etc."
            required
          />
        </div>
        <div>
          <label>Select Bloc: </label>
          {loadingBlocs ? (
            <span>Loading blocs...</span>
          ) : (
            <select
              value={selectedBlocId}
              onChange={(e) => setSelectedBlocId(e.target.value)}
              required
            >
              <option value="">-- Select a Bloc --</option>
              {blocs.map((bloc) => (
                <option key={bloc.idBloc} value={bloc.idBloc}>
                  {bloc.nomBloc}
                </option>
              ))}
            </select>
          )}
        </div>
        <button type="submit">Add Chambre</button>
      </form>
    </div>
  );
};

export default ChambreForm;
