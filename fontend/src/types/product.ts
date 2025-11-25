export interface ProductRes {
  id: number;
  name: string;
  slug: string;
  price: number;
  thumbnailUrl: string | null;
  categoryName: string | null;
}

export interface PageRes<T> {
  content: T[];
  page: number;
  size: number;
  totalElements: number;
  totalPages: number;
}

export interface ProductDetailRes {
  id: number;
  name: string;
  slug: string;
  price: number;
  shortDescription: string | null;
  description: string | null;
  categoryName: string | null;
  imageUrls: string[]; // list ảnh
}
