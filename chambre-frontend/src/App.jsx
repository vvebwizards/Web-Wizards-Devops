// src/App.jsx
import React from "react";
import { Container } from "react-bootstrap";
import ChambreList from "./components/ChambreList";
import ChambreQueries from "./components/ChambreQueries"; // if you want to include queries
import "./index.css";
import "bootstrap/dist/css/bootstrap.min.css";

function App() {
  return (
    <Container fluid className="p-3">
      <h1 className="mb-3">Chambre Management</h1>
      <ChambreList />
      <hr />
      <ChambreQueries />
    </Container>
  );
}

export default App;
