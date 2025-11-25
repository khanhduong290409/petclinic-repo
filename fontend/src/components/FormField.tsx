import type { HTMLInputTypeAttribute } from "react";

type Props = {
  label: string;
  value: string | number | undefined;
  onChange: (v: string) => void;
  type?: HTMLInputTypeAttribute;
  placeholder?: string;
  required?: boolean;
};

export default function FormField({ label, value, onChange, type="text", placeholder, required }: Props) {
  return (
    <label className="block">
      <span className="text-sm text-gray-600">{label}{required && " *"}</span>
      <input
        className="border rounded w-full p-2 mt-1"
        type={type}
        value={value ?? ""}
        placeholder={placeholder}
        required={required}
        onChange={(e) => onChange(e.target.value)}
      />
    </label>
  );
}
