import { PayloadAction, createSlice } from "@reduxjs/toolkit";


interface AppState {
    activeTicketId?: string
}

const initialState: AppState = {}

export const appSlice = createSlice({
    name: 'app',
    initialState,
    reducers: {
        changeActiveTicket: (state, action: PayloadAction<string>) => {
            state.activeTicketId = action.payload;
        },
        
        resetActiveTicket: (state) => {
            state.activeTicketId = undefined;
        }
    }
})