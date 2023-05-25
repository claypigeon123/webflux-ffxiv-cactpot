import { createApi } from '@reduxjs/toolkit/query/react';
import { MakeMiniCactpotSelectionRequest, MiniCactpotTicketDto, PaginatedResponse, ScratchMiniCactpotNodeRequest, WinningsMap } from '../../Interfaces';
import { axiosBaseQuery } from './AxiosBaseQuery';
import { BACKEND_BASE_URI, MINI_CACTPOT_MAKE_SELECTION_ID_URI, MINI_CACTPOT_SCRATCH_ID_URI, MINI_CACTPOT_START_NEW_GAME_URI, MINI_CACTPOT_TICKETS_ID_URI, MINI_CACTPOT_TICKETS_URI, MINI_CACTPOT_WINNINGS_MAP_URI, makeUri } from './BackendURIs';

export const appApi = createApi({
    reducerPath: 'appApi',
    baseQuery: axiosBaseQuery({
        baseUrl: BACKEND_BASE_URI
    }),
    tagTypes: ['winnings_map', 'mini_cactpot_tickets', 'mini_cactpot_ticket'],
    endpoints: ({ query, mutation }) => ({
        getWinningsMap: query({
            query: () => ({
                url: MINI_CACTPOT_WINNINGS_MAP_URI,
                method: 'GET'
            }),
            transformResponse: (native) => native as WinningsMap,
            providesTags: ['winnings_map']
        }),

        queryTickets: query({
            query: ({ page = 1, limit = 15, filters }: { page?: number, limit?: number, filters?: object }) => ({
                url: MINI_CACTPOT_TICKETS_URI,
                method: 'GET',
                params: { page, limit, ...filters }
            }),
            transformResponse: (native) => native as PaginatedResponse<MiniCactpotTicketDto>,
            providesTags: (res) => res
                ? [
                    ...res.documents.map((ticket) => ({ type: 'mini_cactpot_tickets' as const, id: ticket.id })),
                    { type: 'mini_cactpot_tickets', id: 'LIST' }
                ]
                : [{ type: 'mini_cactpot_tickets', id: 'LIST' }],
        }),

        getTicket: query({
            query: ({ id }: { id: string }) => ({
                url: makeUri(MINI_CACTPOT_TICKETS_ID_URI, [{ name: 'id', value: id }]),
                method: 'GET'
            }),
            transformResponse: (native) => native as MiniCactpotTicketDto,
            providesTags: (res) => res
                ? [{ type: 'mini_cactpot_ticket', id: res.id }]
                : []
        }),

        startNewGame: mutation({
            query: () => ({
                url: MINI_CACTPOT_START_NEW_GAME_URI,
                method: 'POST'
            }),
            transformResponse: (native) => native as MiniCactpotTicketDto,
            invalidatesTags: (res) => res
                ? ['mini_cactpot_tickets', { type: 'mini_cactpot_ticket', id: res.id }]
                : []
        }),

        scratch: mutation({
            query: ({ id, request }: { id: string, request: ScratchMiniCactpotNodeRequest }) => ({
                url: makeUri(MINI_CACTPOT_SCRATCH_ID_URI, [{ name: 'id', value: id }]),
                method: 'POST',
                data: request
            }),
            transformResponse: (native) => native as MiniCactpotTicketDto,
            invalidatesTags: (res) => res
                ? ['mini_cactpot_tickets', { type: 'mini_cactpot_ticket', id: res.id }]
                : []
        }),

        makeSelection: mutation({
            query: ({ id, request }: { id: string, request: MakeMiniCactpotSelectionRequest }) => ({
                url: makeUri(MINI_CACTPOT_MAKE_SELECTION_ID_URI, [{ name: 'id', value: id }]),
                method: 'POST',
                data: request
            }),
            transformResponse: (native) => native as MiniCactpotTicketDto,
            invalidatesTags: (res) => res
                ? ['mini_cactpot_tickets', { type: 'mini_cactpot_ticket', id: res.id }]
                : []
        })
    })
});