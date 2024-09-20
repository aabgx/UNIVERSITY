import { Directory, Filesystem } from '@capacitor/filesystem';
import { useCallback } from 'react';

export function useFileSystem() {
    const readFile = useCallback<(path: string) => Promise<string>>(
      (path) =>
        Filesystem.readFile({
          path,
          directory: Directory.Data,
        }).then(result => result.data as string), []);
  
    const writeFile = useCallback<(path: string, data: string) => Promise<any>>(
      (path, data) =>
        Filesystem.writeFile({
          path,
          data,
          directory: Directory.Data,
        }), []);
  
    const deleteFile = useCallback<(path: string) => Promise<void>>(
      (path) =>
        Filesystem.deleteFile({
          path,
          directory: Directory.Data,
        }), []);
  
    return {
      readFile,
      writeFile,
      deleteFile,
    };
}