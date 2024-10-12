import { defineConfig } from 'astro/config';

import react from '@astrojs/react';
import tailwind from '@astrojs/tailwind';

// https://astro.build/config
export default defineConfig({
  site: 'https://valentinsvedas.vercel.app', 
  outDir: 'dist',
  integrations: [react({
    include:['**/react/*']
  }), tailwind()],
  output: 'hybrid',
  
});