import instance from "@/app/components/common/configs/axios-config"
import { IUser } from "../model/user"


export const findAllUsersAPI = async (page: number)=>{ //axios
    try{
        const response = await instance().get('/users/list',{
            params: {page, limit: 10}
        })
        return response.data

    }catch(error){
        console.log("getAllUserAPI error"+error)

        return error
    }
}

export const findUserByIdAPI = async (id: number)=>{ 
    try{
        const response = await instance().get('/users/detail',{
            params: {id}
        })
        return response.data

    }catch(error){
        console.log("getfindUserById error"+error)

        return error
    }
}
export const deleteUserByIdAPI = async (id: number)=>{ 
    try{
        const response = await instance().delete('/users/delete',{
            params: {id}
        })
        console.log("success");
        return response.data

    }catch(error){
        console.log("deleteUserById error"+error)

        return error
    }
}
export const countUserAPI = async ()=>{ 
    try{
        const response = await instance().get('/users/count',{
        })
        return response.data

    }catch(error){
        console.log("count error"+error)

        return error
    }
}
export const modifyUserByIdAPI = async (all: IUser)=>{ 
    try{
        const response = await instance().put('/users/modify',{
            params: {all}
        })
        return response.data

    }catch(error){
        console.log("modifyUserById error"+error)

        return error
    }
}

export const loginAPI = async(user:IUser) => {
    console.log(` 로그인API 에 넘어온 파라미터 : ${JSON.stringify(user)}`)
    try{
        const response = await instance().post('/auth/login',user)
        return response.data

    }catch(error){
        console.log("login error"+error)

        return error
    }
}

export const existsUsernameAPI = async (username: string) => {
    try{
        const response = await instance().get('/auth/exists-username',{params: {username}})
        console.log('existsUsernameAPI 결과: '+ response.data)
        return response.data
    }catch (error: unknown) {
        if (error instanceof Error) {
            console.log('Error', error.message);
        } else {
            console.log('An unexpected error occurred');
        }
    }
}
export const logoutAPI = async () => {
    try{
        const response = await instance().get(`/users/logout`)
        console.log('logoutAPI 결과: '+ response.data)
        return response.data
    }catch(error){
        console.log(error)
        return error
    }
}

  //토큰이 발급 되기 전 날라가는 요청사항을 auth라고 줌