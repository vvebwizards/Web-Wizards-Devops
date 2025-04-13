// vite.config.js
import { defineConfig } from 'vite'
import react from '@vitejs/plugin-react'

// https://vitejs.dev/config/
export default defineConfig({
  plugins: [react()],
  server: {
    proxy: {
      '/chambre': {
        target: 'http://localhost:8089/foyer',
        changeOrigin: true,
        rewrite: (path) => path.replace(/^\/chambre/, '/chambre')
      },
      '/bloc': {
        target: 'http://localhost:8089/foyer',
        changeOrigin: true,
        rewrite: (path) => path.replace(/^\/bloc/, '/bloc')
      }
    }
  }
})
