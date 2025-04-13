import React, { useState, useEffect } from "react";
import { getChambres, removeChambre } from "../services/chambreService";
import ChambreForm from "./ChambreForm";

const ChambreList = () => {
  const [chambres, setChambres] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);

  // Function to fetch chambres
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
    fetchChambres();
  };

  if (loading) return <div>Loading chambres...</div>;
  if (error) return <div>Error: {error}</div>;

  return (
    <div>
      <h1>Liste des Chambres</h1>
      <ChambreForm onChambreAdded={handleChambreAdded} />
      <table border="1" cellPadding="5">
        <thead>
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
              <td>{chambre.bloc && chambre.bloc.nomBloc ? chambre.bloc.nomBloc : "N/A"}</td>
              <td>
                <button onClick={() => handleDelete(chambre.idChambre)}>
                  Delete
                </button>
              </td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
};

export default ChambreList;
