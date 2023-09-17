import { defineConfig } from 'vite'
import react from '@vitejs/plugin-react-swc'

// https://vitejs.dev/config/
export default defineConfig({
    plugins: [react()],
    build: {
        rollupOptions: {
            output: {
                manualChunks: {
                    e: [
                        "@emotion/react"
                    ],
                    m: [
                        "@mantine/core",
                        "@mantine/modals",
                        "@mantine/hooks",
                        "@mantine/notifications"
                    ]
                }
            }
        },
        chunkSizeWarningLimit: 750
    }
})
