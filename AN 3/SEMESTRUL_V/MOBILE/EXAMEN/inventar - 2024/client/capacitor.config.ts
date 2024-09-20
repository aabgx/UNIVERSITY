import { CapacitorConfig } from '@capacitor/cli';

const config: CapacitorConfig = {
  appId: 'ionic.client',
  appName: 'client',
  webDir: 'dist',
  server: {
    androidScheme: 'https'
  }
};

export default config;
