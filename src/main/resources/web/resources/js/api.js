// 요청 인터셉터 추가
axios.interceptors.request.use(
    (config) => {
        if (!util.isEmptyObj(util.getSession('token'))) {
            config.headers['Authorization'] = `Bearer ${util.getSession('token')}`;
        }
        return config;
    }, (error) => {
        return Promise.reject(error);
    }
);

// 응답 인터셉터 추가
axios.interceptors.response.use(
    (response) => {
        return response;
    }, (error) => {
        return Promise.reject(error);
    }
);

class Api {
}

const api = new Api();
