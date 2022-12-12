import api from './api';

const GenreApi = (() => {
    const endpoint = '/genres';

    const getGenres = () => {
        return api.get(endpoint);
    }

}
)();

export default GenreApi;