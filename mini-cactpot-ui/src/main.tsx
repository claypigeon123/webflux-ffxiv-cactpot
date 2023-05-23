import { StrictMode } from 'react';
import { createRoot } from 'react-dom/client';
import { Provider as ReduxProvider } from 'react-redux';
import { App } from './App';
import { AppWrapper } from './AppWrapper';
import { makeStore } from './redux/util/Store';

import './assets/styles/global.css';

const store = makeStore();

createRoot(document.getElementById('root') as HTMLElement).render(
    <StrictMode>
        <ReduxProvider store={store}>
            <AppWrapper>
                <App />
            </AppWrapper>
        </ReduxProvider>
    </StrictMode>
)
