"use client";

import { AttachFile, FmdGood, ThumbUpAlt } from '@mui/icons-material';
import FmdGoodIcon from '@mui/icons-material/FmdGood';
import AttachFileIcon from '@mui/icons-material/AttachFile';
import ThumbUpAltIcon from '@mui/icons-material/ThumbUpAlt';
import { MyTypography } from "@/app/components/common/style/cell";
import { useEffect, useState } from 'react';
import { IBoard } from '@/app/components/boards/model/board';
import { useRouter } from 'next/navigation';
import { useDispatch, useSelector } from 'react-redux';
import { PG } from '@/app/components/common/enums/PG';
import { findAllBoards } from '@/app/components/boards/service/board.service';
import { getAllBoards } from '@/app/components/boards/service/board.slice';
import { useForm } from 'react-hook-form';
import { saveArticle } from '@/app/components/articles/service/article.service';
import { jwtDecode } from 'jwt-decode';
import { parseCookie } from 'next/dist/compiled/@edge-runtime/cookies';
import { parseCookies } from 'nookies';
import { IArticle } from '@/app/components/articles/model/article';



export default function WriteArticlePage() {
  const { register, handleSubmit,formState:{errors}} = useForm()

  const router = useRouter();
  const dispatch = useDispatch();
  const allboards = useSelector(getAllBoards) as IBoard[]

  const [content, setContent] = useState("")
  const [article, setArticle] = useState({} as IArticle)

  const selectHandler = (e: any) => {
    setContent(e.target.value)
  }

  const handelCancel = () => {//Cancel
    router.back();

  }

  const onSubmit = (data:any)=> {
    alert(JSON.stringify(data))
    dispatch(saveArticle(data))
    .then((res:any)=>{
      const data = res.payload
      alert(`'게시글 작성 완료'${data}`)
      // router.push('') detail[id] 넘어가는거 구현
      const boardId = data.boardId
      router.push(`/article/list/${boardId}`)

    })
    .catch((err:any)=>{

    })
  }
  // const handleSubmit = () => { //POST
  //   console.log('user ...' + JSON.stringify(article))
  //   dispatch(saveArticle(article))  // writer추가 구현 필요
  //   alert("작성 완료")
  //   router.push(`${PG.ARTICLE}/list`)

  // }
  const handleInsertTitle = (e: any) => {
    setArticle({
      ...article,
      title: e.target.value
    })


  }
  const handleInsertContent = (e: any) => {
    setArticle({
      ...article,
      content: e.target.value

    })
  }

  useEffect(() => {
    dispatch(findAllBoards(1))
    // console.log("토큰을 디코드한 내용:")
    // console.log(JSON.stringify(jwtDecode<any>(parseCookies().accessToken)))
    // console.log("토큰을 디코드한 ID:")
    // console.log(jwtDecode<any>(parseCookies().accessToken).userId)
  

      
  }, [])

  return (<>
    <form onSubmit={handleSubmit(onSubmit)} className="max-w-sm mx-auto">
      <label htmlFor="countries" className="block mb-2 text-sm font-medium text-gray-900 dark:text-white">Select an option</label>
      <select defaultValue={1}
      {...register('id',{required:true})}
        onChange={selectHandler}
        id="countries" className="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500">
        {allboards.map((board) => (
          <option key={board.id} value={board.id}>{board.title}</option>
        ))
        }
      </select>
 

    <div className="editor mx-auto w-10/12 flex flex-col text-gray-800 border border-gray-300 p-4 shadow-lg max-w-2xl">
      {MyTypography('Article 작성', "1.5rem")}
      <input type='hidden' value={jwtDecode<any>(parseCookies().accessToken).userId}readOnly/>
      <input
      {...register('title',{required:true, maxLength:30})}
      className="title bg-gray-100 border border-gray-300 p-2 mb-4 outline-none" placeholder="Title" type="text" name="title" onChange={handleInsertTitle} />
      <textarea
      {...register('content',{required:true,maxLength:300})}
      className="description bg-gray-100 sec p-3 h-60 border border-gray-300 outline-none" placeholder="Describe everything about this post here" name="content" onChange={handleInsertContent}></textarea>
      {/* <!-- icons --> */}
      <div className="icons flex text-gray-500 m-2">
        <svg className="mr-2 cursor-pointer hover:text-gray-700 border rounded-full p-1 h-7" xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke="currentColor">
          <ThumbUpAltIcon component={ThumbUpAlt}></ThumbUpAltIcon>
        </svg>
        <svg className="mr-2 cursor-pointer hover:text-gray-700 border rounded-full p-1 h-7" xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke="currentColor">
          <FmdGoodIcon component={FmdGood}></FmdGoodIcon>
        </svg>
        <svg className="mr-2 cursor-pointer hover:text-gray-700 border rounded-full p-1 h-7" xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke="currentColor">
          <AttachFileIcon component={AttachFile}></AttachFileIcon>
        </svg>
        <div className="count ml-auto text-gray-400 text-xs font-semibold">0/300</div>
      </div>
      {/* <!-- buttons --> */}
      <div className="buttons flex">
        {/* <div className="btn  overflow-hidden relative w-30 bg-blue-500 text-white p-3 px-8 rounded-xl font-bold uppercase -- before:block before:absolute before:h-full before:w-1/2 before:rounded-full
        before:bg-pink-400 before:top-0 before:left-1/4 before:transition-transform before:opacity-0 before:hover:opacity-100 hover:text-200 hover:before:animate-ping transition-all duration-00"
          onSubmit={handleSubmit}> Post </div> */}
          <input type="submit" value="SUBMIT" />


        <div className="btn  overflow-hidden relative w-30 bg-white text-blue-500 p-3 px-4 rounded-xl font-bold uppercase -- before:block before:absolute before:h-full before:w-1/2 before:rounded-full
        before:bg-pink-400 before:top-0 before:left-1/4 before:transition-transform before:opacity-0 before:hover:opacity-100 hover:text-200 hover:before:animate-ping transition-all duration-00"
          onClick={handelCancel}>Cancel</div>
      </div>
    </div>
    </form>
    <div>
      {allboards.map((board,index) => (
        <div key={index}>
          <h3>{board.title}</h3>
          <h3>{board.content}</h3>
        </div>
      ))}
    </div>

  </>
  )


}