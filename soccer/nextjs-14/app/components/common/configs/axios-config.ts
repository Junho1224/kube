import axios, { AxiosInstance } from "axios"
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

// const instance = axios.create({baseURL:process.env.NEXT_PUBLIC_API_URL}) //axios의 객체

// instance.interceptors.request.use(
//     (request)=> {
//         const accessToken = parseCookies().accessToken;
//         console.log('AXIOS interceptors 쿠키에서 토큰 추출')
//         request.headers['Content-Type'] = 'application/json'
//         request.headers['Authorization'] = `Bearer ${accessToken}` //변화
        
//         return request
//     },
//     (error)=> {
//         console.log('AXIOS interceptors 에러'+error)
//         return Promise.reject(error)
//     }

// )

// instance.interceptors.response.use(
//     (response)=>{
//         if(response.status === 404){
//             console.log('response 404 error')

//         }
//         return response

//     }


// )

// export default instance


//기존 정적 값에서 동적 값으로 변환
export default function instance() {
    const instance = axios.create({baseURL: process.env.NEXT_PUBLIC_API_URL})
    setInterceptor(instance)
    return instance
}

export const setInterceptor = (inputInstance:AxiosInstance) => {
    inputInstance.interceptors.request.use(
    (config) => {
        config.headers["Content-Type"] = "application/json"
        config.headers["Authorization"] = `Bearer ${parseCookies().accessToken}`
        return config
    }, (error) => {
        console.log("AXIOS INTERSEPTOR ERROR OCCURRED : " + error)
        return Promise.reject(error)
    })
    inputInstance.interceptors.response.use(
        (response) => {
            if(response.status === 404) console.log("AXIOS INTERSEPTOR CATCHES 404")
            return response
        }
    )
    return inputInstance
}