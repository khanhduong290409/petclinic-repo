import api from "./api";

export interface AppointmentDTO {
  id?: number;
  petId: number;
  startTime: string; // ISO (YYYY-MM-DDTHH:mm:ss)
  reason?: string;
  status?: string;
}

export const AppointmentService = {
  list: () => api.get<AppointmentDTO[]>("/appointments"),
  listByPet: (petId: number) => api.get<AppointmentDTO[]>(`/appointments/pet/${petId}`),
  create: (payload: AppointmentDTO) => api.post<AppointmentDTO>("/appointments", payload),
  update: (id: number, payload: AppointmentDTO) => api.put<AppointmentDTO>(`/appointments/${id}`, payload),
  remove: (id: number) => api.delete(`/appointments/${id}`),
};
