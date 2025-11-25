import { Link } from "react-router-dom";
import type { ProductRes } from "../../types/product";

interface Props {
  product: ProductRes;
}

export default function ProductCard({ product }: Props) {
  const thumbnail = product.thumbnailUrl
    ? `http://localhost:8080${product.thumbnailUrl}`
    : "https://via.placeholder.com/300x200?text=No+Image";

  const formatPrice = (price: number) => {
    return price.toLocaleString("vi-VN") + "₫";
  };

  return (
    <div style={styles.card}>
      <Link to={`/products/${product.slug}`} style={styles.imageWrapper}>
        <img src={thumbnail} alt={product.name} style={styles.image} />
      </Link>

      <div style={styles.body}>
        <Link to={`/products/${product.slug}`} style={styles.name}>
          {product.name}
        </Link>

        {product.categoryName && (
          <div style={styles.category}>{product.categoryName}</div>
        )}

        <div style={styles.price}>{formatPrice(product.price)}</div>
      </div>
    </div>
  );
}

const styles: Record<string, any> = {
  card: {
    border: "1px solid #eee",
    borderRadius: 8,
    padding: 12,
    display: "flex",
    flexDirection: "column",
    gap: 8,
    background: "#fff",
  },
  imageWrapper: {
    display: "block",
    borderRadius: 8,
    overflow: "hidden",
  },
  image: {
    width: "100%",
    height: 180,
    objectFit: "cover",
    display: "block",
  },
  body: {
    display: "flex",
    flexDirection: "column",
    gap: 4,
  },
  name: {
    fontWeight: 600,
    color: "#222",
    textDecoration: "none",
  },
  category: {
    fontSize: 13,
    color: "#777",
  },
  price: {
    marginTop: 4,
    fontWeight: 700,
    color: "#e53935",
  },
};
