from fastapi import APIRouter
from pydantic import BaseModel

from app.api.titanic.service.titanic_service import TitanicService

router = APIRouter()

class Request(BaseModel):
    question : str
class Response(BaseModel):
    answer: str

@router.post("/titanic")
async def titanic(req:Request):
    print("titanic 진입"+req.question)

    # 경로테스트
    # f = open("C:/Users/bitcamp/vonteam-kuber/kube/chat-server/backend/app/api/titanic/data/hello.txt",  "r", encoding="utf-8")
    # data = f.read()
    # print(data)

    service = TitanicService()
    service.preprocess()
    
   
    # return Response(answer="타이타닉 생존자 수는 100명 입니다.")
    return Response(answer=f"The accuracy of {req.question} Titanic model is {service.learning(service.modeling(req.question), req.question)}%")