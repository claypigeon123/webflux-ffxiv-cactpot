import { StrictMode } from 'react';
import ReactDOM from 'react-dom';
import { App } from './App';

import 'bootstrap/dist/css/bootstrap.min.css';
import './assets/css/global.css';
import axios from 'axios';

axios.defaults.baseURL = "http://localhost:10900";

ReactDOM.render(
    <StrictMode>
        <App />
    </StrictMode>,
    document.getElementById('root')
);