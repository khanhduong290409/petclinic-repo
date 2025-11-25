import { useEffect, useState } from "react";

type Props = {
  onFileChange: (file?: File) => void;
  initialUrl?: string;
};

export default function ImageUploader({ onFileChange, initialUrl }: Props) {
  const [preview, setPreview] = useState<string | undefined>(initialUrl);

  useEffect(() => setPreview(initialUrl), [initialUrl]);

  return (
    <div className="space-y-2">
      <input
        type="file"
        accept="image/*"
        onChange={(e) => {
          const f = e.target.files?.[0];
          onFileChange(f || undefined);
          setPreview(f ? URL.createObjectURL(f) : initialUrl);
        }}
      />
      {preview && <img src={preview} alt="preview" className="max-h-40 rounded border" />}
    </div>
  );
}
