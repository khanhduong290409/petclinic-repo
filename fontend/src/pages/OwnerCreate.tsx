import { useState } from "react";
import { OwnerService, type OwnerDTO } from "../services/owner.service";
import { isAxiosError } from "axios";

export default function OwnerCreate() {
  const [name, setName] = useState("");
  const [phone, setPhone] = useState("");
  const [email, setEmail] = useState("");
  const [res, setRes] = useState<OwnerDTO | null>(null);
  const [err, setErr] = useState("");

  const submit = async (e: React.FormEvent) => {
    e.preventDefault();
    setErr("");
    try {
      const out = await OwnerService.create({ name, phone, email });
      setRes(out.data);
    } catch (e: unknown) {
      if (isAxiosError<{ message?: string }>(e)) setErr(e.response?.data?.message ?? e.message);
      else if (e instanceof Error) setErr(e.message);
      else setErr("Unknown error");
    }
  };

  return (
    <div className="p-6 max-w-xl space-y-4">
      <h1 className="text-2xl font-bold">Create Owner</h1>
      <form className="space-y-3" onSubmit={submit}>
        <input className="border p-2 w-full" placeholder="Name *" value={name} onChange={e=>setName(e.target.value)} required/>
        <input className="border p-2 w-full" placeholder="Phone" value={phone} onChange={e=>setPhone(e.target.value)} />
        <input className="border p-2 w-full" type="email" placeholder="Email" value={email} onChange={e=>setEmail(e.target.value)} />
        <button className="bg-blue-600 text-white px-4 py-2 rounded">Save</button>
      </form>
      {err && <p className="text-red-600">{err}</p>}
      {res && <pre className="bg-gray-100 p-3 rounded">{JSON.stringify(res, null, 2)}</pre>}
    </div>
  );
}
