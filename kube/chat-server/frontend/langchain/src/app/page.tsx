"use client"

import Image from "next/image";
import { useState } from 'react';
import { useForm, SubmitHandler } from "react-hook-form"


type Inputs = {
  question: string
  exampleRequired?: string
}


export default function Home() {


  const [messages, setMessages] = useState('')
  const [input, setInput] = useState('');


  const {
    register,
    handleSubmit,
    watch,
    formState: { errors },
  } = useForm<Inputs>()
  const onSubmit: SubmitHandler<Inputs> = (data) => {
    console.log('입력된 값 : ' + JSON.stringify(data))
    fetch('http://localhost:8000/api/chat/titanic', { //titanic //chat
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify(data),
    })
      .then((response) => response.json()) // JSON 형식으로 파싱
      .then((data) => {
        console.log("서버 응답:", data); // 서버 응답 로그 출력
        setMessages(data.answer); // 받은 응답을 상태에 저장
      })
      .catch((error) => console.log("error:", error));
  }


  return (<>


    <div className="Article w-[1440px] h-[1238px] relative bg-white">
      <div className="Cards left-[90px] top-[654px] absolute justify-start items-start gap-8 inline-flex">
        <div className="Card w-[404px] h-[434px] flex-col justify-start items-start gap-6 inline-flex">
          <div className="Copy self-stretch h-16 flex-col justify-center items-start gap-1 flex">
        {messages? messages : ""}
          </div>
        </div>
        <div className="Card w-[404px] h-[434px] flex-col justify-start items-start gap-6 inline-flex">
          <div className="Copy self-stretch h-16 flex-col justify-center items-start gap-1 flex">
          </div>
        </div>
        <div className="Card w-[404px] h-[434px] flex-col justify-start items-start gap-6 inline-flex">
          <div className="Copy self-stretch h-16 flex-col justify-center items-start gap-1 flex">
          </div>
        </div>
      </div>
      <div className="ArticleTitle w-[1181px] h-[139px] left-[80px] top-[244px] absolute flex-col justify-center items-start gap-6 inline-flex">
        <div className="Titanic self-stretch text-black text-[64px] font-bold font-['Inter']">Titanic에 대해서 물어보세요!</div>
      </div>
      <div className="Navigation h-[164px] px-20 py-14 left-0 top-0 absolute bg-white justify-center items-center gap-[795px] inline-flex">
        <div className="SiteName text-black text-xl font-medium font-['Inter'] leading-[30px]">Site name</div>
        <div className="Items self-stretch justify-end items-center gap-12 inline-flex">
          <div className="Page text-black text-xl font-medium font-['Inter'] leading-[30px]">Page</div>
          <div className="Page text-black text-xl font-medium font-['Inter'] leading-[30px]">Page</div>
          <div className="Page text-black text-xl font-medium font-['Inter'] leading-[30px]">Page</div>
          <div className="Button px-6 py-3.5 bg-black rounded-lg shadow justify-center items-center gap-2 flex">
            <div className="Button text-white text-base font-medium font-['Inter'] leading-normal">Button</div>
          </div>
        </div>
      </div>
      <form onSubmit={handleSubmit(onSubmit)}>
      <div className="InputField w-[1312px] h-[101px] px-4 py-3 left-[80px] top-[383px] absolute bg-white rounded-lg shadow border-4 border-blue-500 justify-start items-start gap-2 inline-flex">
      
        <input type="text" {...register("question", { required: true })} placeholder="메시지를 입력하세요"
              className="peer h-full  min-h-full w-full resize-y rounded-[7px]  !border-0 border-blue-gray-200 border-t-transparent bg-transparent px-3 py-2.5 font-sans text-sm font-normal text-blue-gray-700 outline outline-0 transition-all placeholder:text-blue-gray-300 placeholder-shown:border placeholder-shown:border-blue-gray-200 placeholder-shown:border-t-blue-gray-200 focus:border-2 focus:border-gray-900 focus:border-transparent focus:border-t-transparent focus:outline-0 disabled:resize-none disabled:border-0 disabled:bg-blue-gray-50"></input>
              <label
               className="before:content[' '] after:content[' '] pointer-events-none absolute left-0 -top-1.5 flex h-full w-full select-none text-[11px] font-normal leading-tight text-blue-gray-400 transition-all before:pointer-events-none before:mt-[6.5px] before:mr-1 before:box-border before:block before:h-1.5 before:w-2.5 before:rounded-tl-md before:border-t before:border-l before:border-blue-gray-200 before:transition-all before:content-none after:pointer-events-none after:mt-[6.5px] after:ml-1 after:box-border after:block after:h-1.5 after:w-2.5 after:flex-grow after:rounded-tr-md after:border-t after:border-r after:border-blue-gray-200 after:transition-all after:content-none peer-placeholder-shown:text-sm peer-placeholder-shown:leading-[3.75] peer-placeholder-shown:text-blue-gray-500 peer-placeholder-shown:before:border-transparent peer-placeholder-shown:after:border-transparent peer-focus:text-[11px] peer-focus:leading-tight peer-focus:text-gray-900 peer-focus:before:border-t-2 peer-focus:before:border-l-2 peer-focus:before:!border-gray-900 peer-focus:after:border-t-2 peer-focus:after:border-r-2 peer-focus:after:!border-gray-900 peer-disabled:text-transparent peer-disabled:before:border-transparent peer-disabled:after:border-transparent peer-disabled:peer-placeholder-shown:text-blue-gray-500"></label>
      </div>
      </form>
      <h4>{messages? messages : ""}</h4>
      
      
    </div>

    




  </>);
}