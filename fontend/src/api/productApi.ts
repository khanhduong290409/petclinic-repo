import type { PageRes, ProductRes, ProductDetailRes } from "../types/product";

const BASE_URL = "http://localhost:8080/api";

export async function fetchProducts(params: {
  page?: number;
  size?: number;
  keyword?: string;
  category?: string;
}): Promise<PageRes<ProductRes>> {
  const query = new URLSearchParams();

  query.set("page", String(params.page ?? 0));
  query.set("size", String(params.size ?? 12));

  if (params.keyword) query.set("keyword", params.keyword);
  if (params.category) query.set("category", params.category);

  const res = await fetch(`${BASE_URL}/products?${query.toString()}`);

  if (!res.ok) throw new Error("Không thể load danh sách sản phẩm");

  return res.json();
}

export async function fetchProductDetailBySlug(
  slug: string
): Promise<ProductDetailRes> {
  const res = await fetch(`${BASE_URL}/products/slug/${encodeURIComponent(slug)}`);

  if (!res.ok) throw new Error("Không thể load chi tiết sản phẩm");

  return res.json();
}
