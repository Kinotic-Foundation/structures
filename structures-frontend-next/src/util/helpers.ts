// src/util/helpers.ts
export const getQueryParam = (name: string) => {
    const urlParams = new URLSearchParams(window.location.search);
    return urlParams.get(name);
};

export const getBaseUrl = () => {
    let prefix = 'http';
    let port = '4000';
    if (window.location.protocol.startsWith('https')) {
        prefix = 'https';
        port = '443';
    }
    return `${prefix}://${window.location.hostname}:${port}`;
};

export const graphqlURI = (id: string) => {
    return `${getBaseUrl()}/graphql/?namespace=${id}`
}