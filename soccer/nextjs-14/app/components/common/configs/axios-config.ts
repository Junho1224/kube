import axios from "axios"
import { parseCookie } from "next/dist/compiled/@edge-runtime/cookies"
import { parseCookies } from "nookies"
import { env } from "process"

// export default function AxiosConfig(){
//     return {
//         headers: {
//             "Cache-Control": "no-cache",
//             "Content-Type": "application/json",
//             Authorization: `Bearer blah ~`,
//             "Access-Control-Allow-Origin": "*",
//         }
//     }
// };

const instance = axios.create({baseURL:process.env.NEXT_PUBLIC_API_URL}) //axios의 객체

instance.interceptors.request.use(
    (request)=> {
        const accessToken = parseCookies().accessToken;
        console.log('AXIOS interceptors 쿠키에서 토큰 추출')
        request.headers['Content-Type'] = 'application/json'
        request.headers['Authorization'] = `Bearer ${accessToken}` //변화
        
        return request
    },
    (error)=> {
        console.log('AXIOS interceptors 에러'+error)
        return Promise.reject(error)
    }

)

instance.interceptors.response.use(
    (response)=>{
        if(response.status === 404){
            console.log('response 404 error')

        }
        return response

    }


)

export default instance