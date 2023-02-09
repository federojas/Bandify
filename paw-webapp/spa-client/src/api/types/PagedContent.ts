//TODO: esto va aca o en models, lo mismo para apiresult

export default class PagedContent<T> {
    private readonly content: T;
    private readonly maxPage: number;
    private readonly nextPage: string;
    private readonly previousPage: string;
    private readonly lastPage: string;
    private readonly firstPage: string;

    constructor(content: T, maxPage: number, nextPage: string, previousPage: string, lastPage: string, firstPage: string) {
        this.content = content;
        this.maxPage = maxPage;
        this.nextPage = nextPage;
        this.previousPage = previousPage;
        this.lastPage = lastPage;
        this.firstPage = firstPage;
    }

    public getContent(): T {
        return this.content;
    }

    public getMaxPage(): number {
        return this.maxPage;
    }

    public getNextPage(): string {
        return this.nextPage;
    }

    public getPreviousPage(): string {
        return this.previousPage;
    }

    public getLastPage(): string {
        return this.lastPage;
    }

    public getFirstPage(): string {
        return this.firstPage;
    }
}