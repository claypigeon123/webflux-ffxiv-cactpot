
export const BACKEND_BASE_URI =                     import.meta.env.VITE_BACKEND_URI;
const PREFIX =                                      import.meta.env.VITE_BACKEND_PREFIX || '';

const MINI_CACTPOT_PREFIX =                         `${PREFIX}/mini-cactpot`;

export const MINI_CACTPOT_WINNINGS_MAP_URI =        `${MINI_CACTPOT_PREFIX}/winnings`;
export const MINI_CACTPOT_TICKETS_URI =             `${MINI_CACTPOT_PREFIX}/tickets`;
export const MINI_CACTPOT_TICKETS_ID_URI =          `${MINI_CACTPOT_PREFIX}/tickets/{id}`;
export const MINI_CACTPOT_START_NEW_GAME_URI =      `${MINI_CACTPOT_PREFIX}/start-new-game`;
export const MINI_CACTPOT_SCRATCH_ID_URI =          `${MINI_CACTPOT_PREFIX}/scratch/{id}`;
export const MINI_CACTPOT_MAKE_SELECTION_ID_URI =   `${MINI_CACTPOT_PREFIX}/make-selection/{id}`;

export const makeUri = (baseUri: string, pathVariables: Array<{ name: string, value: string }>) => {
    let uri = "".concat(baseUri);

    pathVariables.forEach(pair => {
        uri = uri.replace(`{${pair.name}}`, pair.value);
    })

    return uri;
}