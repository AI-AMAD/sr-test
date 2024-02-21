import { fileURLToPath, URL } from "node:url";

import vue from "@vitejs/plugin-vue";
import { defineConfig } from "vite";

// https://github.com/vuetifyjs/vuetify-loader/tree/next/packages/vite-plugin
import vuetify, { transformAssetUrls } from "vite-plugin-vuetify";

// https://vitejs.dev/config/
export default defineConfig({
  plugins: [
    vue({
      template: { transformAssetUrls },
    }),
    vuetify({
      autoImport: true,
    }),
  ],
  resolve: {
    alias: {
      "@": fileURLToPath(new URL("./src", import.meta.url)),
    },
  },

  // build 경로 설정
  build: {
    outDir: "../src/main/resources/static",
    emptyOutDir: true,
  },

  // proxy 설정
  server: {
    proxy: {
      "/api": {
        target: "http://localhost:1101",
        changeOrigin: true,
      },
    },
  },
});

