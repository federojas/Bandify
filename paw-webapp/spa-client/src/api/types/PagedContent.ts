//TODO: esto va aca o en models, lo mismo para apiresult

export default class PagedContent<T> {
    private readonly content: T;
    private readonly maxPage: number;

    constructor(content: T, maxPage: number) {
        this.content = content;
        this.maxPage = maxPage;
    }

    public getContent(): T {
        return this.content;
    }

    public getMaxPage(): number {
        return this.maxPage;
    }
}