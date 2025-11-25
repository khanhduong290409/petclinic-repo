import { useState } from "react";
import { AppointmentService, type AppointmentDTO } from "../services/appointment.service";
import { isAxiosError } from "axios";

export default function AppointmentCreate() {
  const [petId, setPetId] = useState<string>("");
  const [startTime, setStartTime] = useState<string>(""); // yyyy-MM-ddTHH:mm
  const [reason, setReason] = useState<string>("");

  const [res, setRes] = useState<AppointmentDTO | null>(null);
  const [err, setErr] = useState<string>("");

  const submit = async (e: React.FormEvent) => {
    e.preventDefault();
    setErr("");
    try {
      if (!petId || !startTime) throw new Error("PetId và thời gian bắt buộc");
      const iso = startTime.length === 16 ? startTime + ":00" : startTime;
      const out = await AppointmentService.create({
        petId: Number(petId),
        startTime: iso,
        reason: reason || undefined,
      });
      setRes(out.data);
    } catch (e: unknown) {
      if (isAxiosError<{ message?: string }>(e)) setErr(e.response?.data?.message ?? e.message ?? "Error");
      else if (e instanceof Error) setErr(e.message);
      else setErr("Unknown error");
    }
  };

  return (
    <div className="p-6 max-w-xl space-y-4">
      <h1 className="text-2xl font-bold">Create Appointment</h1>
      <form className="space-y-3" onSubmit={submit}>
        <input className="border p-2 w-full" type="number" placeholder="Pet ID *"
               value={petId} onChange={(e)=>setPetId(e.target.value)} required/>
        <label className="block">
          <span className="text-sm text-gray-600">Start Time *</span>
          <input className="border rounded w-full p-2 mt-1" type="datetime-local"
                 value={startTime} onChange={(e)=>setStartTime(e.target.value)} required/>
        </label>
        <input className="border p-2 w-full" placeholder="Reason" value={reason} onChange={(e)=>setReason(e.target.value)} />
        <button className="bg-blue-600 text-white px-4 py-2 rounded">Save</button>
      </form>

      {err && <p className="text-red-600">{err}</p>}
      {res && <pre className="bg-gray-100 p-3 rounded">{JSON.stringify(res, null, 2)}</pre>}
    </div>
  );
}
