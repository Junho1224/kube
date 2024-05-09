'use client'


import { IBoard } from "@/app/components/boards/model/board";
import Columns from "@/app/components/boards/module/columns";
import { findAllBoards } from "@/app/components/boards/service/board.service";
import { getAllBoards } from "@/app/components/boards/service/board.slice";
import { PG } from "@/app/components/common/enums/PG";
import { Box } from "@mui/material";
import { DataGrid } from "@mui/x-data-grid";
import { NextPage } from "next";
import Link from "next/link";
import { useEffect, useState } from "react";
import { useDispatch, useSelector } from "react-redux";

// import React from "react";

// const cards = [
//   "https://www.tailwindtap.com/assets/components/horizontal-carousel/autumn.jpg",
//   "https://www.tailwindtap.com/assets/components/horizontal-carousel/babypinetree.jpg",
//   "https://www.tailwindtap.com/assets/components/horizontal-carousel/beach.jpg",
//   "https://www.tailwindtap.com/assets/components/horizontal-carousel/purpleflowers.jpg",
//   "https://www.tailwindtap.com/assets/components/horizontal-carousel/starrysky.jpg",
//   "https://www.tailwindtap.com/assets/components/horizontal-carousel/lake.jpg",
// ];


const BoardListPage: NextPage = ({ data }: any) => {
  const dispatch = useDispatch()
  const allBoards = useSelector(getAllBoards) as IBoard[]

  console.log(allBoards)


  useEffect(() => {
    dispatch(findAllBoards(1))
  }, [dispatch])

  return (<>
    <h2>게시판 목록</h2>
    <div className="flex overflow-x-scroll snap-x snap-mandatory max-w-6xl no-scrollbar">
      {/* {cards.map((data, index) => {
        return (
          <section
            className="flex-shrink-0 w-full snap-center justify-center items-center"
            key={index}
          >
            <img
              src={data}
              alt="Images to scroll horizontal"
              className="w-full h-[500px]"
            />
          </section>
        );
      })} */}

    </div>
    <Link href={`${PG.ARTICLE}/save`}>게시판 글쓰기</Link>
    <Box sx={{ height: 400, width: '100%' }}>
      {allBoards && <DataGrid
        rows={allBoards}
        columns={Columns()}
        initialState={{
          pagination: {
            paginationModel: {
              pageSize: 5,
            },
          },
        }}
        pageSizeOptions={[5]}
        checkboxSelection
        disableRowSelectionOnClick
      />}
    </Box>

    <div>
      {allBoards.map((board, index) => (
        <div key={index}>
          <h3>{board.title}</h3>
          <p>{board.description}</p>
        </div>
      ))}
    </div>
    
  </>)
}

export default BoardListPage