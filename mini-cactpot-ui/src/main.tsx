import { MantineProvider } from '@mantine/core';
import { ModalsProvider, ContextModalProps } from '@mantine/modals';
import { Notifications } from '@mantine/notifications';
import { StrictMode, FC } from 'react';
import { createRoot } from 'react-dom/client';
import { Provider as ReduxProvider } from 'react-redux';
import { App } from './App';
import { makeStore } from './redux/util/Store';

import './assets/styles/global.css';
import { TICKET_CHOOSER_MODAL_NAME, TicketChooserModal } from './components/modals/TicketChooserModal';

const store = makeStore();

const modals: Record<string, FC<ContextModalProps<any>>> = {
    [TICKET_CHOOSER_MODAL_NAME]: TicketChooserModal
};

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
                <ModalsProvider
                    modals={modals}
                    modalProps={{
                        size: 'md',
                        shadow: 'xl',
                        overlayProps: {
                            blur: 4,
                            opacity: 0.6
                        },
                        transitionProps: {
                            duration: 500,
                            exitDuration: 500,
                            transition: 'slide-down'
                        },
                        centered: true
                    }}
                >
                    <Notifications />
                    <App />
                </ModalsProvider>
            </MantineProvider>
        </ReduxProvider>
    </StrictMode>
)
