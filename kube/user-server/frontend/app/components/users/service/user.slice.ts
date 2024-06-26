import { createSlice } from "@reduxjs/toolkit";
import { findAllUsers, findUserById, modifyUserById, deleteUserById, countUser, login, existsUsername } from "./user.service";
import { IUser } from "../model/user";

const status = {
    pending: 'pending',
    fulfilled: 'fullfilled',
    rejected: 'rejected'
}

interface IAuth {
    message?: string
    token?: string

}

interface UserState {
    array?: Array<IUser>,
    json?: IUser,
    auth?: IAuth,
    existsUsername?: boolean


}

export const initialState: UserState = {
    json: {} as IUser,
    array: [],
    auth: {} as IAuth,
    existsUsername: false

}

const handleFulfilledarr = (state: any, { payload }: any) => { // payload는 action객체, 요구한것만 담는다. 여기서는 array
    state.array = payload
}
const handleFulfilledjson = (state: any, { payload }: any) => {
    state.json = payload
}

export const userSlice = createSlice({
    name: "user",
    initialState,
    reducers: {}, //내부 연산
    extraReducers: builder => { //자바연동
        const { pending, rejected } = status; // 진행중, 거부

        builder
            .addCase(findAllUsers.fulfilled, handleFulfilledarr)
            .addCase(findUserById.fulfilled, handleFulfilledjson)
            .addCase(deleteUserById.fulfilled, handleFulfilledarr)
            .addCase(modifyUserById.fulfilled, handleFulfilledarr)
            .addCase(login.fulfilled, (state: any, { payload }: any) => { state.auth = payload })
            .addCase(countUser.fulfilled, (state: any, { payload }: any) => { state.count = payload })
            .addCase(existsUsername.fulfilled,  (state: any, {payload}: any) => {state.existsUsername=payload})
                //자바로 해석하자면 swich case() findUserById.fulfilled면 handleFulfilled 실행
    }
})

export const getAllUsers = (state: any) => {
    console.log('------------------ Before useSelector ---------------')
    console.log(JSON.stringify(state.user.array)) //array로 받기 때문에 .result 뺌.
    return state.user.array;
}
export const getUserById = (state: any) => (state.user.json) // 
export const getExistsUsername = (state: any) =>(state.user.existsUsername)
export const getdeleteUserById = (state: any) => (state.user.array) // 
export const getModifyUserById = (state: any) => (state.user.array) // 
export const getCountUser = (state: any) => (state.user.count) // 
export const getAuth = (state: any) => (
    state.user.auth)

export const { } = userSlice.actions
// export const getSlice = (state: any)=> state.user.value
export default userSlice.reducer  //합쳐서 내보냄

