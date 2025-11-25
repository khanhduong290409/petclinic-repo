import api from "./api";

export interface OwnerDTO {
  id?: number;// ? nghĩa là trường này có thể có hoặc không
  name: string;
  phone: string;
  email: string;
}

export const OwnerService = {
    list: () => api.get<OwnerDTO[]>('/owners/find-all'),
    create: (payload: OwnerDTO) => api.post<OwnerDTO>('/owners/add', payload),
    get: (id: number) => api.get<OwnerDTO>(`/owners/find-by-id/${id}`), 
    update: (id: number, payload: OwnerDTO) => api.put<OwnerDTO>(`/owners/update/${id}`, payload),
    remove: (id: number) => api.delete<void>(`/owners/delete/${id}`),
    // đoạn này chat gpt ghi thế này   remove: (id: number) => api.delete(`/owners//${id}`),
    // nếu có lỗi thì hãy thay lại

}
 
