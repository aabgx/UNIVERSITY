import { useEffect, useState } from "react";

export function useConnection() {
  const [isOnline, setOnline] = useState<boolean>((): boolean => {
    return navigator.onLine;
  });
  useEffect(() => {
    window.addEventListener("online", () => setOnline(true));
    window.addEventListener("offline", () => setOnline(false));

    return () => {
      window.removeEventListener("online", () => setOnline(true));
      window.removeEventListener("offline", () => setOnline(false));
    };
  });
  return [isOnline, setOnline] as const;
}
