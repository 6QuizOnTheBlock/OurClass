import { instance } from "./axiosModule";


export const getUserInfo = async (uuid, email) => {
    const url = `/members/start`

    return await instance.post(url, {uuid: uuid, email: email})
    .then((res) => {
        console.log('api 결과:', res)
        localStorage.setItem('tokens', JSON.stringify(res.data.data))
            instance.defaults.headers.common["Authorization"] = res.data.accessToken;
        return res.data;
    })
    .catch(
        (err) => {console.log("API 호출 에러",err);
                  throw err;})
    
}
