"use client";

import CardButton from "@/app/atoms/button/CardButton";
import MoveButton from "@/app/atoms/button/MoveButton";
import { IBoard } from "@/app/components/boards/model/board";
import { findAllBoards } from "@/app/components/boards/service/board.service";
import { getAllBoards } from "@/app/components/boards/service/board.slice"
import { PG } from "@/app/components/common/enums/PG";
import { useEffect, useState } from "react";
import { useSelector, useDispatch } from "react-redux"

export default function BoardCards() {

    const dispatch = useDispatch()
    const allBoards = useSelector(getAllBoards);

    useEffect(() => {
        dispatch(findAllBoards(1));
    }, [])

    return (<>

        <h1>게시판 목록 들어옴 </h1>

        {allBoards.map((board: IBoard) => (
            <CardButton key={board.id} id={board.id} title={board.title} description={board.description} regDate={""} modDate={""} />
        ))}
        <td>
            <MoveButton text={"글쓰기"} path={`${PG.ARTICLE}/save`} />
        </td>

    </>
    )
}