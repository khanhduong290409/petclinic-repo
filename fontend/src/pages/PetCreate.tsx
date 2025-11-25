import { useState, useEffect } from "react";
import { PetService } from "../services/pet.service";
import type { PetDTO } from "../services/pet.service";
import axios from "axios";

export default function PetCreate() {
  const [name, setName] = useState("");
  const [species, setSpecies] = useState("");
  const [breed, setBreed] = useState("");
  const [age, setAge] = useState<number | "">("");
  const [ownerId, setOwnerId] = useState<number | "">("");
  const [file, setFile] = useState<File | undefined>(undefined);
  const [preview, setPreview] = useState<string | undefined>(undefined);

  const [result, setResult] = useState<PetDTO | null>(null);
  const [error, setError] = useState<string>("");

  // cleanup blob URL khi component unmount
  useEffect(() => {
    return () => {
      if (preview?.startsWith("blob:")) URL.revokeObjectURL(preview);
    };
  }, [preview]);

  const onFileChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    const f = e.target.files?.[0];
    setFile(f);
    setPreview(f ? URL.createObjectURL(f) : undefined);
  };

  const submit = async (e: React.FormEvent) => {
    e.preventDefault();
    setError("");
    try {
      if (ownerId === "" || name.trim() === "") {
        throw new Error("Vui lòng nhập Name và OwnerId.");
      }
      const payload: PetDTO = {
        name,
        species: species || undefined,
        breed: breed || undefined,
        age: typeof age === "string" ? undefined : age,
        ownerId: typeof ownerId === "string" ? parseInt(ownerId) : ownerId,
      };
      const res = await PetService.createWithImage(payload, file);
      setResult(res.data);
    }  catch (err: unknown) {
  if (axios.isAxiosError<{ message?: string }>(err)) {
    setError(err.response?.data?.message ?? err.message ?? "Error");
  } else if (err instanceof Error) {
    setError(err.message);
  } else {
    setError("Unknown error");
  }
}
};

  return (
    <div className="p-6 max-w-xl space-y-4">
      <h1 className="text-2xl font-bold">Create Pet</h1>
      <form className="space-y-3" onSubmit={submit}>
        <input
          className="border p-2 w-full"
          placeholder="Name *"
          value={name}
          onChange={(e) => setName(e.target.value)}
        />
        <input
          className="border p-2 w-full"
          placeholder="Species"
          value={species}
          onChange={(e) => setSpecies(e.target.value)}
        />
        <input
          className="border p-2 w-full"
          placeholder="Breed"
          value={breed}
          onChange={(e) => setBreed(e.target.value)}
        />
        <input
          className="border p-2 w-full"
          type="number"
          placeholder="Age"
          value={age}
          onChange={(e) =>
            setAge(e.target.value === "" ? "" : Number(e.target.value))
          }
        />
        <input
          className="border p-2 w-full"
          type="number"
          placeholder="Owner ID *"
          value={ownerId}
          onChange={(e) =>
            setOwnerId(e.target.value === "" ? "" : Number(e.target.value))
          }
        />

        <div className="space-y-2">
          <input type="file" accept="image/*" onChange={onFileChange} />
          {preview && (
            <img
              src={preview}
              alt="preview"
              className="max-h-40 rounded border"
            />
          )}
        </div>

        <button className="bg-blue-600 text-white px-4 py-2 rounded">
          Save
        </button>
      </form>

      {error && <p className="text-red-600">{error}</p>}

      {result && (
        <div className="bg-gray-100 p-3 rounded">
          <pre>{JSON.stringify(result, null, 2)}</pre>
          {result.imageUrl && (
            <img
              src={`http://localhost:8080${result.imageUrl}`}
              alt="pet"
              className="mt-3 max-h-40 rounded border"
            />
          )}
        </div>
      )}
    </div>
  );
}
