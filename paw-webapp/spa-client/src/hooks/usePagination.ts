import {getQueryOrDefault, useQuery } from "./useQuery";

export function usePagination() {
    const query = useQuery();
    const currentPage = parseInt(getQueryOrDefault(query, "page", "1"));
    return [currentPage];
}