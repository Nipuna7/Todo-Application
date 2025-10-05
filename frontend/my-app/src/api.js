const API_BASE = import.meta.env.VITE_API_URL || "http://localhost:8080";
export async function getAllTasks() {
  const res = await fetch(`${API_BASE}/tasks`);
  return res.json();
}
