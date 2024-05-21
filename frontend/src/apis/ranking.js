
import { instance } from "./axiosModule";

export const getRanking = async(quizGameId) => {
    const url = `/quizzes/ranking/${quizGameId}`;
    const tokens = JSON.parse(localStorage.getItem('tokens'))
    console.log(tokens.accessToken);
    const headers = {
        "Authorization": `Bearer ${tokens.accessToken}`
    }

    return await instance.get(url, {headers})
    .then((res) => {
        console.log('api 결과:', res)
        return res.data;
    })
    .catch(
        (err) => {console.log("API 호출 에러", err)
                throw err;}
    )
}