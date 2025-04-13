// src/chambreService.js

const API_BASE_URL = "/chambre";

// Get all chambres
export const getChambres = async () => {
  try {
    const response = await fetch(`${API_BASE_URL}/retrieve-all-chambres`);
    if (!response.ok) {
      throw new Error("Failed to fetch chambres");
    }
    return await response.json();
  } catch (error) {
    console.error("Error in getChambres:", error);
    throw error;
  }
};

// Retrieve a single chambre by ID
export const retrieveChambre = async (chambreId) => {
  try {
    const response = await fetch(`${API_BASE_URL}/retrieve-chambre/${chambreId}`);
    if (!response.ok) {
      throw new Error("Failed to fetch chambre");
    }
    return await response.json();
  } catch (error) {
    console.error("Error in retrieveChambre:", error);
    throw error;
  }
};

// Add a new chambre
export const addChambre = async (chambre) => {
  try {
    const response = await fetch(`${API_BASE_URL}/add-chambre`, {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify(chambre),
    });
    if (!response.ok) {
      throw new Error("Failed to add chambre");
    }
    return await response.json();
  } catch (error) {
    console.error("Error in addChambre:", error);
    throw error;
  }
};

// Update an existing chambre
export const updateChambre = async (chambre) => {
  try {
    const response = await fetch(`${API_BASE_URL}/update-chambre`, {
      method: "PUT",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify(chambre),
    });
    if (!response.ok) {
      throw new Error("Failed to update chambre");
    }
    return await response.json();
  } catch (error) {
    console.error("Error in updateChambre:", error);
    throw error;
  }
};

// Remove a chambre by ID
export const removeChambre = async (idChambre) => {
  try {
    const response = await fetch(`${API_BASE_URL}/removeChambre/${idChambre}`, {
      method: "DELETE",
    });
    if (!response.ok) {
      throw new Error("Failed to remove chambre");
    }
    // Assuming the backend returns nothing on delete.
    return;
  } catch (error) {
    console.error("Error in removeChambre:", error);
    throw error;
  }
};

// Find chambres by type and bloc ID
export const findByTypeCAndBlocIdBloc = async (typeChambre, idBloc) => {
  try {
    const response = await fetch(`${API_BASE_URL}/findByTypeCAndBlocIdBloc/${typeChambre}/${idBloc}`);
    if (!response.ok) {
      throw new Error("Failed to fetch chambres by type and bloc");
    }
    return await response.json();
  } catch (error) {
    console.error("Error in findByTypeCAndBlocIdBloc:", error);
    throw error;
  }
};

// Find chambres by valid reservation status
export const findByReservationsEstValid = async (estValid) => {
  try {
    const response = await fetch(`${API_BASE_URL}/findByReservationsEstValid/${estValid}`);
    if (!response.ok) {
      throw new Error("Failed to fetch chambres by reservation status");
    }
    return await response.json();
  } catch (error) {
    console.error("Error in findByReservationsEstValid:", error);
    throw error;
  }
};

// Find chambres by bloc ID and bloc capacity greater than the provided capacity
export const findByBlocIdBlocAndBlocCapaciteBloc = async (idBloc, capaciteBloc) => {
  try {
    const response = await fetch(`${API_BASE_URL}/findByBlocIdBlocAndBlocCapaciteBloc/${idBloc}/${capaciteBloc}`);
    if (!response.ok) {
      throw new Error("Failed to fetch chambres by bloc capacity");
    }
    return await response.json();
  } catch (error) {
    console.error("Error in findByBlocIdBlocAndBlocCapaciteBloc:", error);
    throw error;
  }
};

// Get chambres by bloc name
export const getChambresParNomBloc = async (nomBloc) => {
  try {
    const response = await fetch(`${API_BASE_URL}/getChambresParNomBloc/${encodeURIComponent(nomBloc)}`);
    if (!response.ok) {
      throw new Error("Failed to fetch chambres by bloc name");
    }
    return await response.json();
  } catch (error) {
    console.error("Error in getChambresParNomBloc:", error);
    throw error;
  }
};

// Get number of chambres by type and bloc ID
export const nbChambreParTypeEtBloc = async (type, idBloc) => {
  try {
    const response = await fetch(`${API_BASE_URL}/nbChambreParTypeEtBloc/${type}/${idBloc}`);
    if (!response.ok) {
      throw new Error("Failed to fetch number of chambres by type and bloc");
    }
    return await response.json();
  } catch (error) {
    console.error("Error in nbChambreParTypeEtBloc:", error);
    throw error;
  }
};

// Get non-reserved chambres by foyer name and chambre type
export const getChambresNonReserveParNomFoyerEtTypeChambre = async (nomFoyer, type) => {
  try {
    const response = await fetch(`${API_BASE_URL}/getChambresNonReserveParNomFoyerEtTypeChambre/${encodeURIComponent(nomFoyer)}/${type}`);
    if (!response.ok) {
      throw new Error("Failed to fetch non-reserved chambres by foyer and type");
    }
    return await response.json();
  } catch (error) {
    console.error("Error in getChambresNonReserveParNomFoyerEtTypeChambre:", error);
    throw error;
  }
};
