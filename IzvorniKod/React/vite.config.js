
import { defineConfig } from 'vite'
import react from '@vitejs/plugin-react'
import path from "path"

import dotenv from 'dotenv';
dotenv.config();

// https://vite.dev/config/
export default defineConfig({
  plugins: [react()],
  resolve: {
    alias: {
      "@": path.resolve(__dirname, "./src"),
    },
  },
  server: {
    proxy: {
      "/api":{
        target:`${process.env.VITE_BACKEND}`,
        changeOrigin: true,
        rewrite: (path) => path.replace(/^\/api/,'')
      }
    }
  }
})
