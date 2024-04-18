import { createSlice } from "@reduxjs/toolkit";
import { findAllBoards, findBoardById } from "./board.service";
import { IBoard } from "../model/board";

const boardThunks = [findAllBoards, findBoardById]
interface BoardState{
    json: IBoard
    array: Array<IBoard>
}
export const initialState: BoardState = {
    json: {} as IBoard,
    array: []
  };

const status = {
    pending: 'pending',
    fulfilled: 'fullfilld',
    rejected: 'rejected'
}

const handleFulfilled = (state: any, {payload}: any) => { // payload는 action객체
    console.log('-----conclusion-----')
    state.array = payload 
    console.log(state.array)


}

export const boardSlice = createSlice({
    name:"board",
    initialState,
    reducers:{}, //내부 연산
        extraReducers: builder => { //자바연동
            const { pending, rejected } = status; // 진행중, 거부
    
            builder
                .addCase(findAllBoards.fulfilled, handleFulfilled)
                .addCase(findBoardById.fulfilled, (state:any,{payload} : any) => {state.array = payload})

    }
})

export const getAllBoards = (state: any) => {
    return state.board.array;
}
export const getBoardById = (state: any) => state.board.array
export const getSingleBoard = (state: any) => state.board.json

export const {}= boardSlice.actions
// export const getSlice = (state: any)=> state.board.value
export default boardSlice.reducer  //합쳐서 내보냄
    
