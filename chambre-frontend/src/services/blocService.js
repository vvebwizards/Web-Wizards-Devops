// src/services/blocService.js

const API_BASE_URL = "/bloc"; // With Vite proxy, this should be correct

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
