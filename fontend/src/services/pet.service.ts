import api from "./api";

export interface PetDTO {
  id?: number;
  name: string;
  species?: string; 
  breed?: string;
  age?: number;
  ownerId: number;
  imageUrl?: string;
}
export const PetService = {
  list: () => api.get<PetDTO[]>("/pets/find-all"),
  listByOwner: (ownerId: number) => api.get<PetDTO[]>(`/pets/find-by-owner/${ownerId}`),
  create: (payload: PetDTO) => api.post<PetDTO>("/pets/add", payload),
  get: (id: number) => api.get<PetDTO>(`/pets/find-by-id/${id}`),
  update: (id: number, payload: PetDTO) => api.put<PetDTO>(`/pets/update/${id}`, payload),
  remove: (id: number) => api.delete<void>(`/pets/delete/${id}`),

  createWithImage: (payload: PetDTO, file?: File) => {
    const form = new FormData();
    form.append("data", new Blob([JSON.stringify(payload)], { type: "application/json" }));
    if (file) form.append("image", file);
    return api.post<PetDTO>("/pets/multipart", form);
  },

  updateImage: (id: number, file: File) => {
    const form = new FormData();
    form.append("image", file);
    return api.put<PetDTO>(`/pets/update-image/${id}`, form); // khớp backend
  },
};
