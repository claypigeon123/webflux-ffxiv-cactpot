import { MantineProvider } from '@mantine/core';
import { ContextModalProps, ModalsProvider } from '@mantine/modals';
import { Notifications } from '@mantine/notifications';
import { FC, StrictMode } from 'react';
import { createRoot } from 'react-dom/client';
import { Provider as ReduxProvider } from 'react-redux';
import { App } from './App';
import { TicketChooserModal } from './components/modals/TicketChooserModal';
import { makeStore } from './redux/util/Store';

import '@mantine/core/styles.css';
import '@mantine/notifications/styles.css';
import './assets/styles/global.css';
import { TICKET_CHOOSER_MODAL_NAME } from './components/modals/_modals';


const store = makeStore();

const modals: Record<string, FC<ContextModalProps<any>>> = {
    [TICKET_CHOOSER_MODAL_NAME]: TicketChooserModal
};

createRoot(document.getElementById('root') as HTMLElement).render(
    <StrictMode>
        <ReduxProvider store={store}>
            <MantineProvider
                theme={{
                    primaryColor: 'violet',
                    fontFamily: 'Poppins, sans-serif',
                    fontFamilyMonospace: 'consolas',
                    headings: {
                        fontFamily: 'Poppins, sans-serif'
                    },
                    cursorType: 'pointer'
                }}
                defaultColorScheme='dark'
            >
                <ModalsProvider
                    modals={modals}
                    modalProps={{
                        size: 'md',
                        shadow: 'xl',
                        overlayProps: {
                            blur: 3
                        },
                        transitionProps: {
                            duration: 300,
                            exitDuration: 300
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
