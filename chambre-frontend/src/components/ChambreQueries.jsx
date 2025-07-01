import React, { useState } from "react";
import {
  findByTypeCAndBlocIdBloc,
  findByReservationsEstValid,
  findByBlocIdBlocAndBlocCapaciteBloc,
  getChambresParNomBloc,
  nbChambreParTypeEtBloc,
  getChambresNonReserveParNomFoyerEtTypeChambre,
} from "../services/chambreService";
import { Container, Form, Button, Alert, Row, Col } from "react-bootstrap";

const ChambreQueries = () => {
  // Query 1: findByTypeCAndBlocIdBloc
  const [typeQuery, setTypeQuery] = useState("");
  const [blocIdQuery, setBlocIdQuery] = useState("");
  const [result1, setResult1] = useState(null);

  // Query 2: findByReservationsEstValid
  const [estValid, setEstValid] = useState("true");
  const [result2, setResult2] = useState(null);

  // Query 3: findByBlocIdBlocAndBlocCapaciteBloc
  const [blocIdQuery3, setBlocIdQuery3] = useState("");
  const [capaciteQuery3, setCapaciteQuery3] = useState("");
  const [result3, setResult3] = useState(null);

  // Query 4: getChambresParNomBloc
  const [nomBloc, setNomBloc] = useState("");
  const [result4, setResult4] = useState(null);

  // Query 5: nbChambreParTypeEtBloc
  const [typeQuery5, setTypeQuery5] = useState("");
  const [blocIdQuery5, setBlocIdQuery5] = useState("");
  const [result5, setResult5] = useState(null);

  // Query 6: getChambresNonReserveParNomFoyerEtTypeChambre
  const [nomFoyer, setNomFoyer] = useState("");
  const [typeQuery6, setTypeQuery6] = useState("");
  const [result6, setResult6] = useState(null);

  // Handlers for each query
  const handleQuery1 = async (e) => {
    e.preventDefault();
    try {
      const data = await findByTypeCAndBlocIdBloc(typeQuery, blocIdQuery);
      setResult1(data);
    } catch (err) {
      setResult1("Error: " + err.message);
    }
  };

  const handleQuery2 = async (e) => {
    e.preventDefault();
    try {
      const data = await findByReservationsEstValid(estValid);
      setResult2(data);
    } catch (err) {
      setResult2("Error: " + err.message);
    }
  };

  const handleQuery3 = async (e) => {
    e.preventDefault();
    try {
      const data = await findByBlocIdBlocAndBlocCapaciteBloc(blocIdQuery3, capaciteQuery3);
      setResult3(data);
    } catch (err) {
      setResult3("Error: " + err.message);
    }
  };

  const handleQuery4 = async (e) => {
    e.preventDefault();
    try {
      const data = await getChambresParNomBloc(nomBloc);
      setResult4(data);
    } catch (err) {
      setResult4("Error: " + err.message);
    }
  };

  const handleQuery5 = async (e) => {
    e.preventDefault();
    try {
      const data = await nbChambreParTypeEtBloc(typeQuery5, blocIdQuery5);
      setResult5(data);
    } catch (err) {
      setResult5("Error: " + err.message);
    }
  };

  const handleQuery6 = async (e) => {
    e.preventDefault();
    try {
      const data = await getChambresNonReserveParNomFoyerEtTypeChambre(nomFoyer, typeQuery6);
      setResult6(data);
    } catch (err) {
      setResult6("Error: " + err.message);
    }
  };

  // A helper function to render JSON results nicely
  const renderResult = (res) =>
    res ? (
      <pre style={{ backgroundColor: "#f8f9fa", padding: "10px", maxHeight: "200px", overflowY: "auto" }}>
        {typeof res === "string" ? res : JSON.stringify(res, null, 2)}
      </pre>
    ) : null;

  return (
    <Container className="my-4">
      <h2>Chambre Queries</h2>
      
      {/* Query 1 */}
      <Form onSubmit={handleQuery1} className="mb-3">
        <Row>
          <Col md={4}>
            <Form.Group>
              <Form.Label>Type Chambre</Form.Label>
              <Form.Control type="text" value={typeQuery} onChange={(e) => setTypeQuery(e.target.value)} required />
            </Form.Group>
          </Col>
          <Col md={4}>
            <Form.Group>
              <Form.Label>Bloc ID</Form.Label>
              <Form.Control type="number" value={blocIdQuery} onChange={(e) => setBlocIdQuery(e.target.value)} required />
            </Form.Group>
          </Col>
          <Col md={4} className="d-flex align-items-end">
            <Button type="submit" variant="primary">Query 1</Button>
          </Col>
        </Row>
      </Form>
      {renderResult(result1)}

      {/* Query 2 */}
      <Form onSubmit={handleQuery2} className="mb-3">
        <Row>
          <Col md={4}>
            <Form.Group>
              <Form.Label>Reservations Valid (true/false)</Form.Label>
              <Form.Control as="select" value={estValid} onChange={(e) => setEstValid(e.target.value)} required>
                <option value="true">true</option>
                <option value="false">false</option>
              </Form.Control>
            </Form.Group>
          </Col>
          <Col md={8} className="d-flex align-items-end">
            <Button type="submit" variant="primary">Query 2</Button>
          </Col>
        </Row>
      </Form>
      {renderResult(result2)}

      {/* Query 3 */}
      <Form onSubmit={handleQuery3} className="mb-3">
        <Row>
          <Col md={4}>
            <Form.Group>
              <Form.Label>Bloc ID</Form.Label>
              <Form.Control type="number" value={blocIdQuery3} onChange={(e) => setBlocIdQuery3(e.target.value)} required />
            </Form.Group>
          </Col>
          <Col md={4}>
            <Form.Group>
              <Form.Label>Bloc Capacite</Form.Label>
              <Form.Control type="number" value={capaciteQuery3} onChange={(e) => setCapaciteQuery3(e.target.value)} required />
            </Form.Group>
          </Col>
          <Col md={4} className="d-flex align-items-end">
            <Button type="submit" variant="primary">Query 3</Button>
          </Col>
        </Row>
      </Form>
      {renderResult(result3)}

      {/* Query 4 */}
      <Form onSubmit={handleQuery4} className="mb-3">
        <Row>
          <Col md={4}>
            <Form.Group>
              <Form.Label>Nom Bloc</Form.Label>
              <Form.Control type="text" value={nomBloc} onChange={(e) => setNomBloc(e.target.value)} required />
            </Form.Group>
          </Col>
          <Col md={8} className="d-flex align-items-end">
            <Button type="submit" variant="primary">Query 4</Button>
          </Col>
        </Row>
      </Form>
      {renderResult(result4)}

      {/* Query 5 */}
      <Form onSubmit={handleQuery5} className="mb-3">
        <Row>
          <Col md={4}>
            <Form.Group>
              <Form.Label>Type Chambre</Form.Label>
              <Form.Control type="text" value={typeQuery5} onChange={(e) => setTypeQuery5(e.target.value)} required />
            </Form.Group>
          </Col>
          <Col md={4}>
            <Form.Group>
              <Form.Label>Bloc ID</Form.Label>
              <Form.Control type="number" value={blocIdQuery5} onChange={(e) => setBlocIdQuery5(e.target.value)} required />
            </Form.Group>
          </Col>
          <Col md={4} className="d-flex align-items-end">
            <Button type="submit" variant="primary">Query 5</Button>
          </Col>
        </Row>
      </Form>
      {renderResult(result5)}

      {/* Query 6 */}
      <Form onSubmit={handleQuery6} className="mb-3">
        <Row>
          <Col md={4}>
            <Form.Group>
              <Form.Label>Nom Foyer</Form.Label>
              <Form.Control type="text" value={nomFoyer} onChange={(e) => setNomFoyer(e.target.value)} required />
            </Form.Group>
          </Col>
          <Col md={4}>
            <Form.Group>
              <Form.Label>Type Chambre</Form.Label>
              <Form.Control type="text" value={typeQuery6} onChange={(e) => setTypeQuery6(e.target.value)} required />
            </Form.Group>
          </Col>
          <Col md={4} className="d-flex align-items-end">
            <Button type="submit" variant="primary">Query 6</Button>
          </Col>
        </Row>
      </Form>
      {renderResult(result6)}
    </Container>
  );
};

export default ChambreQueries;
