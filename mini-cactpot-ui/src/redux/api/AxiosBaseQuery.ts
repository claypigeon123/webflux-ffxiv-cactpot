import { BaseQueryFn } from '@reduxjs/toolkit/query/react';
import axios, { AxiosError, AxiosRequestConfig, Method } from 'axios';

export interface AxiosBaseQueryError {
    status: number | undefined,
    data: any | string | undefined
}

export const axiosBaseQuery = ({ baseUrl }: { baseUrl: string } = { baseUrl: '' }): BaseQueryFn<
    {
        url: string,
        method: Method,
        data?: AxiosRequestConfig['data'],
        params?: AxiosRequestConfig['params'],
        headers?: AxiosRequestConfig['headers']
    },
    unknown,
    AxiosBaseQueryError
> => async ({ url, method, data, params, headers }) => {

    try {
        const axiosRequestConfig: AxiosRequestConfig<any> = { url: baseUrl + url, method, data, params, headers: { ...headers } };

        const result = await axios(axiosRequestConfig);
        return { data: result.data };
    } catch (axiosError) {
        let err = axiosError as AxiosError
        return {
            error: {
                status: err.response?.status,
                data: (err.response?.data as any).message || err.message,
            },
        }
    }
}