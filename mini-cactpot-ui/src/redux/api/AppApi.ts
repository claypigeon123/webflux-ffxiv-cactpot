import { createApi } from '@reduxjs/toolkit/query/react';
import { axiosBaseQuery } from './AxiosBaseQuery';
import { BACKEND_BASE_URI } from './BackendURIs';

export const appApi = createApi({
    reducerPath: 'appApi',
    baseQuery: axiosBaseQuery({
        baseUrl: BACKEND_BASE_URI
    }),
    tagTypes: ['mini_cactpot_tickets', 'mini_cactpot_ticket'],
    endpoints: ({ query, mutation }) => ({

    })
});