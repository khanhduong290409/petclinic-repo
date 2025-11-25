import React from "react";

import doctorDog from "../assets/poster-service.webp"; // ảnh trung tâm
import backgroundSerive from "../assets/bg-chim-service.jpg"; // ảnh nền chìm
import capcuuUrl from "../assets/doctor.jpg";
import noikhoaUrl from "../assets/doctor.jpg";
import sanUrl from "../assets/doctor.jpg";
import ngoaikhoaUrl from "../assets/doctor.jpg";
import chandoanUrl from "../assets/doctor.jpg";
import khamTongQuatUrl from "../assets/doctor.jpg";

export default function Services() {
  const leftServices = [
    {
      title: "Khám & Điều Trị Tổng Quát Thú Cưng",
      desc: "Cung cấp dịch vụ khám sức khỏe định kỳ, tư vấn dinh dưỡng, tiêm phòng...",
      img: khamTongQuatUrl,
    },
    {
      title: "Chuyên Khoa Nội",
      desc: "Điều trị bệnh lý nội khoa ở chó mèo như tim mạch, tiêu hóa, hô hấp...",
      img: noikhoaUrl,
    },
    {
      title: "Chuyên Khoa Sản",
      desc: "Chăm sóc sức khỏe sinh sản toàn diện cho thú cưng, hỗ trợ đỡ đẻ...",
      img: sanUrl,
    },
  ];

  const rightServices = [
    {
      title: "Cấp Cứu Thú Cưng 24/7",
      desc: "Đội ngũ bác sĩ thú y nhiều kinh nghiệm, sẵn sàng mọi lúc với thiết bị hiện đại.",
      img: capcuuUrl,
    },
    {
      title: "Chuyên Khoa Ngoại",
      desc: "Phẫu thuật an toàn, hiệu quả cho thú cưng, từ triệt sản đến phẫu thuật phức tạp.",
      img: ngoaikhoaUrl,
    },
    {
      title: "Chẩn Đoán Hình Ảnh",
      desc: "Cung cấp dịch vụ X-quang, siêu âm, xét nghiệm máu chính xác.",
      img: chandoanUrl,
    },
  ];

  return (
    <section
      id="services"
      className="relative py-20 bg-cover bg-center"
      style={{ backgroundImage: `url(${backgroundSerive})` }}
    >
      {/* lớp phủ làm mềm nền */}
      <div className="absolute inset-0 bg-white/70 z-0 pointer-events-none" />

      <div className="relative z-10 max-w-7xl mx-auto px-4 text-center">
        <h2 className="text-3xl md:text-4xl font-bold text-sky-900 mb-12">
          CÁC DỊCH VỤ CHÍNH
        </h2>

        {/* Giữ 3 cột từ >=640px; <640px mới xuống 1 cột */}
        <div className="grid grid-cols-1 sm:grid-cols-3 items-center gap-3 lg:gap-4 mx-auto">
          {/* LEFT COLUMN */}
          <div className="flex flex-col gap-6 text-right sm:pr-2">
            {leftServices.map((s) => (
              <div key={s.title} className="flex items-center justify-end gap-4">
                <div className="max-w-[260px]">
                  <h3 className="font-bold text-sky-900 text-base md:text-lg">
                    {s.title}
                  </h3>
                  <p className="text-gray-600 text-sm md:text-[15px] leading-relaxed">
                    {s.desc}
                  </p>
                </div>
                <img
                  src={s.img}
                  alt={s.title}
                  className="w-20 h-20 md:w-24 md:h-24 rounded-full object-cover shadow-md ring-4 ring-white"
                  loading="lazy"
                />
              </div>
            ))}
          </div>

          {/* CENTER IMAGE */}
          <div className="flex justify-center">
            <img
              src={doctorDog}
              alt="Bác sĩ và thú cưng"
              className="
                rounded-3xl shadow-lg ring-4 ring-white object-cover
                w-[320px] md:w-[420px] lg:w-[520px] h-auto max-h-[520px]
              "
            />
          </div>

          {/* RIGHT COLUMN */}
          <div className="flex flex-col gap-6 text-left sm:pl-2">
            {rightServices.map((s) => (
              <div key={s.title} className="flex items-center gap-4">
                <img
                  src={s.img}
                  alt={s.title}
                  className="w-20 h-20 md:w-24 md:h-24 rounded-full object-cover shadow-md ring-4 ring-white"
                  loading="lazy"
                />
                <div className="max-w-[260px]">
                  <h3 className="font-bold text-sky-900 text-base md:text-lg">
                    {s.title}
                  </h3>
                  <p className="text-gray-600 text-sm md:text-[15px] leading-relaxed">
                    {s.desc}
                  </p>
                </div>
              </div>
            ))}
          </div>
        </div>
      </div>
    </section>
  );
}
