import { useParams } from "react-router-dom";
import { useEffect, useState } from "react";
import { fetchProductDetailBySlug } from "../../api/productApi";
import type { ProductDetailRes } from "../../types/product";

export default function ProductDetailPage() {
  const { slug } = useParams<{ slug: string }>();
  const [product, setProduct] = useState<ProductDetailRes | null>(null);
  const [mainImage, setMainImage] = useState<string>("");

  useEffect(() => {
    const load = async () => {
      if (!slug) return;
      const res = await fetchProductDetailBySlug(slug);
      setProduct(res);

      if (res.imageUrls.length > 0) {
        setMainImage(`http://localhost:8080${res.imageUrls[0]}`);
      }
    };

    load();
  }, [slug]);

  if (!product) return <div>Đang tải...</div>;

  return (
    <div style={styles.container}>
      <div style={styles.layout}>
        {/* Cột ảnh */}
        <div style={styles.left}>
          <img src={mainImage} style={styles.mainImage} />

          <div style={styles.thumbnailList}>
            {product.imageUrls.map((img, idx) => {
              const full = `http://localhost:8080${img}`;
              return (
                <img
                  key={idx}
                  src={full}
                  style={{
                    ...styles.thumbnail,
                    border: full === mainImage ? "2px solid #1976d2" : "1px solid #ddd",
                  }}
                  onClick={() => setMainImage(full)}
                />
              );
            })}
          </div>
        </div>

        {/* Cột thông tin */}
        <div style={styles.right}>
          <h1>{product.name}</h1>
          <h2 style={styles.price}>
            {product.price.toLocaleString("vi-VN")}₫
          </h2>

          <p>{product.shortDescription}</p>

          <button style={styles.cartBtn} onClick={() => alert("TODO giỏ hàng")}>
            Thêm vào giỏ hàng
          </button>

          <h3>Mô tả</h3>
          <p>{product.description}</p>
        </div>
      </div>
    </div>
  );
}

const styles: Record<string, any> = {
  container: { maxWidth: 1200, margin: "0 auto", padding: 16 },
  layout: { display: "grid", gridTemplateColumns: "1fr 1fr", gap: 20 },
  left: { display: "flex", flexDirection: "column", gap: 12 },
  mainImage: { width: "100%", borderRadius: 8 },
  thumbnailList: { display: "flex", gap: 8 },
  thumbnail: { width: 60, height: 60, cursor: "pointer", borderRadius: 4 },
  right: { display: "flex", flexDirection: "column", gap: 12 },
  price: { fontSize: 22, fontWeight: 700, color: "#e53935" },
  cartBtn: {
    padding: "10px 18px",
    background: "#ff9800",
    border: "none",
    color: "#fff",
    cursor: "pointer",
    borderRadius: 4,
  },
};
