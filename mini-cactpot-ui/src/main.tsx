import { StrictMode } from 'react';
import { createRoot } from 'react-dom/client';
import { Provider as ReduxProvider } from 'react-redux';
import { App } from './App';
import { makeStore } from './redux/util/Store';
import { MantineProvider } from '@mantine/core';
import { Notifications } from '@mantine/notifications';

import './assets/styles/global.css';

const store = makeStore();

createRoot(document.getElementById('root') as HTMLElement).render(
    <StrictMode>
        <ReduxProvider store={store}>
            <MantineProvider
                theme={{
                    colorScheme: 'dark',
                    primaryColor: 'violet',
                    fontFamily: 'Poppins, sans-serif',
                    fontFamilyMonospace: 'consolas',
                    headings: {
                        fontFamily: 'Poppins, sans-serif'
                    },
                    cursorType: 'pointer'
                }}
                withGlobalStyles
                withNormalizeCSS
            >
                <Notifications />
                <App />
            </MantineProvider>
        </ReduxProvider>
    </StrictMode>
)
