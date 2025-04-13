// src/services/blocService.js

const API_BASE_URL = "/bloc"; // Adjust if your proxy/rewrite is different.

// Fetch all blocs from the backend
export const getBlocs = async () => {
  try {
    const response = await fetch(`${API_BASE_URL}/retrieve-all-blocs`);
    if (!response.ok) {
      throw new Error("Failed to fetch blocs");
    }
    return await response.json();
  } catch (error) {
    console.error("Error in getBlocs:", error);
    throw error;
  }
};
