import { useEffect, useState, Dispatch, SetStateAction } from 'react';
import { Geolocation, Position } from '@capacitor/geolocation';

interface MyLocation {
  position?: Position | null;
  error?: Error;
}

export const useMyLocation = () => {
  const [state, setState] = useState<MyLocation>({});
  console.log('use state: ', state);

  useEffect(() => {
    console.log('getting location....');
    watchMyLocation()
  }, [setState]);
  return state;

  async function watchMyLocation() {
    let cancelled = false;
    let callbackId: string;
    try {
      const position = await Geolocation.getCurrentPosition({enableHighAccuracy: false, timeout: 10000});
      console.log('position: ', position);
      updateMyPosition('current', position);
    } catch (error) {
      updateMyPosition('current', null, error);
    }
    callbackId = await Geolocation.watchPosition({enableHighAccuracy: false}, (position, error) => {
      updateMyPosition('watch', position, error);
    });
    return () => {
      cancelled = true;
      Geolocation.clearWatch({ id: callbackId });
    };

    function updateMyPosition(source: string, position?: Position | null, error: any = undefined) {
      console.log(source, position, error);
      if (!cancelled && (position || error)) {
        setState({ position, error });
      }
    }
  }
};