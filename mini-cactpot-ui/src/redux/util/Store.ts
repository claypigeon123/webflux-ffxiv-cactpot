import { combineReducers, configureStore, PreloadedStateShapeFromReducersMapObject } from "@reduxjs/toolkit";
import { setupListeners } from "@reduxjs/toolkit/query";
import { appApi } from "../api/AppApi";
import { appSlice } from "../slice/AppSlice";

const rootReducer = combineReducers({
    [appSlice.name]: appSlice.reducer,
    [appApi.reducerPath]: appApi.reducer
});

export const makeStore = (preloadedState?: PreloadedStateShapeFromReducersMapObject<typeof rootReducer>) => {
    const store = configureStore({
        reducer: rootReducer,
        preloadedState,
        devTools: import.meta.env.DEV,
        middleware: (getDefaultMiddleware) => getDefaultMiddleware().concat([
            appApi.middleware
        ])
    });

    setupListeners(store.dispatch);

    return store;
}

export type RootState = ReturnType<typeof rootReducer>;
export type AppStore = ReturnType<typeof makeStore>;
export type AppDispatch = AppStore['dispatch'];