from fastapi import FastAPI, Request
from langchain.chat_models.openai import ChatOpenAI
from langchain.chat_models import ChatOpenAI
from langchain_community.chat_models.openai import ChatOpenAI
from langchain_community.chat_models import ChatOpenAI
from langchain.schema import SystemMessage, HumanMessage, AIMessage
import os
from dotenv import load_dotenv
from typing import Optional
from pydantic import BaseModel
from fastapi.middleware.cors import CORSMiddleware

BASE_DIR = os.path.dirname(os.path.abspath(__file__))
load_dotenv(os.path.join(BASE_DIR, ".env"))


class Request(BaseModel):
    question : str
class Response(BaseModel):
    answer: str

app = FastAPI()

origins = ["*"]

app.add_middleware(
    CORSMiddleware,
    allow_origins=origins,
    allow_credentials=True,
    allow_methods=["*"],
    allow_headers=["*"],
)

@app.get("/")
def read_root():
    return {"Hello": "World"}

@app.post("/chat")
def chatting(req:Request):
    print('딕셔너리 내용')
    print(req)


    response = ChatOpenAI(
        openai_api_key=os.environ["api_key"],
        temperature=0.1,
        max_tokens=2048,
        model="gpt-3.5-turbo-0613"
    )

    message = [
        SystemMessage(content="""
                      Tell me the exact answer to the question
                      """, type="system"),
        HumanMessage(content="한국의 수도는 어디야?", type="human"),
        AIMessage(content="한국의 수도는 서울입니다.", type="ai"),
    ]

    # print('[답변] : ', response.predict_messages(message))
    # print(f'[답변] : {response.predict(req.get("question"))}')
    return Response(answer=response.predict(req.question))

@app.get("/items/{item_id}")
def read_item(item_id: int, q: str = None):
    return {"item_id": item_id, "q": q}