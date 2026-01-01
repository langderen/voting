import { fileURLToPath, URL } from 'node:url'

import { defineConfig } from 'vite'
import vue from '@vitejs/plugin-vue'
import vueDevTools from 'vite-plugin-vue-devtools'
<<<<<<< HEAD
import AutoImport from 'unplugin-auto-import/vite'
import Components from 'unplugin-vue-components/vite'
import { ElementPlusResolver } from 'unplugin-vue-components/resolvers'

=======

// https://vite.dev/config/
>>>>>>> 1dd10d2962ac15626b6ca61ca91852fc43a89c71
export default defineConfig({
  plugins: [
    vue(),
    vueDevTools(),
<<<<<<< HEAD
    AutoImport({
      resolvers: [ElementPlusResolver()],
      imports: ['vue'],
    }),
    Components({
      resolvers: [ElementPlusResolver()],
    }),
=======
>>>>>>> 1dd10d2962ac15626b6ca61ca91852fc43a89c71
  ],
  resolve: {
    alias: {
      '@': fileURLToPath(new URL('./src', import.meta.url))
    },
  },
<<<<<<< HEAD
  build: {
    target: 'es2015',
    cssCodeSplit: true,
    rollupOptions: {
      output: {
        manualChunks: {
          'element-plus': ['element-plus'],
          'echarts': ['echarts'],
          'vue-vendor': ['vue', 'vue-router', 'pinia'],
        },
      },
    },
  },
  optimizeDeps: {
    include: [
      'vue',
      'vue-router',
      'pinia',
      'element-plus',
      'axios',
    ],
  },
  server: {
    host: true,
    port: 5173,
    open: false,
    hmr: true,
    headers: {
      'Cache-Control': 'no-cache',
    },
  },
=======
>>>>>>> 1dd10d2962ac15626b6ca61ca91852fc43a89c71
})
