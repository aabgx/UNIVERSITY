import React from 'react';
import { useAppState } from "./useAppState";
import { useNetwork } from "./useNetwork";
import { IonItem, IonLabel } from "@ionic/react";


export const NetworkState: React.FC = () => {
    const { appState } = useAppState();
    const {networkStatus} = useNetwork();
    
    return (
      networkStatus.connected ? 
        <IonLabel style={{ marginRight: '5px' }} slot="end">
            🟢
        </IonLabel> :
        <IonLabel style={{ marginRight: '5px' }} slot="end">
            🔴
        </IonLabel>
    );
}