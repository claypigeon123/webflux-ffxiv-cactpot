
export const BACKEND_BASE_URI =                 import.meta.env.VITE_BACKEND_URI;
const PREFIX =                                  import.meta.env.VITE_BACKEND_PREFIX || '';



export const makeUri = (baseUri: string, pathVariables: Array<{ name: string, value: string }>) => {
    let uri = "".concat(baseUri);

    pathVariables.forEach(pair => {
        uri = uri.replace(`{${pair.name}}`, pair.value);
    })

    return uri;
}