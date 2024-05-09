'use client'

import { useRouter } from "next/navigation"
import { DataGrid } from '@mui/x-data-grid';
import { useState, useEffect } from "react"
import {Box, Button, Input, Typography} from '@mui/material';
import { useSelector, useDispatch } from 'react-redux'
import { NextPage } from "next";


import { rowSelectionStateInitializer } from "@mui/x-data-grid/internals";
import { MyTypography } from "@/app/components/common/style/cell";
import { getSingleBoard } from "@/app/components/boards/service/board.slice";
import { findBoardById } from "@/app/components/boards/service/board.service";
// import React from "react";




export default function BoardDetailPage (props:any) {

    const dispatch = useDispatch();
    const board = useSelector(getSingleBoard)

    useEffect(() => {
        console.log("+++")
        dispatch(findBoardById(props.params.id))
    },[] )
    return (<>
        {board.title} 게시판 상세 

        <span> ID :</span> <Typography textAlign="center" sx={{fontSize:"3rm"}}> {board.id}</Typography>
        <span> 게시판이름 :</span> <Typography textAlign="center" sx={{fontSize:"3rm"}}> {board.boardName}</Typography>
        <span> 게시판타입 :</span> <Typography textAlign="center" sx={{fontSize:"3rm"}}> {board.boardType}</Typography>
        <span> 등록일 :</span> <Typography textAlign="center" sx={{fontSize:"3rm"}}> {board.regDate}</Typography>
        <span> 수정일 :</span> <Typography textAlign="center" sx={{fontSize:"3rm"}}> {board.modDate}</Typography>

    </>)
} 