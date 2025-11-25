const REVIEWS = [
  { name: "Anh Khoa", text: "Bác sĩ tận tâm, đặt lịch nhanh gọn. Boss khỏe hơn hẳn!", pet: "Milu" },
  { name: "Chị Linh", text: "Grooming rất ok, mèo thơm phức, cắt tỉa đẹp.", pet: "Na" },
  { name: "Bạn Huy",  text: "Tư vấn dinh dưỡng hợp lý, boss ăn ngon miệng.", pet: "Bé Bông" },
];

export default function Testimonials() {
  return (
    <section id="feedback" className="py-12 bg-sky-50">
      <div className="mx-auto max-w-7xl px-4">
        <h2 className="text-3xl md:text-4xl font-bold text-sky-900 mb-12">Khách hàng nói gì?</h2>
        <div className="mt-6 grid md:grid-cols-3 gap-5">
          {REVIEWS.map(r => (
            <div key={r.name} className="rounded-xl bg-white p-5 border">
              <div className="font-semibold">{r.name}</div>
              <p className="text-gray-600 text-sm mt-2">“{r.text}”</p>
              <div className="text-xs text-gray-500 mt-3">Pet: {r.pet}</div>
            </div>
          ))}
        </div>
      </div>
    </section>
  );
}
