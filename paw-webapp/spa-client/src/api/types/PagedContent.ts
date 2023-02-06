//TODO: esto va aca o en models, lo mismo para apiresult

export default class PagedContent<T> {
    private readonly content: T;
    private readonly maxPage: number;
    private readonly nextPage: {};
    private readonly previousPage: {};

    constructor(content: T, maxPage: number, nextPage: {}, previousPage: {}) {
        this.content = content;
        this.maxPage = maxPage;
        this.nextPage = nextPage;
        this.previousPage = previousPage;
    }

    public getContent(): T {
        return this.content;
    }

    public getMaxPage(): number {
        return this.maxPage;
    }

    public getNextPage(): {} {
        return this.nextPage;
    }

    public getPreviousPage(): {} {
        return this.previousPage;
    }
}