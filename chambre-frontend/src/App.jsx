// src/App.jsx (Simplified version)
import React from "react";
import ChambreList from "./components/ChambreList";
import { Container } from "react-bootstrap";
import "./index.css";
import "bootstrap/dist/css/bootstrap.min.css";
ReactDOM.createRoot(document.getElementById("root")).render(
  <React.StrictMode>
    <ChambreList />
  </React.StrictMode>
);

export default App;
