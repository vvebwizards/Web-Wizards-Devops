// src/components/ChambreList.jsx
import React, { useState, useEffect } from "react";
import { getChambres } from "../services/chambreService";

const ChambreList = () => {
  const [chambres, setChambres] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);

  useEffect(() => {
    const fetchChambres = async () => {
      try {
        const data = await getChambres();
        setChambres(data);
      } catch (err) {
        setError(err.message);
      } finally {
        setLoading(false);
      }
    };

    fetchChambres();
  }, []);

  if (loading) return <div>Loading chambres...</div>;
  if (error) return <div>Error: {error}</div>;

  return (
    <div>
      <h1>Liste des Chambres</h1>
      <table border="1" cellPadding="5">
        <thead>
          <tr>
            <th>ID</th>
            <th>Numero Chambre</th>
            <th>Type Chambre</th>
            <th>Reservations Count</th>
            <th>Bloc Name</th>
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
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
};

export default ChambreList;
