import { Link } from "react-router-dom";
import hatmeoUrl from "../assets/hatmeo.jpg";
import daydatchoUrl from "../assets/daydatcho.webp";
import suatamtucungUrl from "../assets/suatamchomeo.jpg";
import pateUrl from "../assets/patechomeo.webp"; 

const MOCK = [
  { id: 1, name: "Hạt cho mèo", price: 180000, img: hatmeoUrl },
  { id: 2, name: "Dây dắt chó", price: 120000, img: daydatchoUrl },
  { id: 3, name: "Sữa tắm thú cưng", price: 140000, img: suatamtucungUrl },
  { id: 4, name: "Pate cho mèo", price: 48000, img: pateUrl },
];

export default function Products() {
  return (
    <section id="products" className="py-12 bg-sky-50">
      <div className="mx-auto max-w-7xl px-4">
        <div className="flex items-end justify-between">
          <h2 className="text-3xl md:text-4xl font-bold text-sky-900 mb-2">Sản phẩm tiêu biểu</h2>
          <Link to="/products" className="text-rose-700 hover:underline">Xem tất cả →</Link>
        </div>

        <div className="mt-6 grid sm:grid-cols-2 md:grid-cols-3 lg:grid-cols-4 gap-5">
          {MOCK.map(p => (
            <div key={p.id} className="rounded-xl overflow-hidden shadow-xl bg-white" >
              <img src={p.img} alt={p.name} className="w-full h-70 object-cover" />
              <div className="p-4">
                <div className="font-semibold">{p.name}</div>
                <div className="text-rose-700 font-bold">{p.price.toLocaleString()} đ</div>
              </div>
            </div>
          ))}
        </div>
      </div>
    </section>
  );
}
