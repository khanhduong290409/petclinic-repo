import { useEffect, useState } from "react";
import ProductCard from "../../components/products/ProductCard";
import { fetchProducts } from "../../api/productApi";
import type { ProductRes, PageRes } from "../../types/product";

export default function ProductListPage() {
  const [products, setProducts] = useState<ProductRes[]>([]);
  const [pageInfo, setPageInfo] = useState<PageRes<ProductRes> | null>(null);

  const [keyword, setKeyword] = useState("");
  const [category, setCategory] = useState("");

  const [loading, setLoading] = useState(false);

  const loadProducts = async (page = 0) => {
    try {
      setLoading(true);
      const data = await fetchProducts({
        page,
        size: 12,
        keyword,
        category,
      });
      setProducts(data.content);
      setPageInfo(data);
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    loadProducts(0);
  }, [keyword, category]);

  return (
    <div style={styles.container}>
      <h1 style={styles.title}>Sản phẩm</h1>

      {/* Search box */}
      <div style={styles.filters}>
        <input
          type="text"
          placeholder="Tìm sản phẩm..."
          value={keyword}
          onChange={(e) => setKeyword(e.target.value)}
          style={styles.input}
        />

        <select
          value={category}
          onChange={(e) => setCategory(e.target.value)}
          style={styles.select}
        >
          <option value="">Tất cả danh mục</option>
          <option value="thuc-an-cho">Thức ăn cho chó</option>
          <option value="thuc-an-meo">Thức ăn cho mèo</option>
        </select>
      </div>

      {loading && <div>Đang tải...</div>}

      {/* Grid sản phẩm */}
      <div style={styles.grid}>
        {products.map((p) => (
          <ProductCard product={p} key={p.id} />
        ))}
      </div>

      {/* Pagination */}
      {pageInfo && pageInfo.totalPages > 1 && (
        <div style={styles.pagination}>
          <button
            disabled={pageInfo.page === 0}
            onClick={() => loadProducts(pageInfo.page - 1)}
          >
            Trước
          </button>

          <span>
            Trang {pageInfo.page + 1}/{pageInfo.totalPages}
          </span>

          <button
            disabled={pageInfo.page + 1 === pageInfo.totalPages}
            onClick={() => loadProducts(pageInfo.page + 1)}
          >
            Sau
          </button>
        </div>
      )}
    </div>
  );
}

const styles: Record<string, any> = {
  container: {
    maxWidth: 1200,
    margin: "0 auto",
    padding: 16,
  },
  title: {
    fontSize: 24,
    fontWeight: 700,
    marginBottom: 16,
  },
  filters: {
    display: "flex",
    gap: 8,
    marginBottom: 16,
  },
  input: {
    padding: "8px 12px",
    flex: 1,
    borderRadius: 4,
    border: "1px solid #ccc",
  },
  select: {
    padding: "8px 12px",
    borderRadius: 4,
    border: "11px solid #ccc",
  },
  grid: {
    display: "grid",
    gridTemplateColumns: "repeat(auto-fill, minmax(220px, 1fr))",
    gap: 16,
  },
  pagination: {
    marginTop: 16,
    display: "flex",
    justifyContent: "center",
    gap: 16,
  },
};
